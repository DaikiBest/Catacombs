package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Iterator;

import persistence.JsonReader;
import persistence.JsonWriter;
import model.*;


// Represents the game; handles the room by room progression and encounters.
public class GameCLI {
    private Player player;
    private Inventory inventory;
    private GameCharacter enemy;
    private String notOver;
    private static final Scanner input = new Scanner(System.in);

    private RoomHandler roomHandler;
    private ItemFactory itemFactory;
    private BattleHandler battleHandler;
    private ShopHandler shopHandler;

    private static final String JSON_STORE = "./data/catacombs.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private static final Random RANDOM = new Random();

    // EFFECTS: begin the game
    public GameCLI() {
        roomHandler = new RoomHandler();
        itemFactory = new ItemFactory();
        battleHandler = new BattleHandler();
        shopHandler = new ShopHandler();

        player = new Player();
        inventory = player.getInventory();
        inventory.collect(itemFactory.makeDagger(), player); // start with a dagger
        notOver = "true";

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        tutorial();
        runGame();
    }

    // EFFECTS: display the beginning exposition and tutorial (inventory and inputs).
    private void tutorial() {
        System.out.println("\nYou enter a mysterious dungeon with only some coins and dagger in hand...");
        System.out.println("    | - - - - - - - - - - - - - - - - - - - - - - - - - |"
                + "\n\tCheck your inventory by typing \u001B[1m'i'\u001B[0m at any time. "
                + "\n\tAccepted inputs are marked in \u001B[1m'bold'\u001B[0m."
                + "\n    | - - - - - - - - - - - - - - - - - - - - - - - - - |");
    }

    // EEFECTS: run the game. Player progresses encounter by encounter, and every 5
    // rooms enters a crossroads.
    // When the loop is broken (notOver not equals the string "true"), then it means
    // that the game must end and
    // either the player won, or the player died.
    public void runGame() {
        while (notOver.equals("true")) {
            int roomNumber = roomHandler.getRoomNum();
            System.out.println("\nYou are in room " + roomNumber + ".");
            if (roomNumber % 5 == 0 && roomNumber != 0) { // crossroads
                crossRoads();
            } else {
                regularEncounter();
            }
            roomHandler.increaseRoomNum();
        }

        if (notOver.equals("victory")) {
            System.out.println("\u001B[1m\nYou are victorious in your quest!");
        }
        System.out.println("\nGame Over");
        EventLog log = EventLog.getInstance();
        Iterator<Event> gameLogs = log.iterator();
        while (gameLogs.hasNext()) {
            System.out.println(gameLogs.next());
        }
    }

    // EFFECTS: move to the next "room" and begin the encounter
    private void regularEncounter() {
        String command = "i"; // move to next room, unless player inputs i (to check their inventory)
        while (command.equalsIgnoreCase("i") | command.equalsIgnoreCase("rest")) {
            System.out.print("A rusty wooden door stands in your way... Type to enter, or "
                    + "\u001B[1m'rest'\u001B[0m (save/load game). ");
            command = input.nextLine();
            checkInventory(command);
            checkRest(command);
            lc(command);
        }
        nextEncounter();
    }

    // EFFECTS: determine the type of encounter: either a goblin, orc, chest, or
    // shop.
    private void nextEncounter() {
        int next = RANDOM.nextInt(10) + 1;
        if (next <= 4) { // 1-4 40%
            System.out.println("\nAs you enter the next room, a sneaky Goblin lunges at you!");
            enemy = new Goblin();
            enemyEncounter(enemy);

        } else if (next <= 7) { // 5-7 30%
            System.out.println("\nYou enter the next room and hear heavy grunting. An Orc readies his sword.");
            enemy = new Orc();
            enemyEncounter(enemy);

        } else if (next == 8) { // only 8: 10%
            System.out.println("\nAn ominous chest rests at the center of the room...");
            lootEncounter();

        } else { // 9-10 20%
            System.out.println("\nA man sits cross-legged on the ground. Scraps of Weapons and"
                    + " Armors are laid out neatly for display. 'Welcome traveller!'\n");
            shopEncounter();
        }
    }

