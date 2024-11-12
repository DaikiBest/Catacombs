package ui;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class Loot extends RoomPanel {
    private JLabel chest;
    private JButton button;
    
    public Loot(JLayeredPane roomsLayered) {
        super(roomsLayered);

        Image originalImage = new ImageIcon("./images/chest.png").getImage();
        Image scaledImg = originalImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon chestImage = new ImageIcon(scaledImg);
        chest = new JLabel(chestImage);
        panel.add(chest);

        button = new JButton();
    }

    public void begin() {
        panel.setVisible(true);
    }
}
