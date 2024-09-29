package model;

// Represents a Goblin enemy
public class Goblin extends GameCharacter implements Enemy {
    
    // EFFECTS: create a goblin with 2hp and 1dmg.
    public Goblin() {
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

    // EFFECTS: if the enemy's health drops to 0 or below,
    // the enemy perishes and the enounter is over.
    public void Perish() {
        
    }
}
