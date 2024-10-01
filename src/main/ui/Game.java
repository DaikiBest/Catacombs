package ui;

import java.util.Random;

import model.BattleHandler;
import model.Inventory;
import model.Player;
import model.Goblin;
import model.Item;

// Represents a room and its encounters
public class Game {
    Random random;
    boolean notOver;
    BattleHandler battleHandler;

    // EFFECTS: create a room
    public Game() {
        Player player = new Player();
        Inventory inventory = player.getInventory();
        Goblin goblin = new Goblin();
        battleHandler = new BattleHandler();

        battleHandler.diceHandler(player, goblin, player.rollDice(), goblin.rollDice());

        Item item = inventory.getItem("Mace");

        // System.out.println(inventory.getItems());

        // while (notOver) {

        // }
    }

    // EFFECTS: determine and begin encounter
    public void nextEncounter() {
        //stub
    }

}
