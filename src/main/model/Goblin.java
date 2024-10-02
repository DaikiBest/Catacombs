package model;

// Represents a Goblin enemy
public class Goblin extends GameCharacter {

    // EFFECTS: create a goblin with a name, 2hp, 1dmg and a reward of 1 coin. It has
    // a low and high values of 4 and 8 respectively. These are the lowest and 
    // highest dice rolls the goblin can roll.
    public Goblin() {
        setName("Goblin");
        setHealth(2);
        setDamage(1);
        setReward(1);
        setLow(4);
        setHigh(8);
    }
}
