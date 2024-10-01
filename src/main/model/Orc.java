package model;

// Represents an Orc enemy
public class Orc extends GameCharacter implements Enemy {
    
    private int reward;

    // EFFECTS: create an orc with 3hp, 2dmg, and a reward of 3 coins. It has
    // a low and high values of 6 and 12 respectively. These are the lowest and 
    // highest dice rolls the orc can roll.
    public Orc() {
        hp = 3;
        dmg = 2;
        reward = 3;
        low = 6;
        high = 12;
    }

    // EFFECTS: if the enemy's health drops to 0 or below,
    // the enemy perishes and the enounter is over.
    public void Perish() {
        //stub
    }

    public int getReward() {
        return reward;
    }
}
