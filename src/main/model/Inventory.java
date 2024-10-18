package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

// Represents the Player's inventory
public class Inventory {
    
    private List<Item> items;
    private int coins;

    // EFFECTS: create an inventory with no items and 5 coins. 
    public Inventory(Player player) {
        items = new ArrayList<>();
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
            if (item.getName().equalsIgnoreCase(itemName)) {

                if (item instanceof Weapon) { 

                    int weaponCount = 0;
                    for (Item i : items) {
                        if (i instanceof Weapon) {
                            weaponCount++; //count number of weapons in inventory
                        }
                    }

                    if (weaponCount <= 1) { //last weapon in inventory?
                        //will not discard. No weapons left.
                        break;
                    }
                    items.remove(item);
                    player.updateWeapon(items);
                    break;
                }
                items.remove(item);
                player.updateArmor(items);
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
            if (item.getName().equalsIgnoreCase(name)) {
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

    // EFFECTS: returns the list of items as a jsonarray
    public JSONArray itemsToJson() {
        JSONArray itemsJson = new JSONArray();

        for (Item item : items) {
            itemsJson.put(itemToJson(item));
        }

        return itemsJson;
    }

    // EFFECTS: return a single item (its name) as a jsonobject
    private JSONObject itemToJson(Item item) {
        JSONObject json = new JSONObject();
        json.put("name", item.getName());
        return json;
    }

}
