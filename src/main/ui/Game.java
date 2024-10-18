package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import persistence.JsonReader;
import persistence.JsonWriter;
import model.*;

// Represents the game; handles the room by room progression and encounters.
public class Game {
    private Player player;
    private Inventory inventory;
    private String notOver;
    private Scanner input;

    private RoomHandler roomHandler;
    private ItemFactory itemFactory;
    private BattleHandler battleHandler;
    private ShopHandler shopHandler;

    private static final String JSON_STORE = "./data/catacombs.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private static final Random RANDOM = new Random();

    // EFFECTS: begin the game
    public Game() {
        roomHandler = new RoomHandler();
        itemFactory = new ItemFactory();
        battleHandler = new BattleHandler();
        shopHandler = new ShopHandler();

        player = new Player();
        inventory = player.getInventory();
        inventory.collect(itemFactory.makeDagger(), player); //start with a dagger
        notOver = "true";
        input = new Scanner(System.in);

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

    // EEFECTS: run the game. Player progresses encounter by encounter, and every 5 rooms enters a crossroads.
    // When the loop is broken (notOver not equals the string "true"), then it means that the game must end and
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
    }

    // EFFECTS: move to the next "room" and begin the encounter
    private void regularEncounter() {
        String command = "i"; //move to next room, unless player inputs i (to check their inventory)
        while (command.equalsIgnoreCase("i") | command.equalsIgnoreCase("rest")) {
            System.out.print("A rusty wooden door stands in your way... Type to enter, or " 
                            + "\u001B[1m'rest'\u001B[0m (save/load game). ");
            command = input.nextLine();
            checkInventory(command);
            checkRest(command);
        }
        nextEncounter();
    }

    // EFFECTS: determine the type of encounter: either a goblin, orc, chest, or shop.
    private void nextEncounter() {
        int next = RANDOM.nextInt(10) + 1;
        if (next <= 4) { // 1-4 40%
            System.out.println("\nAs you enter the next room, a sneaky Goblin lunges at you!");
            GameCharacter goblin = new Goblin();
            enemyEncounter(goblin);

        } else if (next <= 7) { // 5-7 30%
            System.out.println("\nYou enter the next room and hear heavy grunting. An Orc readies his sword.");
            GameCharacter orc = new Orc();
            enemyEncounter(orc);

        } else if (next == 8) { // only 8: 10%
            System.out.println("\nAn ominous chest rests at the center of the room...");
            lootEncounter();

        } else { // 9-10 20%
            System.out.println("\nA man sits cross-legged on the ground. Scraps of Weapons and"
                    + " Armors are laid out neatly for display. 'Welcome traveller!'\n");
            shopEncounter();
        }
    }

