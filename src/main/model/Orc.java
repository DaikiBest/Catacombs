package model;

// Represents an Orc enemy
public class Orc extends GameCharacter {
    
    // EFFECTS: create an orc with a name, 3hp, 2dmg, and a reward of 3 coins. It has
    // a low and high values of 6 and 12 respectively. These are the lowest and 
    // highest dice rolls the orc can roll.
    public Orc() {
        setName("Orc");
        setHealth(3);
        setDamage(2);
        setReward(3);
        setLow(6);
        setHigh(12);
    }

}
