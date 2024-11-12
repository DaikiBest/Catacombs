package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLayeredPane;

public class Crossroads extends RoomPanel {
    private JButton button;
    
    public Crossroads(JLayeredPane roomsLayered, GameGUI game) {
        super(roomsLayered);

        button = createButton(game);
        panel.add(button);
    }

    private JButton createButton(GameGUI game) {
        JButton button = new JButton("This is crossroads");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.nextEncounter();
            }
        });
        
        return button;
    }

    public void end() {
        panel.setVisible(false);
    }
}
