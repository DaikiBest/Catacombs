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
            player.updateWeapon();
        } else {
            player.updateArmor();
        }
    }

    // REQUIRES: item to be removed is in the inventory, item is not last weapon in inventory
    // MODIFIES: this
    // EFFECTS: discards one copy of the selected item (identified using the item name)
    // from the inventory. If item is the last weapon in inventory, do not discard. Also update
    // either weapon or update armor.
    public void discard(int index, Player player) {
        Item item = items.get(index);
        items.remove(item);
        if (item instanceof Weapon) {
            player.updateWeapon();
        } else {
            player.updateArmor();
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

    // EFFECTS returns a list with all instances of this item (by its name).
    public List<Item> selectItem(String itemName) {
        List<Item> selectedItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getName() == itemName) {
                selectedItems.add(item);
            }
        }
        return selectedItems;
    }

    // EFFECTS: get item according to the name, returns null if not found. Returns the first instance of the item.
    public Item getItem(String name) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    // REQUIRES: items.size() > 0, at least one weapon in inventory
    // EFFECTS: get the highest tier weapon (hence the equipped weapon). Due to the requires clause,
    // should never return null.
    public Item getEquippedWeapon() {
        int maxDmg = 0;
        Item weapon = null;
        Item prev = null;
        for (Item item : items) {
            if (item instanceof Weapon && item.getStat() >= maxDmg) {
                maxDmg = item.getStat(); //get the weapon with damage
                prev = ((prev == null) ? item : prev);
                prev = ((prev.getName() != item.getName()) ? item : prev); // upgraded the weapon?
                weapon = ((item.getRefine() > prev.getRefine()) ? item : prev); //get that Weapon with highest refine
                prev = item;
            }
        }
        return weapon;
    }

    // EFFECTS: get the highest tier armor (hence the equipped armor). Will return null if no armor.
    public Item getEquippedArmor() {
        int maxArmor = 5;
        Item armor = null;
        Item prev = null;
        for (Item item : items) {
            if (item instanceof Armor && item.getStat() >= maxArmor) {
                maxArmor = item.getStat(); //get the Armor with highest hpStat)
                prev = ((prev == null) ? item : prev);
                prev = ((prev.getName() != item.getName()) ? item : prev); //upgraded armor?
                armor = ((item.getRefine() > prev.getRefine()) ? item : prev); //get that Armor with highest refine
                prev = item;
            }
        }
        return armor;
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
        json.put("name", item.getName() + "-" + item.getRefine());
        return json;
    }

}
