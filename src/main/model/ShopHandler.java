package model;

import java.util.List;
import java.util.ArrayList;

// Represents a shop. Will perform the actions of shop.
public class ShopHandler {
    
    List<String> shopList;
    private int healPrice;
    private ItemHandler itemHandler = new ItemHandler();

    // EFFECTS: create a shop with a list of all items and healPrice of 2 coins
    public ShopHandler() {
        shopList = new ArrayList<>();
        shopList.add("Dagger");
        shopList.add("Mace");
        shopList.add("Longsword");
        shopList.add("Excalibur");
        shopList.add("Farmer's Cap");
        shopList.add("Thieve's Hood");
        shopList.add("Knight's Helmet");
        shopList.add("Crown");
        
        healPrice = 2;
    }

    // MODIFIES: inventory
    // EFFECTS: aquire item and deduct twice the value of the item if player has
    // enough coins, return true. Don't purchase if this is not met, return false.
    public boolean purchaseItem(Item item, Player player) {
        Inventory inventory = player.getInventory();
        int cost = item.getValue() * 2;

        if (inventory.getCoins() >= cost) {
            inventory.collect(item, player);
            inventory.deductCoins(cost);
            return true;
        } else {
            // can't afford
            return false;
        }
    }

    // MODIFIES: inventory, player
    // EFFECTS: use 2 coins (deduct from inventory) to heal 1 hp. Purchase is successful if
    // player has more or equal to 2 coins, returning true. Fails purchase if player does not
    // have enough coins or player is at full hp, returning false.
    public boolean purchaseHealing(Player player) {
        Inventory inventory = player.getInventory();

        if (inventory.getCoins() >= healPrice & player.getHealth() < player.getMaxHP()) {
            player.heal();
            inventory.deductCoins(healPrice);
            return true;
        } else {
            // can't afford or player at full HP
            return false;
        }
    }

    // REQUIRES: item to be sold is in inventory.
    // MODIFIES: inventory
    // EFFECTS: sell one item for coins from the player's inventory. Removes one copy
    // of the item from the inventory, and adds coins in equivalent value in return.
    // Won't allow to sell last weapon. Items sell for the actual value.
    public void sellItem(String itemName, Player player) {
        Inventory inventory = player.getInventory();
        Item item = inventory.getItem(itemName);

        if (item instanceof Weapon) { //selling weapon?
            int weaponCount = 0;
            for (Item i : inventory.getItems()) {
                if (i instanceof Weapon) {
                    weaponCount++; //count number of weapons in inventory
                }
            }
    
            if (weaponCount > 1) { //at least more than one weapon in inventory
                inventory.discard(itemName, player);
                inventory.collectCoins(item.getValue());
            } else {
                // cannot sell last weapon
            }
        } else { //player selling armor
            inventory.discard(itemName, player);
            inventory.collectCoins(item.getValue());
        }
    }

    public List<String> getShopList() {
        return shopList;
    }

    public int getHealPrice() {
        return healPrice;
    }
}
