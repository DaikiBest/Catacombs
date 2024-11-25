package ui.gui;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import ui.GameGUI;

// Represents the crossroads
public class Crossroads extends RoomPanel {
    private JLabel door;
    private JButton enterRegButton;
    private JLabel wizDoor;
    private JButton enterWizButton;

    private static final Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 18);
    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_HEIGHT = 50;
    
    // EFFECTS: creates the crossroads (a door and a wizdoor)
    public Crossroads(JLayeredPane roomsLayered, GameGUI game) {
        super(roomsLayered);

        panel.setLayout(null);

        createDoor();
        panel.add(door);
        door.setBounds(110, 100, 250, 300);

        createEnterButton(game);
        panel.add(enterRegButton);
        enterRegButton.setBounds(180, 430, BUTTON_WIDTH, BUTTON_HEIGHT);
        enterRegButton.setFont(BUTTON_FONT);

        createWizDoor();
        panel.add(wizDoor);
        wizDoor.setBounds(530, 50, 240, 370);

        createWizButton(game);
        panel.add(enterWizButton);
        enterWizButton.setBounds(600, 430, BUTTON_WIDTH, BUTTON_HEIGHT);
        enterWizButton.setFont(BUTTON_FONT);
    }

    // MODIFIES: this
    // EFFECTS: creates the door image.
    private void createDoor() {
        Image originalImage = new ImageIcon("./images/door.png").getImage();
        Image scaledImg = originalImage.getScaledInstance(250, 300, Image.SCALE_SMOOTH);
        ImageIcon doorImage = new ImageIcon(scaledImg);

        door = new JLabel(doorImage);
    }

    // MODIFIES: this
    // EFFECTS: creates the enter (regular door) button
    private void createEnterButton(GameGUI game) {
        enterRegButton = new JButton("Enter");

        enterRegButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.nextEncounter("regular");
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates the wizard door image
    private void createWizDoor() {
        Image originalImage = new ImageIcon("./images/wizardDoor.png").getImage();
        Image scaledImg = originalImage.getScaledInstance(240, 370, Image.SCALE_SMOOTH);
        ImageIcon wizDoorImage = new ImageIcon(scaledImg);

        wizDoor = new JLabel(wizDoorImage);
    }

    // MODIFIES: this
    // EFFECTS: creates the enter (wizard door) button
    private void createWizButton(GameGUI game) {
        enterWizButton = new JButton("Enter...?");

        enterWizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.nextEncounter("darkWizard");
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: ends the crossroads room (hides the panel)
    public void end() {
        panel.setVisible(false);
    }
}
