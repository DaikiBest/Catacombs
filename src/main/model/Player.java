package model;

// Represents the Player
public class Player extends GameCharacter {
    
    int hp;
    int dmg;

    // EFFECTS: create the Player with default inventory (no armor, 
    // Dagger as weapon, and 5 coins). Set the player hp (hit points) to 5, and dmg (damage) as 1.
    public Player() {
        this.setHealth(5);
    }

    // EFFECTS: attack opponent using this dmg
    public void attack() {
        //stub
    }

    // Basic getters and setters all in GameCharacter superclass (eg. getHealth, setDamage, etc.)

}
