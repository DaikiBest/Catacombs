package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Inventory;
import model.Item;
import model.ItemFactory;
import model.Player;
import model.RoomHandler;
import model.ShopHandler;
import model.Weapon;

// Represents a shop encounter
public class Shop extends RoomPanel implements ActionListener {
    private JButton exitButton;
    private JButton openButton;
    private JLabel gary;

    private JPanel shopPanel;
    private JButton buyButton;
    private JButton sellButton;
    private JButton refineButton;
    private JButton backButton;

    private JPanel shopButtonsPanel;
    private CardLayout cl;

    private JPanel shopMenu;
    private JPanel buyPanel;
    private JPanel sellPanel;
    private JPanel refinePanel;
    private JButton itemButton;

    private Player player;
    private Inventory inventory;
    private ShopHandler shopHandler = new ShopHandler();
    private ItemFactory itemFactory = new ItemFactory();
    private Room room;
    private int roomNum;

    private static final String TAB = "       ";
    private static final Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 18);
    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_HEIGHT = 50;
    private static final int SHOP_MENU_WIDTH = 500;
    private static final int SHOP_MENU_HEIGHT = 410;
    private static final int SHOP_BUTTON_HEIGHT = 35;

    // EFFECTS: Create a shop with the open and exit buttons, as well as the shop
    // image and menu
    public Shop(JLayeredPane roomsLayered, GameGUI game, Player player, Room room, RoomHandler roomHandler) {
        super(roomsLayered);
        this.player = player;
        inventory = player.getInventory();
        this.room = room;

        panel.setLayout(null);

        // create the shop menu
        shopPanel = new JPanel();
        shopPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        shopPanel.setLayout(new BorderLayout());
        shopPanel.setBounds(200, 150, SHOP_MENU_WIDTH, SHOP_MENU_HEIGHT);
        createMenu();

        panel.add(shopPanel);

        // create the image of the shopkeeper
        createShopkeep();
        panel.add(gary);
        gary.setBounds(190, 40, 540, 400);

        // create the openShop button
        openButton = createOpenButton(game);
        panel.add(openButton);

        // create the exit button
        exitButton = createExitButton(game);
        panel.add(exitButton);

        openButton.setVisible(true);
        shopPanel.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: Create the shopkeeper image
    private void createShopkeep() {
        Image originalImage = new ImageIcon("./images/shop.png").getImage();
        Image scaledImg = originalImage.getScaledInstance(540, 400, Image.SCALE_SMOOTH);
        ImageIcon shopKeep = new ImageIcon(scaledImg);

        gary = new JLabel(shopKeep);
    }

    // MODIFIES: this
    // EFFECTS: creates exit button
    private JButton createExitButton(GameGUI game) {
        exitButton = new JButton("Exit");
        exitButton.setBounds(50, 490, BUTTON_WIDTH, BUTTON_HEIGHT);
        exitButton.setFont(BUTTON_FONT);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                end(game);
            }
        });

        return exitButton;
    }

    // MODIFIES: this
    // EFFECTS: creates open shop button
    private JButton createOpenButton(GameGUI game) {
        openButton = new JButton("Open Shop");
        openButton.setBounds(400, 400, BUTTON_WIDTH, BUTTON_HEIGHT);
        openButton.setFont(BUTTON_FONT);

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shopPanel.setVisible(true);
                openButton.setVisible(false);
            }
        });

        return openButton;
    }

    // MODIFIES: this
    // EFFECTS: creates the shop menu
    private void createMenu() {
        shopButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        createBuyButton();
        createSellButton();
        createRefineButton();
        createBackButton();

        cl = new CardLayout();
        shopMenu = new JPanel(cl);

        createBuyMenu();
        createSellMenu();
        createRefineMenu();

        JScrollPane buySP = new JScrollPane(buyPanel);
        JScrollPane sellSP = new JScrollPane(sellPanel);
        JScrollPane refineSP = new JScrollPane(refinePanel);

        shopMenu.add(buySP, "buy");
        shopMenu.add(sellSP, "sell");
        shopMenu.add(refineSP, "refine");

        shopPanel.add(shopMenu);
        shopPanel.add(shopButtonsPanel, BorderLayout.NORTH);
    }

    // EFFECTS: creates the buying menu
    private void createBuyMenu() {
        buyPanel = new JPanel();
        buyPanel.setBackground(new Color(51, 51, 51));
        buyPanel.setLayout(new BoxLayout(buyPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < shopHandler.getShopList().size(); i++) {
            String itemName = shopHandler.getShopList().get(i);

            if (itemName.equalsIgnoreCase("Heal")) { // Heal
                itemButton = createShopButton(itemName + TAB + shopHandler.getHealPrice() + " coins");
            } else {
                Item item = itemFactory.makeItem(itemName);
                if (item instanceof Weapon) { // Weapon
                    itemButton = createShopButton(itemName + TAB + item.getStat() + " DMG" + TAB
                            + item.getValue() * 2 + " coins");
                } else { // Armor
                    itemButton = createShopButton(itemName + TAB + item.getStat() + " MaxHP" + TAB
                            + item.getValue() * 2 + " coins");
                }
            }
            itemButton.setName("b" + i); // b is classifier (buy), i specifies the item index in shoplist
            buyPanel.add(itemButton);
        }
        buyPanel.add(Box.createVerticalGlue());
    }

    // EFFECTS: creates the selling menu
    private void createSellMenu() {
        sellPanel = new JPanel();
        sellPanel.setBackground(new Color(51, 51, 51));
        sellPanel.setLayout(new BoxLayout(sellPanel, BoxLayout.Y_AXIS));
        sellPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        for (int i = 0; i < inventory.getItems().size(); i++) {
            Item item = inventory.getItems().get(i);
            itemButton = createShopButton(item.getName() + TAB + (item.getValue() + item.getRefine() * 2) + " coins");
            itemButton.setName("s" + i); // s is classifier (sell), i specifies the item index in inventory list
            sellPanel.add(itemButton);
        }
        sellPanel.add(Box.createVerticalGlue());
    }

    // MODIFIES: this
    // EFFECTS: creates the refining menu
    private void createRefineMenu() {
        refinePanel = new JPanel();
        refinePanel.setBackground(new Color(51, 51, 51));
        refinePanel.setLayout(new BoxLayout(refinePanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < inventory.getItems().size(); i++) {            
            Item item = inventory.getItems().get(i);
            itemButton = createShopButton(item.getName() + TAB + item.getRefine() + " lvl"
                    + TAB + shopHandler.getRefinePrice() + " coins");
            itemButton.setName("r" + i); // r is classifier (refine), i specifies the item index in inventory list
            refinePanel.add(itemButton);
        }
        refinePanel.add(Box.createVerticalGlue());
    }

    // EFFECTS: creates a shop button with given buttonLabel as the text displayed
    // in the button.
    private JButton createShopButton(String buttonLabel) {
        itemButton = new JButton(buttonLabel);
        itemButton.addActionListener(this);
        itemButton.setFont(new Font("Arial", Font.PLAIN, 15));
        itemButton.setMaximumSize(new Dimension(SHOP_MENU_WIDTH, 0));
        itemButton.setMargin(new Insets(7, 0, 7, 0));
        return itemButton;
    }

    // MODIFIES: this
    // EFFECTS: handles the button actions of the shop menu
    public void actionPerformed(ActionEvent e) {
        JButton buttonE = (JButton) e.getSource();
        String buttonName = buttonE.getName();
        char classifier = buttonName.charAt(0);
        int itemIndex = Integer.parseInt(buttonName.substring(1));

        if (classifier == 'b') {
            buyItem(itemIndex);
        } else if (classifier == 's') {
            sellItem(itemIndex);
        } else if (classifier == 'r') {
            refineItem(itemIndex);
        }

        room.updatePlayerData(player, roomNum);
    }

    // MODIFIES: this, player, inventory
    // EFFECTS: handles item purchase
    private void buyItem(int itemIndex) {
        Boolean status;
        if (itemIndex == shopHandler.getShopList().size() - 1) { //the last item, aka the heal.
            status = shopHandler.purchaseHealing(player);
            if (status) {
                JOptionPane.showMessageDialog(panel, "You purchased a Heal!",
                        "Purchase succesful", JOptionPane.INFORMATION_MESSAGE);
                resetShopContents("buy");
            } else {
                JOptionPane.showMessageDialog(panel, "You cannot afford the heal or you're already fully healed",
                        "Failed to buy", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            String itemName = shopHandler.getShopList().get(itemIndex);
            status = shopHandler.purchaseItem(itemIndex, player);
            if (status) {
                JOptionPane.showMessageDialog(panel, "You purchased " + itemName + "!",
                        "Purchase succesful", JOptionPane.INFORMATION_MESSAGE);
                resetShopContents("buy");
            } else {
                JOptionPane.showMessageDialog(panel, "You cannot afford this item.",
                        "Failed to buy", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // MODIFIES: this, player, inventory
    // EFFECTS: handles selling an item
    private void sellItem(int itemIndex) {
        Boolean status;
        String itemName = inventory.getItems().get(itemIndex).getName();
        status = shopHandler.sellItem(itemIndex, player);
        if (status) {
            JOptionPane.showMessageDialog(panel, "Successfully sold your " + itemName + "!",
                    "Sell succesful", JOptionPane.INFORMATION_MESSAGE);
            resetShopContents("sell");
        } else {
            JOptionPane.showMessageDialog(panel, "You can't sell your last weapon.",
                    "Failed to sell", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // MODIFIES: this, player, inventory
    // EFFECTS: handles refining an item
    private void refineItem(int itemIndex) {
        Boolean status;
        Item item = inventory.getItems().get(itemIndex);
        status = shopHandler.purchaseRefine(itemIndex, inventory);
        if (status) {
            JOptionPane.showMessageDialog(panel, "Your " + item.getName() + " is now refine level"
                    + item.getRefine() + "!", "Refinement succesful", JOptionPane.INFORMATION_MESSAGE);
            resetShopContents("refine");
        } else {
            JOptionPane.showMessageDialog(panel, "You could not afford the refinement or your "
                    + item.getName() + " is at max refine level.", "Failed to refine",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: resets shop contents, updating them according to the player's new
    // inventory
    private void resetShopContents(String str) {
        shopPanel.removeAll();
        panel.repaint();
        shopPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        shopPanel.setLayout(new BorderLayout());
        shopPanel.setBounds(200, 150, SHOP_MENU_WIDTH, SHOP_MENU_HEIGHT);
        createMenu();
        if (str.equals("buy")) {
            cl.show(shopMenu, "buy");
        } else if (str.equals("sell")) {
            cl.show(shopMenu, "sell");
        } else if (str.equals("refine")) {
            cl.show(shopMenu, "refine");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates buy button displayed at the top of the shop menu. On press,
    // will switch to buy panel.
    private void createBuyButton() {
        buyButton = new JButton("Buy");
        buyButton.setFont(BUTTON_FONT);
        buyButton.setPreferredSize(new Dimension((SHOP_MENU_WIDTH - 10) / 4, SHOP_BUTTON_HEIGHT));
        shopButtonsPanel.add(buyButton);

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(shopMenu, "buy");
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates sell button displayed at the top of the shop menu. On press,
    // will switch to sell panel.
    private void createSellButton() {
        sellButton = new JButton("Sell");
        sellButton.setFont(BUTTON_FONT);
        sellButton.setPreferredSize(new Dimension((SHOP_MENU_WIDTH - 10) / 4, SHOP_BUTTON_HEIGHT));
        shopButtonsPanel.add(sellButton);

        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(shopMenu, "sell");
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates refine button displayed at the top of the shop menu. On
    // press, will switch to refine panel.
    private void createRefineButton() {
        refineButton = new JButton("Refine");
        refineButton.setFont(BUTTON_FONT);
        refineButton.setPreferredSize(new Dimension((SHOP_MENU_WIDTH - 10) / 4, SHOP_BUTTON_HEIGHT));
        shopButtonsPanel.add(refineButton);

        refineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(shopMenu, "refine");
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates back button displayed at the top of the shop menu. On press,
    // exits the shop menu.
    private void createBackButton() {
        backButton = new JButton("Back");
        backButton.setFont(BUTTON_FONT);
        backButton.setPreferredSize(new Dimension((SHOP_MENU_WIDTH - 10) / 4, SHOP_BUTTON_HEIGHT));
        shopButtonsPanel.add(backButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shopPanel.setVisible(false);
                openButton.setVisible(true);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: begins the shop encounter.
    public void begin(int roomNum) {
        this.roomNum = roomNum;
        panel.setVisible(true);
        openButton.setVisible(true);
        shopPanel.setVisible(false);
        resetShopContents("buy");
    }
}
