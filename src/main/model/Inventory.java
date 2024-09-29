package model;

import java.util.List;

// Represents the Player's inventory
public class Inventory {

    // EFFECTS: create an inventory with a dagger, no armor, and 5 coins. 
    public Inventory() {
        //stub
    }

    // MODIFIES: this
    // EFFECTS: add item to the inventory. There can be multiple of the same item.
    public void addToInventory(Item item) {
        //stub
    }

    // REQUIRES: item to be removed is in the inventory
    // MODIFIES: this
    // EFFECTS: removes one copy of the selected item from the inventory
    public void removeFromInventory(Item item) {
        //stub
    }

    // REQUIRES: inventory is not empty, a weapon must be equipped
    // MODIFIES: this
    // EFFECTS: makes the highest tier weapon the equipped weapon.
    public void updateWeaponEquipment() {
        //stub
    }

    // EFFECTS: makes the highest tier armor the equipped armor. If the inventory has
    // no armors, then no armor is equipped.
    public void updateArmorEquipment() {
        //stub
    }

    public List<Item> getInventory() {
        return List.of();
    }

}
