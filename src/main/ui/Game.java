package ui;

import java.util.Random;
import java.util.Scanner;

import model.BattleHandler;
import model.GameCharacter;
import model.Goblin;
import model.Inventory;
import model.Item;
import model.ItemHandler;
import model.Orc;
import model.Player;

// Represents a room and its encounters
public class Game {
    private BattleHandler battleHandler;
    private ItemHandler itemHandler;
    private Player player;
    private Inventory inventory;
    private Scanner input;
    private boolean notOver;


    // EFFECTS: create a room
    public Game() {
        input = new Scanner(System.in);
        notOver = true;
        player = new Player();
        inventory = player.getInventory();
        battleHandler = new BattleHandler();
        itemHandler = new ItemHandler();
        
        int roomNumber = 0;//TODO
        while (notOver) {
            System.out.println("You have " + inventory.getCoins() + " coins. You are in room " + roomNumber++);//TODO
            System.out.print("A door stands in your way... Do you wish to enter? ");
            input.nextLine();
            nextEncounter();
        }

        System.out.println("Game Over");
        //End Game
    }

    // EFFECTS: determine and begin next encounter
    public void nextEncounter() {
        Random random = new Random();

        int next = random.nextInt(9) + 1;
        if (next <= 4) { //1-4 40%
            System.out.println("As you enter the next room, a sneaky Goblin lunges at you!");
            GameCharacter goblin = new Goblin();
            enemyEncounter(goblin);

        } else if (next <= 7) { //5-7 30%
            System.out.println("You enter the next room and hear heavy grunting. An Orc readies his sword.");
            GameCharacter orc = new Orc();
            enemyEncounter(orc);

        } else if (next == 8) { //only 8 10%
            System.out.println("An ominous chest rests at the center of the room...");
            lootEncounter();

        } else { //9 or 10 20%
            System.out.println("A man sits cross-legged on the ground. Scraps of Weapons and Armors are laid out neatly for display. 'Welcome traveller!'");
            shopEncounter();
        }
    }

    // EFFECTS: begin battle encounter. Enemy and player roll their dice, and damage is dealt accordingly
    public void enemyEncounter(GameCharacter enemy) {
        boolean inBattle = true;

        do {
            System.out.println("HP: " + player.getHealth() + "  DMG: " + player.getDamage() +
                             "      " + enemy.getName() + " HP: " + enemy.getHealth() + "  " 
                             + enemy.getName() + " DMG: " + enemy.getDamage());

            //enemy roll
            int enemyRoll = enemy.rollDice();
            System.out.println("Enemy rolled a " + enemyRoll + ". Roll your dice!");


            //player roll
            input.nextLine();
            int playerRoll = player.rollDice();

            //outcome
            String outcome = battleHandler.diceHandler(player, enemy, playerRoll, enemyRoll);
            System.out.print("You rolled a " + playerRoll + "!");
            if (outcome.equals("win")) {
                System.out.println(" The " + enemy.getName() + " takes a hit.");
                if (!enemy.getAlive()) {
                    System.out.println(enemy.getName() + " has fallen... You move on...");
                    battleHandler.giveRewards(inventory, enemy);
                    break;
                }

            } else if (outcome.equals("lose")) {
                System.out.println(" You take a hit");
                if (!player.getAlive()) {
                    System.out.println("You have fallen in battle... ");
                    notOver = false;
                    break;
                }

            } else {
                System.out.println(" You tie and your attacks rebound!");
            }
        } while(inBattle);

    }

    // EFFECTS: enter shop
    public void shopEncounter() {
        for (int i = 0; i<inventory.getCoins(); i++){//TODO
            player.heal();
            inventory.deductCoins(2);
            if (player.getHealth()==player.getMaxHP()) {
                break;
            }
        }
    }

    // EFFECTS: find a chest with loot
    public void lootEncounter() { //TODO
        Random random = new Random();

        int next = random.nextInt(19) + 1;
        if (next <= 12) { //1-12 60%
            System.out.println("You find a bundle of coins. You collect ..." + "coins");

        } else if (next <= 16) { //13-16 20%
            System.out.println("You find common loot!");

        } else if (next <= 19) { //17-19 15%
            System.out.println("You find rare loot!!");

        } else { //only 20 5% 
            System.out.println("You find loot worth a king's ransom!!! You collect ...");

        }
    }

}