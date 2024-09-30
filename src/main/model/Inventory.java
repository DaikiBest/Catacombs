package model;

import java.util.ArrayList;
import java.util.List;

// Represents the Player's inventory
public class Inventory {

    // EFFECTS: create an inventory with a dagger, no armor, and 5 coins. 
    public Inventory() {
        //stub
    }

    // MODIFIES: this
    // EFFECTS: add item to the inventory and, if the item is a weapon, update weapon; and vice versa for armor.
    // There can be duplicates of the same item.
    public void collect(Item item) {
        //stub
    }

    // REQUIRES: item to be removed is in the inventory //MAYBE
    // MODIFIES: this
    // EFFECTS: discards one copy of the selected item (identified using the item name)
    // from the inventory. If item is the last weapon in inventory, do not discard.
    public void discard(String itemName) {
        //stub
    }

    // REQUIRES: inventory is not empty, a weapon must be equipped
    // MODIFIES: this
    // EFFECTS: update the player's damage to the highest tier weapon in
    // the inventory.
    public void updateWeapon() {
        //stub
    }

    // EFFECTS: update the player's health to the highest tier armor in the inventory.
    // If the inventory has no armors left, then MAX player hp goes to the default 5.
    public void updateArmor() {
        //stub
    }

    // EFFECTS: get the list of items of inventory
    public List<Item> getInventoryList() {
        List<Item> emptylist = new ArrayList<>();
        return emptylist; //stub
    }

    // REQUIRES: item is in inventory's list of items
    // EFFECTS: get item according to the name
    public Item getItem(String name) {
        return new Armor(null, 0, 0); //stub
    }

    // EFFECTS: get item according to the name
    public int getCoins() {
        return 0; //stub
    }

}
