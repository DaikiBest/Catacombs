package model;

import java.util.Random;

public abstract class GameCharacter {

    private int hp;
    private int dmg;
    private int high;
    private int low;
    private int reward;
    private String name;
    private boolean alive = true;

    // REQUIRES high >= low
    // EFFECTS: rolls a D20 but on a given range.
    public int rollDice(long seed) {
        Random random = new Random(seed);
        return low + random.nextInt(high - low + 1);
    }

    // REQUIRES: dmgInflicted >= 0, this hp > 0
    // MODIFIES: this
    // EFFECTS: this loses hp by the amount dmgInflicted. Checks if the entity is dead.
    // If dead, die().
    public void takeDamage(int dmgInflicted) {
        hp = hp - dmgInflicted;
        if (hp <= 0) {
            alive = false;
        }
    }

    public void setDamage(int dmg) {
        this.dmg = dmg;
    }

    public void setHealth(int hp) {
        this.hp = hp;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDamage() {
        return dmg;
    }

    public int getHealth() {
        return hp;
    }

    public int getReward() {
        return reward;
    }

    public String getName() {
        return name;
    }

    public boolean getAlive() {
        return alive;
    }
}
