package model;

// Represents Armor used to boost Player's hp.
public class Armor implements Item {
    
    private String name;
    private int stat;
    private int value;


    // EFFECTS: create an armor with name, hp bonus stat, and value.
    public Armor(String name, int stat, int value) {
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
