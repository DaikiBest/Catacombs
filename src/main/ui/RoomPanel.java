package ui;

import java.awt.Color;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

abstract class RoomPanel {
    protected JPanel panel;
    
    public RoomPanel(JLayeredPane roomsLayered) {
        panel = new JPanel();
        panel.setBounds(0, 0, 900, 620);
        roomsLayered.add(panel, JLayeredPane.DRAG_LAYER);
        panel.setVisible(false);
    }

    // abstract void begin();

    // abstract void end();
}
