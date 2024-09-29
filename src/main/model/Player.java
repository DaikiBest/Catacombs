package model;

// Represents the Player
public class Player extends GameCharacter {
    
    // EFFECTS: create the Player with 5hp and their base 
    // inventory (no armor, Dagger as weapon, and 5 coins)
    public Player() {
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


    public int getPlayerHealth() {
        return 0;
    }

}
