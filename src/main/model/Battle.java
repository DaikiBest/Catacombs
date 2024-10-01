package model;
public class Battle {
    
    // EFFECTS: create a new battle
    void Battle() {
        //stub
    }

    // EFFECTS: handle the back and forth battle between player and enemy.
    public void battleHanddler() {
        //stub
    }

    // EFFECTS: handle dice. If player rolls higher, return "won" and damage enemy.
    // If enemy rolls higher, return "loss" and damage player. If both rolls are equal,
    // return "tie" and no one gets damaged.
    public String diceHandler() {
        return "won";
    }

    // EFFECTS: give player their rewards and end encounter. If enemy defeated was
    // Dark Wizard, end Game.
    public void endEncounter() {
        //stub
    }

    public int getPlayerRoll() {
        return 0;
    }

    public int getEnemyRoll() {
        return 0;
    }
}
