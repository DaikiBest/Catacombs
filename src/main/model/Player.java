package model;

import java.util.List;

// Represents the Player
public class Player extends GameCharacter {
    
    private int maxHP;
    private Inventory inventory;

    // EFFECTS: create the Player with default inventory (no armor, Dagger as weapon, and 5 coins). 
    // Set the player hp and maxHP (hit points) to 5, dmg (damage) as 1, and low and high dice values as 1 and 20.
    public Player() {
        maxHP = 5;
        setHealth(maxHP);
        inventory = new Inventory(this);
        setLow(1);
        setHigh(20);
    }

    // MODIFIES: this
    // EFFECTS: heal hp by one. Can only heal up to maxHP
    public void heal() {
        if (getHealth() < maxHP) {
            setHealth(getHealth() + 1);
        }
    }

    // MODIFIES: this
    // EFFECTS: update the player's damage to the highest tier weapon in
    // the inventory.
    public void updateWeapon(List<Item> items) {
        int maxDmg = inventory.getEquippedWeapon().getStat();
        this.setDamage(maxDmg);
    }

    // MODIFIES: this
    // EFFECTS: update the player's health to the highest tier armor in the inventory.
    // If the inventory has no armors left, then MAX player hp goes to the default 5.
    public void updateArmor(List<Item> items) {
        int prevMax = getMaxHP();
        Item equippedArmor = inventory.getEquippedArmor();
        int maxArmor = ((equippedArmor == null) ? 5 : equippedArmor.getStat());

        this.setMaxHP(maxArmor);
        if (prevMax < maxArmor || getHealth() > maxArmor) { //increase hp up to newly updated maxHP
            this.setHealth(maxArmor);
        }
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
