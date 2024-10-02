package model;

import java.util.Random;

public class GameCharacter {

    public int hp;
    public int dmg;
    public int high;
    public int low;
    public Random random = new Random();

    // REQUIRES high >= low
    // EFFECTS: rolls a D20 but on a given range.
    public int rollDice() {
        return low + random.nextInt(high - low + 1);
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

    // // allows goblin and orcs to access this when instantiated as Game Characters
    // public int getReward() {
    //     return 0;
    // }
}
