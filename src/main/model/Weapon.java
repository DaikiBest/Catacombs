package model;

// Represents a weapon used to deal damage
public class Weapon extends Item {

    // EFFECTS: create an armor with name, damage stat, value, zero refine, and a maxRefine.
    // Each refine level increases your dice roll by 1, and can be increased up to maxRefine
    public Weapon(String name, int stat, int value) {
        super(name, stat, value);
    }

}