    // EFFECTS: the player chooses between a regular encounter (left), or to
    // confront the final boss (right).
    private void crossRoads() {
        boolean inCrossroads = true;

        while (inCrossroads) {
            System.out.println("You find yourself at a crossroads..."
                    + "\nTo your \u001B[1m'left'\u001B[0m, a familiar wooden door. "
                    + "To your \u001B[1m'right'\u001B[0m, a foreboding magical door oozes with dark, mystical power.");
            String command = input.nextLine().trim();
            checkInventory(command);

            if (command.equalsIgnoreCase("left")) {
                inCrossroads = false;
                regularEncounter();
            } else if (command.equalsIgnoreCase("right")) {
                System.out.println("\nA malicious atmosphere swarms you... A sorcerer with a white beard and "
                        + "dark robes aims his staff at you, chanting corrupt whispers.");
                enemy = new DarkWizard();
                enemyEncounter(enemy);
                inCrossroads = false;
            }
        }
    }

    // MODIFIES: player, enemy, inventory
    // EFFECTS: begin battle encounter. Enemy and player roll their dice, and damage
    // is dealt accordingly.
    // The player is also allowed to attempt to avoid the encounter by fleeing, at
    // the risk of taking damage.
    private void enemyEncounter(GameCharacter enemy) {
        boolean inBattle = true;
        do {
            System.out.println("\nHP: " + player.getHealth() + "  DMG: " + player.getDamage()
                    + "      " + enemy.getName() + " HP: " + enemy.getHealth() + "  "
                    + enemy.getName() + " DMG: " + enemy.getDamage());

            // enemy turn
            int enemyRoll = enemy.rollDice(RANDOM.nextLong());
            System.out.print("Enemy rolled a " + enemyRoll + ". Roll your dice or \u001B[1m'flee'\u001B[0m! ");
            // player turn
            String command = input.nextLine();
            if (command.equalsIgnoreCase("flee")) {
                if (flee(enemy)) {
                    break;
                }

            } else {
                int playerRoll = player.rollDice(RANDOM.nextLong());
                String outcome = battleHandler.diceHandler(player, enemy, playerRoll, enemyRoll);
                printCombatRefinements();
                System.out.print("You rolled a " + playerRoll + "!");

                // outcome
                boolean endBattle = battleOutcome(outcome, enemy);
                if (endBattle) {
                    break;
                }
            }
        } while (inBattle);
    }

    // EFFECTS: print the details of your refinements, if you have at least one in
    // your equipped arsenal.
    private void printCombatRefinements() {
        int curWeaponRefine = inventory.getEquippedWeapon().getRefine();
        int curArmorRefine = ((inventory.getEquippedArmor() != null) ? inventory.getEquippedArmor().getRefine() : 0);
        int totalRefine = curArmorRefine + curWeaponRefine;
        if (totalRefine != 0) {
            System.out.println("Your refinements boost your attack by " + totalRefine + "!");
        }
    }

    // MODIFIES: player
    // EFFECTS: attempt to flee and avoid enemyEncounter. If successful, player
    // escapes enemy encounter. If
    // failed, the player takes half the enemy damage and remains at the enemy
    // encounter.
    private boolean flee(GameCharacter enemy) {
        int rand = RANDOM.nextInt(4) + 1;

        if (rand <= 3) { // 75%
            System.out.println("\nYou duck and weave past the enemy. You manage to escape into the next room!");
            return true; // end flee action
        } else { // 25%
            int hit = enemy.getDamage() / 2;
            hit = ((hit == 0) ? 1 : hit); // take at least 1 damage

            player.takeDamage(hit); // take half of enemy damage, rounded down; or 1 if 0.
            System.out.print("\nIn your attempt to flee, the enemy stops you in your tracks. ");
            System.out.print("You take " + hit + " damage.\n");
            return checkPlayerState();
        }
    }

