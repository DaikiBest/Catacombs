package model;
public class GameCharacter {
    
    // EFFECTS: roll D20 (1-20) dice. ie. return random number between 1 and 20
    public int rollDice() {
        return 0;
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

    public void setDamage() {
        //stub
    }

    public void setHealth() {
        //stub
    }

    public int getDamage() {
        return 0;
    }

    public int getHealth() {
        return 0;
    }
}
