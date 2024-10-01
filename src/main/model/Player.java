package model;

import java.util.List;

// Represents the Player
public class Player extends GameCharacter {
    
    int maxHP;

    Inventory inventory;

    // EFFECTS: create the Player with default inventory (no armor, 
    // Dagger as weapon, and 5 coins). Set the player hp (hit points) to 5, and dmg (damage) as 1.
    public Player() {
        maxHP = 5;
        hp = 5;
        inventory = new Inventory(this);
    }

    // REQUIRES: hp <= maxHP
    // MODIFIES: this
    // EFFECTS: heal hp by one. Can only heal up to maxHP
    public void heal() {
        if (hp < maxHP) {
            hp++;
        }
    }

    // REQUIRES: inventory is not empty, a weapon must be equipped
    // MODIFIES: this
    // EFFECTS: update the player's damage to the highest tier weapon in
    // the inventory.
    public void updateWeapon(List<Item> items) {
        int maxDmg = 0;
        for (Item item : items) {
            if (item instanceof Weapon & item.getStat() > maxDmg) {
                maxDmg = item.getStat(); //get the weapon with highest stats
            }
        }
        this.setDamage(maxDmg);
    }

    // MODIFIES: this
    // EFFECTS: update the player's health to the highest tier armor in the inventory.
    // If the inventory has no armors left, then MAX player hp goes to the default 5.
    public void updateArmor(List<Item> items) {
        int maxArmor = 5;
        for (Item item : items) {
            if (item instanceof Armor & item.getStat() > maxArmor) {
                maxArmor = item.getStat(); //get the Armor with highest stats
            }
        }
        this.setMaxHP(maxArmor);
    }

    public void setMaxHP(int newMaxHP) {
        maxHP = newMaxHP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public Inventory getInventory() {
        return inventory;
    }

    // Basic getters and setters all in GameCharacter superclass (eg. getHealth, setDamage, etc.)

}