    // MODIFIES: this
    // EFFECTS: Determine the outcome of the round of the battle. Either the player
    // or enemy take damage; if the
    // damage drops the player's health to zero, then they die, ending the
    // encounter, and eventually ending the game.
    // If the enemy's health drops to zero, the enemy dies ending the encounter, and
    // the player is rewarded coins.
    // If the slain enemy was the dark wizard (boss), the game ends with victory.
    private boolean battleOutcome(String outcome, GameCharacter enemy) {
        if (outcome.equals("win")) {
            System.out.println(" The " + enemy.getName() + " takes a hit.");

            if (!enemy.getAlive() && enemy.getName().equals("Dark Wizard")) {
                System.out.println("The Dark Wizard crumbles into dust in a raging fire...");
                notOver = "victory";
                return true;

            } else if (!enemy.getAlive()) {
                System.out.println(enemy.getName() + " has fallen... You move on...");
                battleHandler.giveRewards(inventory, enemy);
                return true;
            }

        } else if (outcome.equals("lose")) {
            System.out.println(" You take a hit");
            return checkPlayerState();

        } else {
            System.out.println(" You tie and your attacks rebound!");
        }
        return false;
    }

    // EFFECTS: checks player state. Returns true if dead, false if still alive.
    private boolean checkPlayerState() {
        if (!player.getAlive()) {
            System.out.println("You have fallen in battle... ");
            notOver = "defeat";
            return true;
        }
        return false;
    }

    // EFFECTS: Handles the shop encounter. Either buy, sell or exit encounter.
    private void shopEncounter() {
        boolean shopping = true;

        while (shopping) {
            System.out.print("'Here to \u001B[1m'buy'\u001B[0m, \u001B[1m'sell'\u001B[0m or "
                    + "\u001B[1m'refine'\u001B[0m? Say \u001B[1m'exit'\u001B[0m whenever you are finished here.' ");
            String command = input.nextLine().trim();
            checkInventory(command);

            if (command.equalsIgnoreCase("buy")) {
                buy();
            } else if (command.equalsIgnoreCase("sell")) {
                sell();
            } else if (command.equalsIgnoreCase("refine")) {
                refine();
            } else if (command.equalsIgnoreCase("exit")) {
                shopping = false;
                break;
            }
        }
    }

    // MODIFIES: inventory, player
    // EFFECTS: player gets to choose an item to purchase in exchange for coins
    // collected. Otherwise, player
    // can go back to choose a different option (buy, sell, exit).
    private void buy() {
        boolean buying = true;

        while (buying) {
            printShopData();

            System.out.print("Enter your purchase; otherwise, say \u001B[1m'back'\u001B[0m: ");
            String purchase = input.nextLine();
            checkInventory(purchase);

            if (purchase.equalsIgnoreCase("back")) {
                buying = false;
                break;
            } else if (shopHandler.getShopList().stream().anyMatch(purchase::equalsIgnoreCase)) {
                boolean boughtItem = handlePurchase(purchase);

                if (boughtItem) {
                    System.out.println("'Enjoy the wears traveller!'");
                    System.out.println("You obtained a " + purchase + "!");
                } else {
                    System.out.println("You can't buy that.");
                }
            } else {
                System.out.println("'I've never heard of that.'");
            }
        }
    }

    // EFFECTS: display all items and healing for sale and some player details such
    // as coin count.
    private void printShopData() {
        System.out.println("\nCoins: " + inventory.getCoins() + "    HP: " + player.getHealth()
                + "    MaxHP: " + player.getMaxHP() + "    Damage: " + player.getDamage());
        System.out.println("\n\t\t\t   \u001B[1m'Heal'\u001B[0m 1 hp: 1 coin\n"
                + "\u001B[1m'Dagger'\u001B[0m 1 dmg: 2 coins\t\t\t\t"
                + "\u001B[1m'Farmer's Cap'\u001B[0m 7 MaxHP: 4 coins"
                + "\n\u001B[1m'Mace'\u001B[0m 2 dmg: 4 coins\t\t\t\t"
                + "\u001B[1m'Thieve's Hood'\u001B[0m 9 MaxHP: 8 coins"
                + "\n\u001B[1m'Longsword'\u001B[0m 3 dmg: 10 coins\t\t\t"
                + "\u001B[1m'Knight's Helmet'\u001B[0m 12 MaxHP: 12 coins"
                + "\n\u001B[1m'Excalibur'\u001B[0m 5 dmg: 20 coins\t\t\t"
                + "\u001B[1m'Crown'\u001B[0m 15 MaxHP: 20 coins\n");
    }

