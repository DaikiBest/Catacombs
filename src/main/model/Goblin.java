package model;

// Represents a Goblin enemy
public class Goblin extends GameCharacter implements Enemy {

    int reward;
    int lowD;
    int highD;

    // EFFECTS: create a goblin with 2hp, 1dmg and a reward of 1 coin. It has
    // a low and high values of 4 and 8 respectively. These are the lowest and 
    // highest dice rolls the goblin can roll.
    public Goblin() {
        hp = 2;
        dmg = 1;
        reward = 1;
        lowD = 4;
        highD = 8;
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
