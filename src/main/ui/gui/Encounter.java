package ui.gui;

import java.util.Random;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;

import model.BattleHandler;
import model.GameCharacter;
import model.Player;
import model.RoomHandler;
import ui.GameGUI;

// Represents an enemy encounter
public class Encounter extends RoomPanel {
    private JPanel endPanel;
    private JLabel endLabel;

    private JLabel enemyStats;
    private JLabel enemyPortrait;
    private JLabel feedbackLabel;

    private JButton attackButton;
    private JButton fleeButton;

    private JLabel enemyDice;
    private JLabel playerDice;

    private Player player;
    private GameCharacter enemy;
    private BattleHandler battleHandler = new BattleHandler();

    private static final Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 24);
    private static final int BUTTON_WIDTH = 160;
    private static final int BUTTON_HEIGHT = 70;
    private static final Random RANDOM = new Random();

    // EFFECTS: creates an enemy encounter
    public Encounter(JLayeredPane roomsLayered, GameGUI game, Room room, RoomHandler roomHandler) {
        super(roomsLayered);
        panel.setLayout(null);

        createEndPanel();
        panel.add(endPanel);
        endPanel.setVisible(false);

        createEncounter();
        panel.add(feedbackLabel);
        panel.add(playerDice);
        panel.add(enemyDice);
        panel.add(enemyStats);
        panel.add(enemyPortrait);

        // create the openShop button
        createAttackButton(game, room, roomHandler);
        panel.add(attackButton);

        // create the exit button
        createFleeButton(game, room, roomHandler);
        panel.add(fleeButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the end (game over) panel
    private void createEndPanel() {
        endLabel = new JLabel("", SwingConstants.CENTER);
        endLabel.setFont(new Font("Arial", Font.BOLD, 120));
        endLabel.setBounds(0, 0, 900, 500);
        endPanel = new JPanel();
        endPanel.setBounds(0, 0, 900, 620);
        endPanel.setLayout(null);
        endPanel.add(endLabel);
    }

    // MODIFIES: this
    // EFFECTS: creates the enemy panel with the stats, portrait, and the feedback
    // label
    private void createEncounter() {
        enemyStats = new JLabel();
        enemyStats.setFont(BUTTON_FONT);
        enemyStats.setBounds(340, 20, 300, 60);

        enemyPortrait = new JLabel("", SwingConstants.CENTER);
        enemyPortrait.setBounds(0, 80, 900, 500);

        // instantiate the dice
        // player Dice
        playerDice = new JLabel("17", SwingConstants.CENTER);
        playerDice.setFont(new Font("Arial", Font.BOLD, 120));
        playerDice.setBounds(120, 200, 200, 200);
        playerDice.setBackground(new Color(0, 0, 255, 160)); // transparent blue
        playerDice.setOpaque(true);
        playerDice.setVisible(false);

        // enemy Dice
        enemyDice = new JLabel("14", SwingConstants.CENTER);
        enemyDice.setFont(new Font("Arial", Font.BOLD, 120));
        enemyDice.setBounds(570, 200, 200, 200);
        enemyDice.setBackground(new Color(255, 0, 0, 160)); // transparent red
        enemyDice.setOpaque(true);
        enemyDice.setVisible(false);

        // feedback label; set to be "Pow", "Ouch" or "Clash", depending on the round
        // outcome
        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.BOLD, 200));
        feedbackLabel.setBounds(0, 30, 900, 500);
    }

    // MODIFIES: this
    // EFFECTS: update player stats, update enemy stats, check if player or enemy
    // have perished
    private void updateEncounter(Room room, RoomHandler roomHandler) {
        room.updatePlayerData(player, roomHandler.getRoomNum());
        enemyStats.setText("<html><body style='text-align: center'>" + enemy.getName() + "<br>Health: "
                + enemy.getHealth() + "&ensp; Damage: " + enemy.getDamage() + "</body></html>");
    }

    // MODIFIES: this
    // EFFECTS: creates the attack button
    private void createAttackButton(GameGUI game, Room room, RoomHandler roomHandler) {
        attackButton = new JButton("Attack");
        attackButton.setFont(BUTTON_FONT);
        attackButton.setBounds(70, 260, BUTTON_WIDTH, BUTTON_HEIGHT);

        attackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attack(game, room, roomHandler);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: begins the attack "round", where the and enemy roll their dice, and
    // hit each other.
    private void attack(GameGUI game, Room room, RoomHandler roomHandler) {
        attackButton.setEnabled(false);
        fleeButton.setEnabled(false);

        int playerRoll = player.rollDice(RANDOM.nextLong());
        int enemyRoll = enemy.rollDice(RANDOM.nextLong());

        String outcome = battleHandler.diceHandler(player, enemy, playerRoll, enemyRoll);
        int refinedPlayerRoll = battleHandler.playerDiceRefined(playerRoll, player.getInventory());
        int refinedEnemyRoll = battleHandler.enemyDiceRefined(enemyRoll, player.getInventory());

        displayDice(playerRoll, enemyRoll, refinedPlayerRoll, refinedEnemyRoll, outcome, game, room, roomHandler);
    }

    // MODIFIES: this
    // EFFECTS: displays the enemy and player's unrefined dice rolls
    private void displayDice(int playerRoll, int enemyRoll, int refinedPlayerRoll, int refinedEnemyRoll,
            String outcome, GameGUI game, Room room, RoomHandler roomHandler) {
        playerDice.setText(String.valueOf(playerRoll));
        enemyDice.setText(String.valueOf(enemyRoll));
        playerDice.setVisible(true);
        enemyDice.setVisible(true);

        ActionListener ticktock = new ActionListener() {
            public void actionPerformed(ActionEvent evnt) {
                refineDice(refinedPlayerRoll, refinedEnemyRoll, outcome, game, room, roomHandler);
            }
        };
        Timer timer = new Timer(500, ticktock);
        timer.setRepeats(false);
        timer.start();
    }

    // MODIFIES: this
    // EFFECTS: displays the enemy and player's refined dice rolls
    private void refineDice(int newPRoll, int newERoll, String outcome, GameGUI game, Room room,
            RoomHandler roomHandler) {
        playerDice.setText(String.valueOf(newPRoll));
        enemyDice.setText(String.valueOf(newERoll));

        ActionListener ticktock = new ActionListener() {
            public void actionPerformed(ActionEvent evnt) {
                playerDice.setVisible(false);
                enemyDice.setVisible(false);
                battleOutcome(outcome, game, room, roomHandler);
                battleFeedback(newPRoll, newERoll);
            }
        };
        Timer timer = new Timer(500, ticktock);
        timer.setRepeats(false);
        timer.start();
    }

    // MODIFIES: this
    // EFFECTS: displays the battle feedback depending on the outcome of the dice
    // rolls. "Ouch" if enemy wins,
    // "Pow" if player wins, "Clash" if they tie.
    private void battleFeedback(int playerRoll, int enemyRoll) {
        String feedback = ((playerRoll > enemyRoll) ? "Pow!" : (playerRoll < enemyRoll) ? "Ouch!" : "Clash");
        feedbackLabel.setText(feedback);

        ActionListener ticktock = new ActionListener() {
            public void actionPerformed(ActionEvent evnt) {
                feedbackLabel.setText("");
            }
        };

        Timer timer = new Timer(400, ticktock);
        timer.setRepeats(false);
        timer.start();
    }

    // MODIFIES: this
    // EFFECTS: the battle outcome of the round, checking if the enemy or the player
    // have died.
    private void battleOutcome(String outcome, GameGUI game, Room room, RoomHandler roomHandler) {
        updateEncounter(room, roomHandler);

        if (outcome.equals("win")) { // enemy takes a hit

            if (!enemy.getAlive() && enemy.getName().equals("Dark Wizard")) { // defeated dark wizard
                gameOver(true, game);

            } else if (!enemy.getAlive()) { // defeated regular enemy
                JOptionPane.showMessageDialog(panel, "You defeated the " + enemy.getName() + " and received "
                        + enemy.getReward() + " coins!", "Victory!", JOptionPane.INFORMATION_MESSAGE);
                battleHandler.giveRewards(player.getInventory(), enemy);
                end(game);
            }

        } else if (outcome.equals("lose")) { // player takes a hit
            checkPlayerState(game);
        }
        attackButton.setEnabled(true);
        fleeButton.setEnabled(true);
    }

    // MODIFIES: this
    // EFFECTS: creates the flee button
    private void createFleeButton(GameGUI game, Room room, RoomHandler roomHandler) {
        fleeButton = new JButton("Flee");
        fleeButton.setFont(BUTTON_FONT);
        fleeButton.setBounds(670, 260, BUTTON_WIDTH, BUTTON_HEIGHT);

        fleeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flee(game, room, roomHandler);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: the player attempts to flee the encounter, with a 75% to succeed and
    // end the encounter,
    // and 25% chance to take damage.
    private void flee(GameGUI game, Room room, RoomHandler roomHandler) {
        int rand = RANDOM.nextInt(4) + 1;

        if (rand <= 3) { // 75%
            end(game); // successfuly fled encounter
        } else { // 25%
            int hit = enemy.getDamage() / 2;
            hit = ((hit == 0) ? 1 : hit); // take at least 1 damage

            player.takeDamage(hit); // take half of enemy damage, rounded down; or 1 if 0.
            battleFeedback(0, 1);
            updateEncounter(room, roomHandler);
            checkPlayerState(game);
        }
    }

    // EFFECTS: checks player state. If dead, finish game.
    private void checkPlayerState(GameGUI game) {
        if (!player.getAlive()) {
            gameOver(false, game);
        }
    }

    // MODIFIES: this, room
    // EFFECTS: Game Over. Finish the game, either win or lose
    private void gameOver(boolean hasWon, GameGUI game) {
        if (hasWon) { // win
            endPanel.setVisible(true);
            endLabel.setText("You Win! Nice.");
            endPanel.setBackground(new Color(51, 153, 255));
        } else { // lose
            endPanel.setVisible(true);
            endLabel.setText("You're dead.");
            endPanel.setBackground(new Color(255, 51, 51));
        }
        Object[] options = { "Quit", "Restart" };
        int result = JOptionPane.showOptionDialog(panel, "Would you like to quit or restart?", "Game Over",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (result == 1) {
            game.restart();
        } else {
            game.gameOver();
        }
    }

    // MODIFIES: this
    // EFFECTS: begins current encounter
    public void begin(Player player, GameCharacter enemy) {
        setupEncounter(player, enemy);
        panel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets up the encounter, setting the player and the enemy
    private void setupEncounter(Player player, GameCharacter enemy) {
        this.player = player;
        this.enemy = enemy;

        enemyStats.setText("<html><body style='text-align: center'>" + enemy.getName() + "<br>Health: "
                + enemy.getHealth() + "&ensp; Damage: " + enemy.getDamage() + "</body></html>");

        Image originalImage;
        Image scaledImg;
        if (enemy.getName().equals("Goblin")) {
            originalImage = new ImageIcon("./images/goblin.png").getImage();
            scaledImg = originalImage.getScaledInstance(210, 330, Image.SCALE_SMOOTH);
        } else if (enemy.getName().equals("Orc")) {
            originalImage = new ImageIcon("./images/orc.png").getImage();
            scaledImg = originalImage.getScaledInstance(300, 480, Image.SCALE_SMOOTH);
        } else { // it is a dark wizard
            originalImage = new ImageIcon("./images/darkWizard.png").getImage();
            scaledImg = originalImage.getScaledInstance(220, 460, Image.SCALE_SMOOTH);
        }
        ImageIcon enemyImage = new ImageIcon(scaledImg);
        enemyPortrait.setIcon(enemyImage);
    }
}
