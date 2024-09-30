package model;


import java.util.ArrayList;
import java.util.List;

// Represents the Player's inventory
public class Inventory {
    
    List<Item> items = new ArrayList<>();
    int coins;

    // EFFECTS: create an inventory with a dagger, no armor, and 5 coins. 
    public Inventory() {
        Weapon dagger = new Weapon("Dagger", 1, 0);
        this.collect(dagger);
        coins = 5;
    }

    // MODIFIES: this
    // EFFECTS: add item to the inventory and, if the item is a weapon, update weapon; and vice versa for armor.
    // There can be duplicates of the same item.
    public void collect(Item item) {
        items.add(item);
        if (item instanceof Weapon) {
            updateWeapon();
        } else {
            updateArmor();
        }
    }

    // REQUIRES: item to be removed is in the inventory //MAYBE
    // MODIFIES: this
    // EFFECTS: discards one copy of the selected item (identified using the item name)
    // from the inventory. If item is the last weapon in inventory, do not discard.
    public void discard(String itemName) {
        for (Item item : items) {
            if (itemName == item.getName()) {

                int weaponCount = 0;
                for (Item i : items) {
                    if (i instanceof Weapon) {
                        weaponCount++; //count number of weapons in inventory
                    }
                }

                if (item instanceof Weapon) { 

                    if (weaponCount <= 1) { //last weapon in inventory?
                        //will not discard. No weapons left.
                        break;
                    } else {
                        items.remove(item);
                        updateWeapon();
                    }

                } else {
                    items.remove(item);
                    updateArmor();
                }
                break;
            }
        }
    }

    // REQUIRES: inventory is not empty, a weapon must be equipped
    // MODIFIES: this
    // EFFECTS: update the player's damage to the highest tier weapon in
    // the inventory.
    public void updateWeapon() {
        int maxDmg = 0;
        for (Item item : items) {
            if (item instanceof Weapon & item.getStat() > maxDmg) {
                maxDmg = item.getStat(); //get the weapon with highest stats
            }
        }
        //Player.setDamage(maxDmg); //problem.... BUG........
    }

    // EFFECTS: update the player's health to the highest tier armor in the inventory.
    // If the inventory has no armors left, then MAX player hp goes to the default 5.
    public void updateArmor() {
        int maxArmor = 0;
        for (Item item : items) {
            if (item instanceof Armor & item.getStat() > maxArmor) {
                maxArmor = item.getStat(); //get the Armor with highest stats
            }
        }
        //Player.setHealth(maxArmor); //problem.... BUG........    
    }

    // EFFECTS: get the list of items of inventory
    public List<Item> getItems() {
        return this.items; //stub
    }

    // EFFECTS return number of times given item appears in items
    public int countItem(String itemName, List<Item> items) {
        int count = 0;
        for (Item item : items) {
            if (item.getName() == itemName) {
                count++;
            }
        }
        return count;
    }

    // REQUIRES: item is in inventory's list of items
    // EFFECTS: get item according to the name
    public Item getItem(String name) {
        for (Item item : items) {
            if (item.getName().equals(name)) {
                return item;
            }
        };
        return null;
    }

    // EFFECTS: get item according to the name
    public int getCoins() {
        return this.coins; //stub
    }

}
