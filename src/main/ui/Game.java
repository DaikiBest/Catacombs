package ui;

import java.util.Random;
import java.util.Scanner;

import model.BattleHandler;
import model.DarkWizard;
import model.GameCharacter;
import model.Goblin;
import model.Inventory;
import model.Item;
import model.ItemHandler;
import model.Orc;
import model.Player;
import model.ShopHandler;
import model.Weapon;

// Represents a room and its encounters
public class Game {
    private Player player;
    private Inventory inventory;
    private String notOver;
    private Scanner input;

    private ItemHandler itemHandler;
    private BattleHandler battleHandler;
    private ShopHandler shopHandler;

    private static final Random RANDOM = new Random();

    // EFFECTS: create a room
    public Game() {
        notOver = "true";
        player = new Player();
        inventory = player.getInventory();
        input = new Scanner(System.in);

        itemHandler = new ItemHandler();
        battleHandler = new BattleHandler();
        shopHandler = new ShopHandler();

        runGame();
    }

    // runs the game
    public void runGame() {
        int roomNumber = 0;
        while (notOver.equals("true")) {
            System.out.println("\nYou are in room " + roomNumber + ".");
            if (roomNumber % 5 == 0 && roomNumber != 0) { // crossroads
                crossRoads();
            } else {
                regularEncounter();
            }
            roomNumber++;
        }

        roomNumber--; // actual conquered roomNumber is one less

        if (notOver.equals("victory")) {
            System.out.println("\u001B[1m\nYou are victorious in your quest!");
        }
        System.out.println("\nGame Over");
        // End Game
    }

    public void regularEncounter() {
        String command = "inventory";
        while (command.equalsIgnoreCase("inventory")) {
            System.out.print("A rusty wooden door stands in your way... Do you wish to enter? ");
            command = input.nextLine();
            checkInventory(command);
        }
        nextEncounter();
    }

