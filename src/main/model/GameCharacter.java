package model;

import java.util.Random;

public class GameCharacter {

    int hp;
    int dmg;
    Random random = new Random();
    

    // EFFECTS: roll D20 (1-20) dice. ie. return random number between 1 and 20
    public int rollDice() {
        return random.nextInt(19);
    }

    // REQUIRES: dmgInflicted >= 0, this hp > 0
    // MODIFIES: this
    // EFFECTS: this loses hp by the amount dmgInflicted
    public void takeDamage(int dmgInflicted) {
        hp = hp - dmgInflicted;
    }

    public void setDamage(int dmg) {
        this.dmg = dmg;
    }

    public void setHealth(int hp) {
        this.hp = hp;
    }

    public int getDamage() {
        return dmg;
    }

    public int getHealth() {
        return hp;
    }

    // allows goblin and orcs to access this when instantiated as Game Characters
    public int getReward() {
        return 0;
    }
}
