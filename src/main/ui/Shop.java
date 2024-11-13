package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

public class Shop extends RoomPanel {
    private JButton exitButton;
    private JLabel shopkeeper;

    private JPanel shopMenu;
    private JButton buyButton;
    private JButton sellButton;
    private JButton refineButton;
    private JButton backButton;

    private static final Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 18);
    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_HEIGHT = 50;
    private static final int SHOP_MENU_WIDTH = 300;
    private static final int SHOP_BUTTON_HEIGHT = 35;

    public Shop(JLayeredPane roomsLayered, GameGUI game) {
        super(roomsLayered);
        panel.setLayout(null);

        shopMenu = new JPanel();
        createMenu();
        JScrollPane sp = new JScrollPane(shopMenu);
        panel.add(sp);
        sp.setBounds(280, 250, SHOP_MENU_WIDTH, 300);

        createShopkeep();
        panel.add(shopkeeper);
        shopkeeper.setBounds(310, 70, 250, 300);

        exitButton = createExitButton(game);
        panel.add(exitButton);
        exitButton.setBounds(80, 480, BUTTON_WIDTH, BUTTON_HEIGHT);
        exitButton.setFont(BUTTON_FONT);

        // createFireplace();
        // panel.add(fireplace);
        // fireplace.setBounds(530, 120, 250, 250);

        // restButton = createRestButton(game);
        // panel.add(restButton);
        // restButton.setBounds(610, 390, 90, 30);
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

    private void createMenu() {
        shopMenu.setLayout(new BoxLayout(shopMenu, BoxLayout.Y_AXIS));
        createBuyButton();
        createSellButton();
        createRefineButton();
        createBackButton();
    }

    private void createBuyButton() {
        buyButton = new JButton("Buy");
        shopMenu.add(buyButton);
        buyButton.setMaximumSize(new Dimension(SHOP_MENU_WIDTH, SHOP_BUTTON_HEIGHT));
        buyButton.setFont(BUTTON_FONT);

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ...
            }
        });
    }

    private void createSellButton() {
        sellButton = new JButton("Sell");
        shopMenu.add(sellButton);
        sellButton.setMaximumSize(new Dimension(SHOP_MENU_WIDTH, SHOP_BUTTON_HEIGHT));
        sellButton.setFont(BUTTON_FONT);

        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ...
            }
        });
    }

    private void createRefineButton() {
        refineButton = new JButton("Refine");
        shopMenu.add(refineButton);
        refineButton.setMaximumSize(new Dimension(SHOP_MENU_WIDTH, SHOP_BUTTON_HEIGHT));
        refineButton.setFont(BUTTON_FONT);

        refineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ...
            }
        });
    }

    private void createBackButton() {
        backButton = new JButton("Back");
        shopMenu.add(backButton);
        backButton.setFont(BUTTON_FONT);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // ...
            }
        });
    }
}
