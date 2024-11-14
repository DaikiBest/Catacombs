package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import model.Inventory;
import model.Player;

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

    private static final Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 18);
    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_HEIGHT = 50;
    private static final int SHOP_MENU_WIDTH = 500;
    private static final int SHOP_MENU_HEIGHT = 400;
    private static final int SHOP_BUTTON_HEIGHT = 35;

    public Shop(JLayeredPane roomsLayered, GameGUI game, Player player) {
        super(roomsLayered);
        panel.setLayout(null);

        //create the shop menu
        shopPanel = new JPanel();
        shopPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        shopPanel.setLayout(new BorderLayout());
        shopPanel.setBounds(200, 150, SHOP_MENU_WIDTH, SHOP_MENU_HEIGHT);
        createMenu(player);
        
        panel.add(shopPanel);

        //create the image of the shopkeeper
        createShopkeep();
        panel.add(shopkeeper);
        shopkeeper.setBounds(310, 70, 250, 300);

        //create the openShop button
        openButton = createOpenButton(game);
        panel.add(openButton);
        openButton.setBounds(380, 380, BUTTON_WIDTH, BUTTON_HEIGHT);
        openButton.setFont(BUTTON_FONT);

        //create the exit button
        exitButton = createExitButton(game);
        panel.add(exitButton);
        exitButton.setBounds(50, 490, BUTTON_WIDTH, BUTTON_HEIGHT);
        exitButton.setFont(BUTTON_FONT);

        openButton.setVisible(true);
        shopPanel.setVisible(false);
    }

    private void createShopkeep() {
        Image originalImage = new ImageIcon("./images/door.png").getImage();
        Image scaledImg = originalImage.getScaledInstance(250, 300, Image.SCALE_SMOOTH);
        ImageIcon shopKeep = new ImageIcon(scaledImg);

        shopkeeper = new JLabel(shopKeep);
    }

    private JButton createExitButton(GameGUI game) {
        exitButton = new JButton("Exit");

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                end(game);
            }
        });

        return exitButton;
    }

    private JButton createOpenButton(GameGUI game) {
        openButton = new JButton("Open Shop");

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shopPanel.setVisible(true);
                openButton.setVisible(false);
            }
        });

        return openButton;
    }

    private void createMenu(Player player) {
        shopButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));

        cl = new CardLayout();
        shopMenu = new JPanel(cl);

        buyPanel = new JPanel();
        buyPanel.setBackground(new Color(200));
        sellPanel = new JPanel();
        sellPanel.setBackground(new Color(0));
        refinePanel = new JPanel();
        refinePanel.setBackground(new Color(100));
        shopMenu.add(buyPanel, "buy");
        shopMenu.add(sellPanel, "sell");
        shopMenu.add(refinePanel, "refine");

        createBuyButton(player);
        createSellButton();
        createRefineButton();
        createBackButton();

        shopPanel.add(shopMenu);
        shopPanel.add(shopButtonsPanel, BorderLayout.NORTH);
    }

    private void createBuy(Inventory inventory) {
        itemButton = new JButton("hello");
        // shopPanel.add(itemButton);
        // for (Item item : inventory.getItems()) {
        //     System.out.println(item.getName());
        //     itemButton = new JButton(item.getName());
        //     itemButton.addActionListener(this);
        //     shopPanel.add(itemButton);
        // }
        itemButton.setMaximumSize(new Dimension(SHOP_MENU_WIDTH, SHOP_BUTTON_HEIGHT));
    }

    public void actionPerformed(ActionEvent e) {
        //
    }

    private void createBuyButton(Player player) {
        buyButton = new JButton("Buy");
        buyButton.setFont(BUTTON_FONT);
        buyButton.setPreferredSize(new Dimension((SHOP_MENU_WIDTH-10)/4, SHOP_BUTTON_HEIGHT));
        shopButtonsPanel.add(buyButton);

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(shopMenu, "buy");
                createBuy(player.getInventory());
            }
        });
    }

    private void createSellButton() {
        sellButton = new JButton("Sell");
        sellButton.setFont(BUTTON_FONT);
        sellButton.setPreferredSize(new Dimension((SHOP_MENU_WIDTH-10)/4, SHOP_BUTTON_HEIGHT));
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
        refineButton.setPreferredSize(new Dimension((SHOP_MENU_WIDTH-10)/4, SHOP_BUTTON_HEIGHT));
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
        backButton.setPreferredSize(new Dimension((SHOP_MENU_WIDTH-10)/4, SHOP_BUTTON_HEIGHT));
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
    }
}
