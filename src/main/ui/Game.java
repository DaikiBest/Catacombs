package ui;

import java.util.Random;

import model.Inventory;
import model.Player;

// Represents a room and its encounters
public class Game {
    Random random;
    boolean notOver;

    // EFFECTS: create a room
    public Game() {
        Player player = new Player();
        Inventory inventory = player.getInventory();

        System.out.println(inventory.getItems());

        while (notOver) {
            
        }
    }

    // EFFECTS: determine and begin encounter
    public void nextEncounter() {
        //stub
    }

}
