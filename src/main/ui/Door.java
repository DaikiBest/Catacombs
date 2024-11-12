package ui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

// Represents a 
public class Door extends RoomPanel {
    private JLabel door;
    private JButton enterButton;
    private JLabel fireplace;
    private JButton restButton;

    public Door(JLayeredPane roomsLayered, GameGUI game) {
        super(roomsLayered);

        panel.setLayout(null);

        createDoor();
        panel.add(door);
        door.setBounds(90, 70, 300, 300);

        enterButton = createEnterButton(game);
        panel.add(enterButton);
        enterButton.setBounds(190, 390, 90, 30);

        createFireplace();
        panel.add(fireplace);
        fireplace.setBounds(530, 120, 250, 250);

        restButton = createRestButton(game);
        panel.add(restButton);
        restButton.setBounds(610, 390, 90, 30);
    }

    private void createDoor() {
        Image originalImage = new ImageIcon("./images/door.png").getImage();
        Image scaledImg = originalImage.getScaledInstance(250, 300, Image.SCALE_SMOOTH);
        ImageIcon doorImage = new ImageIcon(scaledImg);

        door = new JLabel(doorImage);
    }

    private JButton createEnterButton(GameGUI game) {
        enterButton = new JButton("Enter");

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.nextEncounter();
            }
        });

        return enterButton;
    }

    private void createFireplace() {
        Image originalImage = new ImageIcon("./images/fireplace.png").getImage();
        Image scaledImg = originalImage.getScaledInstance(200, 250, Image.SCALE_SMOOTH);
        ImageIcon fireplaceImage = new ImageIcon(scaledImg);

        fireplace = new JLabel(fireplaceImage);
    }

    private JButton createRestButton(GameGUI game) {
        enterButton = new JButton("Rest");

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restPopup(game);
            }
        });

        return enterButton;
    }

    private void restPopup(GameGUI game) {
        Object[] options = {"Save", "Load", "Cancel"};
        int result = JOptionPane.showOptionDialog(panel, "Would you like to save or load?", "Rest",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
        if (result == 0) {
            game.save();
        } else if (result == 1) {
            game.load();
        }
    }

    public void end() {
        panel.setVisible(false);
    }
}