    // EFFECTS: determine and begin next encounter
    public void nextEncounter() {
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

    public void crossRoads() {
        boolean inCrossroads = true;

        while (inCrossroads) {
            System.out.println("You find yourself at a crossroads..."
                    + "\nTo your \u001B[1m'left'\u001B[0m, a familiar wooden door. "
                    + "To your \u001B[1m'right'\u001B[0m, a foreboding magical door oozes with dark, mystical power.");
            String command = input.nextLine();
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
    // EFFECTS: begin battle encounter. Enemy and player roll their dice, and damage
    // is dealt accordingly.
    public void enemyEncounter(GameCharacter enemy) {
        boolean inBattle = true;

        do {
            System.out.println("\nHP: " + player.getHealth() + "  DMG: " + player.getDamage()
                    + "      " + enemy.getName() + " HP: " + enemy.getHealth() + "  "
                    + enemy.getName() + " DMG: " + enemy.getDamage());

            // enemy turn
            int enemyRoll = enemy.rollDice();
            System.out.print("Enemy rolled a " + enemyRoll + ". Roll your dice "
                    + "or \u001B[1m'flee'\u001B[0m! ");

            // player turn
            String command = input.nextLine();
            if (command.equalsIgnoreCase("flee")) {
                if (flee(enemy)) {
                    break;
                }

            } else {
                int playerRoll = player.rollDice();
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
    // EFFECTS: attempt to flee and avoid enemyEncounter
    public boolean flee(GameCharacter enemy) {
        int rand = RANDOM.nextInt(2) + 1;
        System.out.println(rand);

        if (rand == 1) { // 50%
            System.out.println("\nYou duck and weave past the enemy. You manage to escape into the next room!");
            return true; //end flee action
        } else { // 50%
            int hit = enemy.getDamage() / 2;
            hit = ((hit == 0) ? 1 : hit); //take at least 1 damage

            player.takeDamage(hit); // take half of enemy damage, rounded down; or 1 if 0.
            System.out.print("\nIn your attempt to flee, the enemy stops you in your tracks. ");
            System.out.print("You take " + hit + " damage.\n");
            if (checkPlayerState()) { //is the player dead?
                return true; //end flee action
            }
            return false; //stay in combat
        }
    }

    // MODIFIES: this
    // EFFECTS: Determine the outcome of the round of the battle
    public boolean battleOutcome(String outcome, GameCharacter enemy) {
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
    public boolean checkPlayerState() {
        if (!player.getAlive()) {
            System.out.println("You have fallen in battle... ");
            notOver = "defeat";
            return true;
        }
        return false;
    }

    // EFFECTS: Handles the shop encounter.
    public void shopEncounter() {
        boolean shopping = true;

        while (shopping) {
            System.out.print("'Here to \u001B[1m'buy'\u001B[0m or \u001B[1m'sell'\u001B[0m?"
                    + ". Say \u001B[1m'exit'\u001B[0m whenever you are finished here.' ");
            String command = input.nextLine();
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

    public void buy() {
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

    public boolean handlePurchase(String purchase) {
        if (purchase.equals("dagger")) {
            return shopHandler.purchaseItem(itemHandler.makeDagger(), player);
        } else if (purchase.equals("mace")) {
            return shopHandler.purchaseItem(itemHandler.makeMace(), player);
        } else if (purchase.equals("longsword")) {
            return shopHandler.purchaseItem(itemHandler.makeLongsword(), player);
        } else if (purchase.equals("excalibur")) {
            return shopHandler.purchaseItem(itemHandler.makeExcalibur(), player);
        } else if (purchase.equals("farmer's cap")) {
            return shopHandler.purchaseItem(itemHandler.makeCap(), player);
        } else if (purchase.equals("thieve's hood")) {
            return shopHandler.purchaseItem(itemHandler.makeHood(), player);
        } else if (purchase.equals("knight's helmet")) {
            return shopHandler.purchaseItem(itemHandler.makeHelmet(), player);
        } else if (purchase.equals("crown")) {
            return shopHandler.purchaseItem(itemHandler.makeCrown(), player);
        } else if (purchase.equals("heal")) {
            return shopHandler.purchaseHealing(player);
        } else {
            return false;
        }
    }

    public void sell() {
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

    public void printShopData() {
        System.out.println("\nCoins: " + inventory.getCoins() + "    HP: " + player.getHealth()
                + "    MaxHP: " + player.getMaxHP() + "    Damage: " + player.getDamage());
        System.out.println("\n\t\t\t   \u001B[1m'Heal'\u001B[0m 1 hp: 2 coins\n"
                + "\u001B[1m'Dagger'\u001B[0m 1 dmg: 2 coins\t\t\t\t"
                + "\u001B[1m'Farmer's Cap'\u001B[0m 7 MaxHP: 4 coins"
                + "\n\u001B[1m'Mace'\u001B[0m 2 dmg: 4 coins\t\t\t\t"
                + "\u001B[1m'Thieve's Hood'\u001B[0m 9 MaxHP: 8 coins"
                + "\n\u001B[1m'Longsword'\u001B[0m 3 dmg: 10 coins\t\t\t"
                + "\u001B[1m'Knight's Helmet'\u001B[0m 12 MaxHP: 12 coins"
                + "\n\u001B[1m'Excalibur'\u001B[0m 5 dmg: 20 coins\t\t\t"
                + "\u001B[1m'Crown'\u001B[0m 15 MaxHP: 20 coins\n");
    }

    // EFFECTS: find a chest with loot
    public void lootEncounter() {
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

    public void commonLoot() {
        System.out.println("You found common loot!");

        int rand = RANDOM.nextInt(3) + 1;
        if (rand == 1) {
            inventory.collect(itemHandler.makeMace(), player);
            System.out.println("You obtained a Mace!");
        } else if (rand == 2) {
            inventory.collect(itemHandler.makeCap(), player);
            System.out.println("You obtained a Farmer's Cap!");
        } else {
            inventory.collect(itemHandler.makeHood(), player);
            System.out.println("You obtained a Thieve's Hood!");
        }
    }

    public void rareLoot() {
        System.out.println("You found rare loot!!");

        int rand = RANDOM.nextInt(2) + 1;
        if (rand == 1) {
            inventory.collect(itemHandler.makeLongsword(), player);
            System.out.println("You obtained a Longsword!");
        } else {
            inventory.collect(itemHandler.makeHelmet(), player);
            System.out.println("You obtained a Knight's Helmet!");
        }
    }

    public void kingLoot() {
        System.out.println("You find loot worth a king's ransom!!!");

        int rand = RANDOM.nextInt(2) + 1;
        if (rand == 1) {
            inventory.collect(itemHandler.makeExcalibur(), player);
            System.out.println("You obtained the Excalibur!");
        } else {
            inventory.collect(itemHandler.makeCrown(), player);
            System.out.println("You obtained the Crown!");
        }
    }

    public void checkInventory(String command) {
        if (command.equals("inventory")) {
            System.out.println("\nCoins: " + inventory.getCoins() + "    HP: " + player.getHealth()
                    + "    MaxHP: " + player.getMaxHP() + "    Damage: " + player.getDamage());

            String stat;
            for (Item item : inventory.getItems()) {
                stat = ((item instanceof Weapon) ? " dmg" : " MaxHP");

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
        }
    }
}