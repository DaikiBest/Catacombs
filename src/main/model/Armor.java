package model;

// Represents Armor used to boost Player's hp.
public class Armor extends Item {
    
    // EFFECTS: create an armor with name, damage stat, value, zero refine, and a maxRefine.
    // Each refine level increases your dice roll by 1, and can be increased up to maxRefine
    public Armor(String name, int stat, int value) {
        super(name, stat, value);
    }
}
