package model;

import java.util.List;
import java.util.ArrayList;

// Represents a shop. Will perform the actions of shop.
public class ShopHandler {
    
    private int healPrice;

    // EFFECTS: create a shop with a list of all items and healPrice of 2 coins
    public ShopHandler() {
        //stub
    }

    // EFFECTS: purchase one chosen item. Deduct coin value of the item from
    // the player's inventory. If player does not have enough coins !(coins >= cost),
    // then purchase is unsucessful and returns false; otherwise, purchase item and return true.
    public boolean purchaseItem(Item item, Player player) {
        return true;
    }

    // EFFECTS: use 2 coins (deduct from inventory) to heal 1 hp. Purchase is successful if
    // player has more or equal to 2 coins, and returns true. Fails purchase if player does not
    // have enough and returns false.
    public boolean purchaseHealing(Player player) {
        return true;
    }

    // EFFECTS: sell one item for coins from the player's inventory. Removes one copy
    //  of the item from the inventory, and adds coins in equivalent value in return.
    public void sellItem(Inventory inventory) {

    }

    public List<Item> getShopList() {
        return new ArrayList<>();
    }

    public int getItemPrice(Item item) {
        return 0;
    }

    public int getHealPrice() {
        return healPrice;
    }
}
