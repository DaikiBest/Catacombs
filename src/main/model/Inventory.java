package model;


import java.util.ArrayList;
import java.util.List;

// Represents the Player's inventory
public class Inventory {
    
    List<Item> items = new ArrayList<>();
    int coins;
    private ItemHandler itemHandler = new ItemHandler();

    // EFFECTS: create an inventory with a dagger, no armor, and 5 coins. 
    public Inventory(Player player) {
        Item dagger = itemHandler.makeDagger();
        this.collect(dagger, player);
        coins = 5;
    }

    // MODIFIES: this
    // EFFECTS: add item to the inventory and, if the item is a weapon, update weapon; and vice versa for armor.
    // There can be duplicates of the same item.
    public void collect(Item item, Player player) {
        items.add(item);
        if (item instanceof Weapon) {
            player.updateWeapon(items);
        } else {
            player.updateArmor(items);
        }
    }

    // REQUIRES: item to be removed is in the inventory
    // MODIFIES: this
    // EFFECTS: discards one copy of the selected item (identified using the item name)
    // from the inventory. If item is the last weapon in inventory, do not discard. Also update
    // either weapon or update armor.
    public void discard(String itemName, Player player) {
        for (Item item : items) {
            if (itemName == item.getName()) {

                if (item instanceof Weapon) { 

                    int weaponCount = 0;
                    for (Item i : items) {
                        if (i instanceof Weapon) {
                            weaponCount++; //count number of weapons in inventory
                        }
                    }

                    if (weaponCount <= 1) { //last weapon in inventory?
                        //will not discard. No weapons left.
                        continue;
                    } else {
                        items.remove(item);
                        player.updateWeapon(items);
                    }

                } else {
                    items.remove(item);
                    player.updateArmor(items);
                }
                break;
            }
        }
    }

    // REQUIRES: newCoins >= 0
    // MODIFIES: this
    // EFFECTS: add amount to coins
    public void collectCoins(int amount) {
        coins += amount;
    }

    // REQUIRES: coins >= amount
    // MODIFIES: this
    // EFFECTS: deduct coins by amount
    public void deductCoins(int amount) {
        coins -= amount;
    }

    // EFFECTS: get the list of items of inventory
    public List<Item> getItems() {
        return this.items; //stub
    }

    // EFFECTS returns the number of times the item appears in inventory list
    public int countItem(String itemName, List<Item> items) {
        int count = 0;
        for (Item item : items) {
            if (item.getName() == itemName) {
                count++;
            }
        }
        return count;
    }

    // EFFECTS: get item according to the name, returns null if not found.
    public Item getItem(String name) {
        for (Item item : items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    // REQUIRES: new coins amount >= 0.
    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getCoins() {
        return this.coins;
    }

}
