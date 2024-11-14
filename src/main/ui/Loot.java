package ui;

import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import model.Inventory;
import model.ItemFactory;
import model.Player;

public class Loot extends RoomPanel {
    private JLabel chest;
    private JButton button;

    private ItemFactory itemFactory = new ItemFactory();

    private static final Random RANDOM = new Random();
    
    public Loot(JLayeredPane roomsLayered, Player player, GameGUI game) {
        super(roomsLayered);

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        //add spacing above chest
        panel.add(Box.createVerticalStrut(50));

        //creates chest image
        Image originalImage = new ImageIcon("./images/chest.png").getImage();
        Image scaledImg = originalImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon chestImage = new ImageIcon(scaledImg);
        chest = new JLabel(chestImage);
        panel.add(chest);

        //add spacing between button and label
        panel.add(Box.createVerticalStrut(20));

        //creates open button
        button = createButton(game, player);
        panel.add(button);
        button.setMargin(new Insets(5, 10, 5, 10));
        button.setFont(new Font("Arial", Font.PLAIN, 16));

        chest.setAlignmentX((float)0.5);
        button.setAlignmentX((float)0.5);
    }

    private JButton createButton(GameGUI game, Player player) {
        button = new JButton("Inspect");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String item = openChest(player, player.getInventory());
                JOptionPane.showMessageDialog(panel, "You obtained " + item + "!");
                end(game);
            }
        });

        return button;
    }

    // EFFECTS: open chest with randomized loot (either coins or a random item of
    // different "rarity")
    private String openChest(Player player, Inventory inventory) {
        int randomLoot = RANDOM.nextInt(20) + 1;
        if (randomLoot <= 10) { // 1-10 50%
            int coins = 3 + RANDOM.nextInt(8 - 3 + 1);
            inventory.collectCoins(coins);
            return coins + " coins";

        } else if (randomLoot <= 16) { // 11-16 30%
            return commonLoot(player, inventory);

        } else if (randomLoot <= 19) { // 17-19 15%
            return rareLoot(player, inventory);

        } else { // only 20 5%
            return kingLoot(player, inventory);
        }
    }

    // EFFECTS: collect common loot. One of mace, cap, or hood.
    private String commonLoot(Player player, Inventory inventory) {
        int rand = RANDOM.nextInt(3) + 1;
        if (rand == 1) {
            inventory.collect(itemFactory.makeMace(), player);
            return "Mace";
        } else if (rand == 2) {
            inventory.collect(itemFactory.makeCap(), player);
            return "Farmer's Cap";
        } else {
            inventory.collect(itemFactory.makeHood(), player);
            return "Thieve's Hood";
        }
    }

    // EFFECTS: collect rare loot. One of longsword or helmet.
    private String rareLoot(Player player, Inventory inventory) {
        int rand = RANDOM.nextInt(2) + 1;
        if (rand == 1) {
            inventory.collect(itemFactory.makeLongsword(), player);
            return "Longsword";
        } else {
            inventory.collect(itemFactory.makeHelmet(), player);
            return "Knight's Helmet";
        }
    }

    // EFFECTS: collect "king" loot. One of excalibur or crown.
    private String kingLoot(Player player, Inventory inventory) {
        int rand = RANDOM.nextInt(2) + 1;
        if (rand == 1) {
            inventory.collect(itemFactory.makeExcalibur(), player);
            return "Excalibur";
        } else {
            inventory.collect(itemFactory.makeCrown(), player);
            return "Crown";
        }
    }
}
