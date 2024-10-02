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
        

        while (notOver) {
            System.out.println(inventory.getCoins());
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
            GameCharacter goblin = new Goblin();
            enemyEncounter(goblin);

        } else if (next <= 8) { //5-8 40%
            GameCharacter orc = new Orc();
            enemyEncounter(orc);

        } else { //9 or 10 20%
            shopEncounter();
        }
    }

    // EFFECTS: begin battle encounter
    public void enemyEncounter(GameCharacter enemy) {
        boolean inBattle = true;

        while (inBattle) {
            System.out.println("HP: " + player.getHealth() + "  DMG: " + player.getDamage() +
                             "      " + enemy.getName() + " HP: " + enemy.getHealth() + "  " 
                             + enemy.getName() + " DMG: " + enemy.getDamage());

            //enemy roll
            int enemyRoll = enemy.rollDice();
            System.out.println("Enemy rolled a " + enemyRoll);

            //player roll
            System.out.println("Roll your dice!");
            input.next();
            int playerRoll = player.rollDice();

            //outcome
            String outcome = battleHandler.diceHandler(player, enemy, playerRoll, enemyRoll);
            System.out.print("You rolled a " + playerRoll + "!");
            if (outcome.equals("win")) {
                System.out.println(" The " + enemy.getName() + " takes a hit.");
                if (!enemy.getAlive()) {
                    System.out.println(enemy.getName() + " has fallen... ");
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
        }

    }

    // EFFECTS: end battle encounter

    // EFFECTS: enter shop
    public void shopEncounter() {
        //stub
    }

}
