package ui;

import model.Inventory;
import model.Item;
import model.Player;
import model.RoomHandler;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

//Represents the rooms where the game takes place
public class Room extends JFrame {
    private JFrame frame;
    private JLayeredPane roomsLayered;

    private JPanel hudPanel;
    private JButton inventoryButton;
    private JLabel playerInfo;

    private JPanel inventoryPanel; // a separate panel to display the player's inventory
    private JTable inventoryTable;

    private Door door;
    private Crossroads crossroads;
    private Encounter encounter;
    private Shop shop;
    private Loot loot;

    private static final int WIDTH = 900;
    private static final int HEIGHT = 700;
    private static final String TAB = "       ";
    private static final Color GRAY = new Color(153, 153, 153);
    private static final Font FONT = new Font("Arial", Font.PLAIN, 18);

    // private Shop shop;
    // private Encounter encounter;

    // EFFECTS: Creates the room JFrame
    public Room(Player player, GameGUI game, RoomHandler roomHandler) {
        createRoom(player, game, roomHandler);
    }

    // EFFECTS: creates the room and
    private void createRoom(Player player, GameGUI game, RoomHandler roomHandler) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(Room.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Catacombs");
        frame.setResizable(false);

        roomsLayered = new JLayeredPane();
        frame.add(roomsLayered);

        addHud(player, roomHandler.getRoomNum());
        hudPanel.setBackground(GRAY);
        setupRooms(game, player, roomHandler);

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
        addInventoryButton(player.getInventory());
    }

    // MODIFIES: this
    // EFFECTS: adds the player data (a label) to the hud
    private void addPlayerData(Player player, int roomNum) {
        playerInfo = new JLabel("Room #: " + roomNum + TAB + "HP: " + player.getHealth()
                + TAB + "MaxHP: " + player.getMaxHP() + TAB + "DMG: " + player.getDamage() + TAB
                + "Coins: " + player.getInventory().getCoins());
        playerInfo.setFont(FONT);
        hudPanel.add(playerInfo, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: updates the player data
    public void updatePlayerData(Player player, int roomNum) {
        playerInfo.setText("Room #: " + roomNum + TAB + "HP: " + player.getHealth()
                + TAB + "MaxHP: " + player.getMaxHP() + TAB + "DMG: " + player.getDamage() + TAB
                + "Coins: " + player.getInventory().getCoins());
        addItemsToTable(player.getInventory());
    }

    // MODIFIES: this
    // EFFECTS: creates and adds the inventory button to the hud
    private void addInventoryButton(Inventory inventory) {
        inventoryButton = new JButton();
        inventoryButton.setFocusable(false);
        inventoryButton.setBorder(BorderFactory.createEmptyBorder());

        Image originalImage = new ImageIcon("./images/backpack.png").getImage();
        Image scaledInv = originalImage.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon inventoryImg = new ImageIcon(scaledInv);

        inventoryButton.setIcon(inventoryImg);
        hudPanel.add(inventoryButton, BorderLayout.EAST);

        addInventoryInfo(inventory);
    }

    // MODIFIES: THIS
    // EFFECTS: add the inventory table
    private void addInventoryInfo(Inventory inventory) {
        inventoryPanel = new JPanel();
        frame.add(inventoryPanel, BorderLayout.EAST);
        inventoryPanel.setBackground(new Color(102, 102, 102));

        inventoryTable = new JTable();
        JScrollPane sp = new JScrollPane(inventoryTable);
        sp.setPreferredSize(new Dimension(430, 580));
        inventoryPanel.add(sp);

        addItemsToTable(inventory);

        inventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inventoryPanel.isVisible()) {
                    inventoryPanel.setVisible(false);
                } else {
                    inventoryPanel.setVisible(true);
                }
            }
        });
        inventoryPanel.setVisible(false);
    }

    // MODIFIES: THIS
    // EFFECTS: create the model of the table and add the columns and rows.
    private void addItemsToTable(Inventory inventory) {
        DefaultTableModel model = new DefaultTableModel() { // make the table non-editable
            @Override
            public boolean isCellEditable(int row, int column) {
                // all cells false
                return false;
            }
        };
        model.addColumn("Damage");
        model.addColumn("Stat");
        model.addColumn("Value");
        model.addColumn("Refinement");

        addRows(inventory, model);

        inventoryTable.setModel(model);
    }

    // MODIFIES: THIS
    // EFFECTS: add each item's data to a row (vector). Then add each row to the
    // table.
    private void addRows(Inventory inventory, DefaultTableModel model) {
        Vector<String> row;
        int numItems = inventory.getItems().size();
        for (int j = 0; j < numItems; j++) { // loop for each item
            row = new Vector<>();
            for (int i = 0; i < 4; i++) { // loop for the data of each item
                Item item = inventory.getItems().get(j); // get current item
                String val = "";
                if (i == 0) {
                    val = item.getName(); // item name
                } else if (i == 1) {
                    val = String.valueOf(item.getStat()); // item stat
                } else if (i == 2) {
                    val = String.valueOf(item.getValue()); // item value
                } else {
                    val = String.valueOf(item.getRefine()); // item refine
                }
                row.add(val);
            }
            model.addRow(row);
        }
    }

    // EFFECTS: create all rooms
    private void setupRooms(GameGUI game, Player player, RoomHandler roomHandler) {
        door = new Door(roomsLayered, game);
        crossroads = new Crossroads(roomsLayered, game);
        encounter = new Encounter(roomsLayered);
        shop = new Shop(roomsLayered, game);
        loot = new Loot(roomsLayered, player, game, frame);
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
        encounter.begin();
    }

    public void beginShop() {
        shop.begin();
    }

    public void beginLoot() {
        loot.begin();
    }

    // MODIFIES: this
    // EFFECTS: creates a new room (resets). Used when loading from a file.
    public Room resetRooms(Player player, GameGUI game, RoomHandler roomHandler) {
        frame.dispose();
        createRoom(player, game, roomHandler);
        toDoor();
        return this;
    }
}
