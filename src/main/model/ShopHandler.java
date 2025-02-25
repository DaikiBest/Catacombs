package model;

import java.util.List;
import java.util.ArrayList;

// Represents a shop. Will perform the actions of shop.
public class ShopHandler {
    private List<String> shopList;
    private int healPrice;
    private int refinePrice;

    private ItemFactory itemFactory = new ItemFactory();

    // EFFECTS: create a shop with a list of all item names, healPrice (1 coin), and refinePrice (4 coins)
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
        shopList.add("Heal");
        
        healPrice = 1;
        refinePrice = 4;
    }

    // REQUIRES: index < shopList.size() - 1
    // MODIFIES: inventory
    // EFFECTS: aquire item and deduct twice the value of the item if player has
    // enough coins, return true. Don't purchase if this is not met, return false.
    public boolean purchaseItem(int index, Player player) {
        Inventory inventory = player.getInventory();
        Item item = itemFactory.makeItem(shopList.get(index));
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
    // EFFECTS: use 1 coin (deduct from inventory) to heal 1 hp. Purchase is successful if
    // player has more or equal to 1 coin, returning true. Fails purchase if player does not
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

    // REQUIRES: index < inventoryList.size() - 1
    // REQUIRES: item to be sold is in inventory.
    // MODIFIES: inventory
    // EFFECTS: sell one item for coins from the player's inventory. Removes the item
    // selected (at the given index) from the inventory, and adds coins in equivalent value in return.
    // Won't allow to sell last weapon. Items sell for the actual value.
    public boolean sellItem(int index, Player player) {
        Inventory inventory = player.getInventory();
        Item item = inventory.getItems().get(index);

        if (item instanceof Weapon) { //selling weapon?
            int weaponCount = 0;
            for (Item i : inventory.getItems()) {
                if (i instanceof Weapon) {
                    weaponCount++; //count number of weapons in inventory
                }
            }
    
            if (weaponCount <= 1) { //at least more than one weapon in inventory
                return false;
            }
        }
        inventory.discard(index, player);
        inventory.collectCoins(item.getValue() + item.getRefine() * refinePrice / 2);
        return true;
    }

    // REQUIRES: index < inventoryList.size() - 1
    // MODIFIES: item, inventory
    // EFFECTS: refine an item in your inventory for refinePrice. Will return true if successful,
    // false if fails (either not enough coins or item is max refined).
    public boolean purchaseRefine(int index, Inventory inventory) {
        Item item = inventory.getItems().get(index);

        if (inventory.getCoins() >= refinePrice & item.getRefine() < 3) {
            item.refine();
            inventory.deductCoins(refinePrice);
            return true;
        } else {
            // can't afford or item is max refined
            return false;
        }
    }

    public List<String> getShopList() {
        return shopList;
    }

    public int getHealPrice() {
        return healPrice;
    }

    public int getRefinePrice() {
        return refinePrice;
    }
}