    // MODIFIES: inventory, player
    // EFFECTS: Purchase item given the player's input
    private boolean handlePurchase(String purchase) {

        List<String> shopList = new ArrayList<>();
        for (String shopItem : shopHandler.getShopList()) {
            shopList.add(shopItem.toLowerCase());
        }
        
        int itemIndex = shopList.indexOf(purchase.toLowerCase());
        if (itemIndex == shopHandler.getShopList().size() - 1) { // last item in shopList, aka heal.
            return shopHandler.purchaseHealing(player);
        } else {
            return shopHandler.purchaseItem(itemIndex, player);
        }
    }

    // MODIFIES: inventory, player
    // EFFECTS: player gets to choose to sell an item they have in their inventory
    // for coins.
    // They can sell any item they have, except their last weapon.
    private void sell() {
        boolean selling = true;

        while (selling) {
            System.out.print("'What do you wish to sell?'; otherwise, say \u001B[1m'back'\u001B[0m: ");
            String command = input.nextLine();

            checkInventory(command);
            if (command.equals("back")) {
                selling = false;
                break;
            } else if (inventory.getItem(command) != null) { // if inventory contains item
                int itemIndex = getItemIndex(command);
                boolean status = shopHandler.sellItem(itemIndex, player);

                if (status) { // sold item succesfully
                    System.out.println("\n'This will make a fine addition to my collection.'");
                    System.out.println("You sold your " + command + "! \n");
                } else {
                    System.out.println("That's your last weapon. You can't sell that.\n");
                }
            }
        }
    }

    // MODIFIES: item
    // EFFECTS: player chooses to refine one of their items (or not) for coins.
    private void refine() {
        boolean refining = true;

        while (refining) {
            System.out.println();
            for (Item item : inventory.getItems()) {
                adjustInventoryPrint(item);
            }
            System.out.print("Choose an item to refine; otherwise, say \u001B[1m'back'\u001B[0m: ");
            String command = input.nextLine();

            checkInventory(command);
            if (command.equalsIgnoreCase("back")) {
                refining = false;
                break;
            } else if (inventory.getItem(command) != null) { // if inventory contains item
                int itemIndex = getItemIndex(command);
                boolean status = shopHandler.purchaseRefine(itemIndex, inventory);
                if (status) {
                    System.out.println("You offer your " + command + " to the shop keeper. "
                            + "With a grin, he smashes it with a hammer!\nYour item feels more refined somehow.");
                } else {
                    System.out.println("You can't afford that, or is already maximally refined.");
                }
            }
        }
    }

    // EFFECTS: obtains the item index in the player's inventory for given itemname (command). Obtains first instance
    private int getItemIndex(String command) {
        int itemIndex;
        // if (itemInstancesList.size() > 1) { //there is more than one of this item
        // for () {

        // }

        // System.out.print("Type the index of the item which you wish to sell (first
        // item is index 0)");
        // itemIndex = input.nextInt();
        // if (itemIndex > inventory.getItems().size()) {
        // System.out.print("Index out of bounds");
        // }
        // } else {
        itemIndex = inventory.getItems().indexOf(inventory.getItem(command));
        // }
        return itemIndex;
    }

    // EFFECTS: find a chest with randomized loot (either coins or a random item of
    // different "rarity")
    private void lootEncounter() {
        System.out.print("Open chest? ");
        input.nextLine();

        int randomLoot = RANDOM.nextInt(20) + 1;
        if (randomLoot <= 10) { // 1-10 50%
            int coins = 3 + RANDOM.nextInt(8 - 3 + 1);
            inventory.collectCoins(coins);
            System.out.println("You find a bundle of coins. You collect " + coins + " coins!");

        } else if (randomLoot <= 16) { // 11-16 30%
            commonLoot();

        } else if (randomLoot <= 19) { // 17-19 15%
            rareLoot();

        } else { // only 20 5%
            kingLoot();
        }
    }

    // EFFECTS: collect common loot. One of mace, cap, or hood.
    private void commonLoot() {
        System.out.println("You found common loot!");

        int rand = RANDOM.nextInt(3) + 1;
        if (rand == 1) {
            inventory.collect(itemFactory.makeMace(), player);
            System.out.println("You obtained a Mace!");
        } else if (rand == 2) {
            inventory.collect(itemFactory.makeCap(), player);
            System.out.println("You obtained a Farmer's Cap!");
        } else {
            inventory.collect(itemFactory.makeHood(), player);
            System.out.println("You obtained a Thieve's Hood!");
        }
    }

