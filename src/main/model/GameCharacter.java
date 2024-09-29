package model;
public class GameCharacter {
    
    // EFFECTS: roll dice...
    public void rollDice() {
        //stub
    }

    // EFFECTS: attack opponent using this dmg
    public void attack() {
        //stub
    }

    // REQUIRES: dmg >= 0, this hp > 0
    // MODIFIES: this
    // EFFECTS: this loses hp by the amount dmg
    public void takeDamage(int dmg) {
        //stub
    }

    public int getHealthRemaining() {
        return 0;
    }
}