    // EFFECTS: the player chooses between a regular encounter (left), or to confront the final boss (right).
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
                GameCharacter darkWizard = new DarkWizard();
                enemyEncounter(darkWizard);
                inCrossroads = false;
            }
        }
    }

    // MODIFIES: player, enemy, inventory
    // EFFECTS: begin battle encounter. Enemy and player roll their dice, and damage is dealt accordingly. 
    // The player is also allowed to attempt to avoid the encounter by fleeing, at the risk of taking damage.
    private void enemyEncounter(GameCharacter enemy) {
        boolean inBattle = true;

        do {
            System.out.println("\nHP: " + player.getHealth() + "  DMG: " + player.getDamage()
                    + "      " + enemy.getName() + " HP: " + enemy.getHealth() + "  "
                    + enemy.getName() + " DMG: " + enemy.getDamage());

            // enemy turn
            int enemyRoll = enemy.rollDice(RANDOM.nextLong());
            System.out.print("Enemy rolled a " + enemyRoll + ". Roll your dice "
                    + "or \u001B[1m'flee'\u001B[0m! ");

            // player turn
            String command = input.nextLine();
            if (command.equalsIgnoreCase("flee")) {
                if (flee(enemy)) {
                    break;
                }

            } else {
                int playerRoll = player.rollDice(RANDOM.nextLong());
                String outcome = battleHandler.diceHandler(player, enemy, playerRoll, enemyRoll);
                System.out.print("You rolled a " + playerRoll + "!");

                // outcome
                boolean endBattle = battleOutcome(outcome, enemy);
                if (endBattle) {
                    break;
                }
            }
        } while (inBattle);
    }

    // MODIFIES: player
    // EFFECTS: attempt to flee and avoid enemyEncounter. If successful, player escapes enemy encounter. If
    // failed, the player takes half the enemy damage and remains at the enemy encounter.
    private boolean flee(GameCharacter enemy) {
        int rand = RANDOM.nextInt(4) + 1;

        if (rand <= 3) { // 75%
            System.out.println("\nYou duck and weave past the enemy. You manage to escape into the next room!");
            return true; //end flee action
        } else { // 25%
            int hit = enemy.getDamage() / 2;
            hit = ((hit == 0) ? 1 : hit); //take at least 1 damage

            player.takeDamage(hit); // take half of enemy damage, rounded down; or 1 if 0.
            System.out.print("\nIn your attempt to flee, the enemy stops you in your tracks. ");
            System.out.print("You take " + hit + " damage.\n");
            return checkPlayerState();
        }
    }

    // MODIFIES: this
    // EFFECTS: Determine the outcome of the round of the battle. Either the player or enemy take damage; if the
    // damage drops the player's health to zero, then they die, ending the encounter, and eventually ending the game.
    // If the enemy's health drops to zero, the enemy dies ending the encounter, and the player is rewarded coins.
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
            System.out.print("'Here to \u001B[1m'buy'\u001B[0m or \u001B[1m'sell'\u001B[0m?"
                    + ". Say \u001B[1m'exit'\u001B[0m whenever you are finished here.' ");
            String command = input.nextLine().trim();
            checkInventory(command);

            if (command.equalsIgnoreCase("buy")) {
                buy();
            } else if (command.equalsIgnoreCase("sell")) {
                sell();
            } else if (command.equalsIgnoreCase("exit")) {
                shopping = false;
                break;
            }
        }
    }

    // MODIFIES: inventory, player
    // EFFECTS: player gets to choose an item to purchase in exchange for coins collected. Otherwise, player
    // can go back to choose a different option (buy, sell, exit).
    private void buy() {
        boolean buying = true;

        while (buying) {
            printShopData();

            System.out.print("Enter your purchase; otherwise, say \u001B[1m'back'\u001B[0m: ");
            String purchase = input.nextLine();
            purchase = purchase.toLowerCase().trim();
            checkInventory(purchase);

            if (purchase.equalsIgnoreCase("inventory")) {
                // dont do anything
            } else if (purchase.equalsIgnoreCase("back")) {
                buying = false;
                break;
            } else if (shopHandler.getShopList().contains(purchase.toLowerCase())) {
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

    // MODIFIES: inventory, player
    // EFFECTS: Purchase item given the player's input
    private boolean handlePurchase(String purchase) {

        Item item = itemFactory.makeItem(purchase);
        if (item != null) {
            return shopHandler.purchaseItem(item, player);
        } else if (purchase.equals("heal")) {
            return shopHandler.purchaseHealing(player);
        }
        return false;
    }

    // MODIFIES: inventory, player
    // EFFECTS: player gets to choose to sell an item they have in their inventory for coins.
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
            }

            for (Item item : inventory.getItems()) {

                if (item.getName().equalsIgnoreCase(command)) { // check that item is in player's inventory
                    boolean status = shopHandler.sellItem(command, player);

                    if (status) { // sold item succesfully
                        System.out.println("\n'This will make a fine addition to my collection.'");
                        System.out.println("You sold your " + command + "! \n");
                    } else {
                        System.out.println("That's your last weapon. You can't sell that.\n");
                    }
                    break;
                }
            }
        }
    }

    // EFFECTS: display all items and healing for sale and some player details such as coin count.
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

    // EFFECTS: find a chest with randomized loot (either coins or a random item of different "rarity")
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

    // EFFECTS: display the players inventory if the user input is "inventory". Show player stats, coins and items.
    // All of this method's complexity is pretty formatting.
    private void checkInventory(String command) {
        if (command.equalsIgnoreCase("i")) {
            System.out.println("\nCoins: " + inventory.getCoins() + "    HP: " + player.getHealth()
                    + "    MaxHP: " + player.getMaxHP() + "    Damage: " + player.getDamage());

            for (Item item : inventory.getItems()) {
                String stat = ((item instanceof Weapon) ? " dmg" : " MaxHP");

                if (item.getName().length() < 7) {
                    System.out.print(item.getName() + "\t\t" + item.getStat());
                } else {
                    System.out.print(item.getName() + "\t" + item.getStat());
                }

                if (item.getName().length() > 14 || item.getName().equals("Crown")) {
                    System.out.print(stat + "\t" + item.getValue() + " value; \n");
                } else {
                    System.out.print(stat + "\t\t" + item.getValue() + " value; \n");
                }
            }
            System.out.print("Type to exit: ");
            input.nextLine();
            System.out.println("");
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
            jsonWriter.write(player, roomHandler);
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
}