    // EFFECTS: collect rare loot. One of longsword or helmet.
    private void rareLoot() {
        System.out.println("You found rare loot!!");

        int rand = RANDOM.nextInt(2) + 1;
        if (rand == 1) {
            inventory.collect(itemFactory.makeLongsword(), player);
            System.out.println("You obtained a Longsword!");
        } else {
            inventory.collect(itemFactory.makeHelmet(), player);
            System.out.println("You obtained a Knight's Helmet!");
        }
    }

    // EFFECTS: collect "king" loot. One of excalibur or crown.
    private void kingLoot() {
        System.out.println("You find loot worth a king's ransom!!!");

        int rand = RANDOM.nextInt(2) + 1;
        if (rand == 1) {
            inventory.collect(itemFactory.makeExcalibur(), player);
            System.out.println("You obtained the Excalibur!");
        } else {
            inventory.collect(itemFactory.makeCrown(), player);
            System.out.println("You obtained the Crown!");
        }
    }

    // EFFECTS: display the players inventory if the user input is "i". Show
    // player stats, coins and items. Returns true if we did check inventory, false
    // if we did not.
    // All of this method's complexity is pretty formatting.
    private boolean checkInventory(String command) {
        if (command.equalsIgnoreCase("i")) {
            System.out.println("\nCoins: " + inventory.getCoins() + "    HP: " + player.getHealth()
                    + "    MaxHP: " + player.getMaxHP() + "    Damage: " + player.getDamage());

            for (Item item : inventory.getItems()) {
                adjustInventoryPrint(item);
            }
            System.out.print("Type to exit: ");
            input.nextLine();
            System.out.println("");
            return true;
        }
        return false;
    }

    // EFFECTS: adjusts the print of the list of items
    private void adjustInventoryPrint(Item item) {
        String stat = ((item instanceof Weapon) ? " dmg" : " MaxHP");
        if (item.getName().length() < 7) {
            System.out.print(item.getName() + "\t\t" + item.getStat());
        } else {
            System.out.print(item.getName() + "\t" + item.getStat());
        }
        if (item.getName().length() > 14 || item.getName().equals("Crown")) {
            System.out.print(stat + "\t" + item.getValue() + " value");
        } else {
            System.out.print(stat + "\t\t" + item.getValue() + " value");
        }
        if (item.getValue() >= 10) {
            System.out.print("\t" + item.getRefine() + " refine; \n");
        } else {
            System.out.print("\t\t" + item.getRefine() + " refine; \n");
        }
    }

    // EFFECTS: if input is "rest", choose betwen saving or loading
    private void checkRest(String command) {
        if (command.equalsIgnoreCase("rest")) {
            System.out.print("\nDo you wish to \u001B[1m'save'\u001B[0m or \u001B[1m'load'\u001B[0m? ");
            command = input.nextLine();
            if (command.equalsIgnoreCase("save")) {
                saveGame();
            } else if (command.equalsIgnoreCase("load")) {
                loadGame();
            } else {
                // do something?
            }
        }
    }

    // EFFECTS: save the game to a file
    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(player, roomHandler.getRoomNum());
            jsonWriter.close();
            System.out.println("Succesfully saved the game to " + JSON_STORE + "\n");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this, player, inventory
    // EFFECTS: load the game from a file
    private void loadGame() {
        try {
            player = new Player();
            inventory = player.getInventory();
            jsonReader.read(player, inventory, roomHandler);
            System.out.println("Succesfully loaded the game from " + JSON_STORE + "\n");
        } catch (IOException e) {
            System.out.println("Unable to read file from: " + JSON_STORE);
        }
    }

    // MODIFIES: this, player, inventory
    // EFFECTS: load cheats from a file
    private void lc(String command) {
        if (command.equals("gimmeItAll")) {
            JsonReader jsonCheat = new JsonReader("./data/catacombsCheat.json");
            try {
                player = new Player();
                inventory = player.getInventory();
                jsonCheat.read(player, inventory, roomHandler);
                System.out.println("Succesfully loaded the game from " + "./data/catacombsCheat.json" + "\n");
            } catch (IOException e) {
                System.out.println("Unable to read file from: " + JSON_STORE);
            }
        }
    }
}