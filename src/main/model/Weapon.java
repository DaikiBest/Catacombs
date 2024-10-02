package model;

// Represents a weapon used to deal damage
public class Weapon implements Item {
    
    String name;
    int stat;
    int value;

    // EFFECTS: create an armor with name, damage stat, and value.
    public Weapon(String name, int stat, int value) {
        this.name = name;
        this.stat = stat;
        this.value = value;
    }


    public String getName() {
        return this.name;
    }

    public int getStat() {
        return this.stat;
    }

    public int getValue() {
        return this.value;
    }
    
}
