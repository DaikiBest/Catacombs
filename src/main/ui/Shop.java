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

// Represents a shop encounter
public class Shop extends RoomPanel implements ActionListener {
    private JButton exitButton;
    private JButton openButton;
    private JLabel shopkeeper;

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
    private ShopHandler shopHandler;
    private Room room;
    private int roomNum;

    private static final Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 18);
    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_HEIGHT = 50;
    private static final int SHOP_MENU_WIDTH = 500;
    private static final int SHOP_MENU_HEIGHT = 400;
    private static final int SHOP_BUTTON_HEIGHT = 35;

    // EFFECTS: Create a shop with the open and exit buttons, as well as the shop image and menu
    public Shop(JLayeredPane roomsLayered, GameGUI game, Player player, Room room, RoomHandler roomHandler) {
        super(roomsLayered);
        shopHandler = new ShopHandler();
        this.player = player;
        inventory = player.getInventory();
        this.room = room;
        roomNum = roomHandler.getRoomNum();

        panel.setLayout(null);

        //create the shop menu
        shopPanel = new JPanel();
        shopPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        shopPanel.setLayout(new BorderLayout());
        shopPanel.setBounds(200, 150, SHOP_MENU_WIDTH, SHOP_MENU_HEIGHT);
        createMenu();
        
        panel.add(shopPanel);

        //create the image of the shopkeeper
        createShopkeep();
        panel.add(shopkeeper);
        shopkeeper.setBounds(190, 40, 540, 400);

        //create the openShop button
        openButton = createOpenButton(game);
        panel.add(openButton);

        //create the exit button
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

        shopkeeper = new JLabel(shopKeep);
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

    private void createBuyMenu() {
        buyPanel = new JPanel();
        buyPanel.setBackground(new Color(51, 51, 51));
        buyPanel.setLayout(new BoxLayout(buyPanel, BoxLayout.Y_AXIS));
        
        for (String itemName : shopHandler.getShopList()) {
            itemButton = createShopButton(itemName);
            itemButton.setName("b" + itemName); //starts with a b to classify button
            buyPanel.add(itemButton);
        }
        buyPanel.add(Box.createVerticalGlue());
    }

    private void createSellMenu() {
        sellPanel = new JPanel();
        sellPanel.setBackground(new Color(51, 51, 51));
        sellPanel.setLayout(new BoxLayout(sellPanel, BoxLayout.Y_AXIS));
        sellPanel.setAlignmentY(Component.TOP_ALIGNMENT);

        for (Item item : inventory.getItems()) {
            createShopButton(item.getName());
            itemButton.setName("s" + item.getName()); //starts with an s to classify button
            sellPanel.add(itemButton);
        }
        sellPanel.add(Box.createVerticalGlue());
    }

    private void createRefineMenu() {
        refinePanel = new JPanel();
        refinePanel.setBackground(new Color(51, 51, 51));
        refinePanel.setLayout(new BoxLayout(refinePanel, BoxLayout.Y_AXIS));
        
        for (Item item : inventory.getItems()) {
            createShopButton(item.getName());
            itemButton.setName("r" + item.getName()); //starts with an r to classify button
            refinePanel.add(itemButton);
        }
        refinePanel.add(Box.createVerticalGlue());
    }

    private JButton createShopButton(String itemName) {
        itemButton = new JButton(itemName);
        itemButton.addActionListener(this);
        itemButton.setFont(new Font("Arial", Font.PLAIN, 15));
        itemButton.setMaximumSize(new Dimension(SHOP_MENU_WIDTH, 1000));
        itemButton.setMargin(new Insets(8, 0, 8, 0));
        return itemButton;
    }

    public void actionPerformed(ActionEvent e) {
        JButton buttonE = (JButton)e.getSource();
        String buttonName = buttonE.getName();
        char classifier = buttonName.charAt(0);
        String itemName = buttonName.substring(1);


        if (classifier == 'b') {
            buyItem(itemName);
        } else if (classifier == 's') {
            sellItem(itemName);
        } else if (classifier == 'r') {
            refineItem(itemName);
        }

        room.updatePlayerData(player, roomNum);
    }

    private void buyItem(String itemName) {
        Boolean status;
        ItemFactory itemFactory = new ItemFactory();
        if (itemName.equals("Heal")) {
            status = shopHandler.purchaseHealing(player);
        } else {
            status = shopHandler.purchaseItem(itemFactory.makeItem(itemName), player);
        }

        if (status) {
            JOptionPane.showMessageDialog(panel, "Successfully purchased " + itemName + "!");
        } else {
            JOptionPane.showMessageDialog(panel, "Purchase unsuccessful.");
        }
    }

    private void sellItem(String itemName) {
        Boolean status;
        status = shopHandler.sellItem(itemName, player);
        if (status) {
            JOptionPane.showMessageDialog(panel, "Successfully sold " + itemName + "!");
            resetShopContents();
        } else {
            JOptionPane.showMessageDialog(panel, "You can't sell your last weapon.");
        }
    }

    private void refineItem(String itemName) {
        
    }

    private void resetShopContents() {
        shopPanel.removeAll();
        panel.repaint();
        shopPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        shopPanel.setLayout(new BorderLayout());
        shopPanel.setBounds(200, 150, SHOP_MENU_WIDTH, SHOP_MENU_HEIGHT);
        createMenu();
        cl.show(shopMenu, "sell");
    }

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

    @Override
    public void begin() {
        panel.setVisible(true);
        openButton.setVisible(true);
        shopPanel.setVisible(false);
        // resetShopContents();
    }
}
