package ui;

import model.Player;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

//Represents the rooms where the game takes place
public class Room extends JFrame {
    private JFrame frame;
    private JLayeredPane roomsLayered;

    private JPanel hudPanel;
    private JButton inventoryButton;
    private JLabel playerInfo;

    private Door door;
    private Crossroads crossroads;
    private Encounter encounter;
    private Shop shop;
    private Loot loot;

    private static final int WIDTH = 900;
    private static final int HEIGHT = 700;
    
    // private Shop shop;
    // private Encounter encounter;

    // EFFECTS: Creates the room JFrame
    public Room(Player player, int roomNum, GameGUI game) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(Room.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Catacombs");
        frame.setResizable(false);

        roomsLayered = new JLayeredPane();
        frame.add(roomsLayered);
        
        addHud(player, roomNum);
        setupRooms(game);
        
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds the inventory button on top right of the screen
    private void addHud(Player player, int roomNum) {
        hudPanel = new JPanel();
        hudPanel.setLayout(new BorderLayout(30, 30));
        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        hudPanel.setBorder(padding);
        frame.add(hudPanel, BorderLayout.NORTH);

        addPlayerData(player, roomNum);
        addInventoryButton();
    }

    // EFFECTS: 
    private void addPlayerData(Player player, int roomNum) {
        String tab = "       ";
        playerInfo = new JLabel("Room Number: " + roomNum + tab + "Health: " + player.getHealth()
                + tab + "Damage: " + player.getDamage());
        playerInfo.setFont(new Font("Arial", Font.PLAIN, 18));
        hudPanel.add(playerInfo, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: creates and adds the inventory button to the hud
    private void addInventoryButton() {
        inventoryButton = new JButton();
        inventoryButton.setFocusable(false);
        inventoryButton.setBorder(BorderFactory.createEmptyBorder());

        Image originalImage = new ImageIcon("./images/backpack.png").getImage();
        Image scaledInv = originalImage.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        ImageIcon inventory = new ImageIcon(scaledInv);

        inventoryButton.setIcon(inventory);
        hudPanel.add(inventoryButton, BorderLayout.EAST);
    }

    // EFFECTS: create all rooms
    public void setupRooms(GameGUI game) {
        door = new Door(roomsLayered, game);
        crossroads = new Crossroads(roomsLayered, game);
        encounter = new Encounter(roomsLayered);
        shop = new Shop(roomsLayered);
        loot = new Loot(roomsLayered);
    }

    public void toDoor() {
        door.begin();
    }

    public void exitDoor() {
        door.end();
    }

    public void toCrossroads() {
        crossroads.begin();
    }

    public void exitCrossroads() {
        crossroads.end();
    }

    public void beginEncounter() {

    }

    public void beginShop() {

    }

    public void beginLoot() {
        loot.begin();
    }
}
