package model;

// Represents a shop. Will perform the actions of shop.
public class ShopHandler {
    
    private int healPrice;

    // EFFECTS: create a shop with a list of all items and healPrice of 2 coins
    public ShopHandler() {
        //stub
    }

    // EFFECTS: purchase one chosen item. Deduct coin value of the item from
    // the player's inventory.
    public void purchaseItem(Item item) {
        //stub
    }

    // MODIFIES: inventory
    // EFFECTS: use 2 coins (deduct from inventory) to heal 1 hp.
    public void purchaseHealing() {
        //stub
    }
}
