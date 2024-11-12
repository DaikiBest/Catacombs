package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLayeredPane;

public class Door extends RoomPanel {
    private JButton button;
    
    public Door(JLayeredPane roomsLayered, GameGUI game) {
        super(roomsLayered);

        button = createButton(game);
        panel.add(button);
    }

    private JButton createButton(GameGUI game) {
        JButton button = new JButton("Enter");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.nextEncounter();
            }
        });
        
        return button;
    }

    public void begin() {
        panel.setVisible(true);
    }

    public void end() {
        panel.setVisible(false);
    }
}
