package ui;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

// Represents an abstract class for all rooms
abstract class RoomPanel {
    protected JPanel panel;
    
    public RoomPanel(JLayeredPane roomsLayered) {
        panel = new JPanel();
        panel.setBounds(0, 0, 900, 640);
        roomsLayered.add(panel, JLayeredPane.DRAG_LAYER);
        panel.setVisible(false);
    }

    public void begin() {
        panel.setVisible(true);
    }

    public void end(GameGUI game) {
        panel.setVisible(false);
        game.runGame();
    }
}
