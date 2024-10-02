package ui;

import java.util.Random;

import model.BattleHandler;
import model.Goblin;
import model.Inventory;
import model.Item;
import model.ItemHandler;
import model.Player;

// Represents a room and its encounters
public class Game {
    Random random;
    boolean notOver;
    BattleHandler battleHandler;
    ItemHandler itemHandler;
    public Player player;
    public Inventory inventory;


    // EFFECTS: create a room
    public Game() {
        player = new Player();
        inventory = player.getInventory();
        Goblin goblin = new Goblin();
        battleHandler = new BattleHandler();
        itemHandler = new ItemHandler();
        

        Item mace = itemHandler.makeMace();
        inventory.collect(mace, player);

        battleHandler.diceHandler(player, goblin, player.rollDice(), goblin.rollDice());

        Item item = inventory.getItem("Mace");

        // System.out.println(inventory.getItems());

        while (notOver) {
            nextEncounter();
        }
    }

    // EFFECTS: determine and begin next encounter
    public void nextEncounter() {
        int next = random.nextInt(9) + 1;
        if (next <= 4) { //1-4 40%
            goblinEncounter();

        } else if (next <= 8) { //5-8 40%
            orcEncounter();

        } else { //9 or 10 20%
            shopEncounter();
        }
    }

    // EFFECTS: begin battle against Goblin
    public void goblinEncounter() {
        Goblin goblin = new Goblin()
        while (inBattle) {
            int enemyRoll = enemy.rollDice();
            int playerRoll = player.rollDice();
        }

    }
    // EFFECTS: handle the back and forth battle between player and enemy.
    public void battleHanddler(GameCharacter player, GameCharacter enemy) {
        while (inBattle) {
            enemyRoll = enemy.rollDice();
            // player rolls their dice
            playerRoll = player.rollDice();
            this.diceHandler(player, enemy);
        }
    }

    // EFFECTS: begin battle against Orc
    public void orcEncounter() {
        //stub
    }

    // EFFECTS: begin battle against Dark Wizard
    public void darkWizardEncounter() {
        //stub
    }

    // EFFECTS: enter shop
    public void shopEncounter() {
        //stub
    }

}
