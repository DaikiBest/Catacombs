package model;

// Represents an Item – either armor or weapon – for the Player.
public class Item {
    protected String name;
    protected int stat;
    protected int value;
    protected int refine;
    protected int maxRefine;

    // EFFECTS: create an armor with name, hp bonus stat, value, zero refine, and a maxRefine.
    // Each refine level decreases the dice roll of the enemy by 1, and can be increased up to maxRefine
    public Item(String name, int stat, int value) {
        this.name = name;
        this.stat = stat;
        this.value = value;
        refine = 0;
        maxRefine = 3; //constant for all armor
    }

    // REQUIRES: refine <= maxRefine
    // MODIFIES: this
    // EFFECTS: increase refinement of armor by 1. Returns true if succesful, 
    // false if it did not refine because it was at max refinement already
    public boolean refine() {
        if (refine < maxRefine) {
            refine++;
            return true;
        }
        return false; //else
    }

    
    public String getName() {
        return name;
    }
    
    public int getStat() {
        return stat;
    }
    
    public int getValue() {
        return value;
    }
    
    public int getRefine() {
        return refine;
    }
    
    public void setRefine(int refine) {
        this.refine = refine;
    }
}
