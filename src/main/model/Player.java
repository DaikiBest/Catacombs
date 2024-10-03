package model;

import java.util.List;
import java.util.Random;

// Represents the Player
public class Player extends GameCharacter {
    
    int maxHP;
    private Random random = new Random();

    Inventory inventory;

    // EFFECTS: create the Player with default inventory (no armor, 
    // Dagger as weapon, and 5 coins). Set the player hp and maxHP (hit points) to 5, and dmg (damage) as 1.
    public Player() {
        maxHP = 5;
        setHealth(maxHP);
        inventory = new Inventory(this);
    }

    // REQUIRES: hp <= maxHP
    // MODIFIES: this
    // EFFECTS: heal hp by one. Can only heal up to maxHP
    public void heal() {
        if (getHealth() < maxHP) {
            setHealth(getHealth() + 1);
        }
    }

    // REQUIRES: inventory is not empty, inventory must have at least one weapon
    // MODIFIES: this
    // EFFECTS: update the player's damage to the highest tier weapon in
    // the inventory.
    public void updateWeapon(List<Item> items) {
        int maxDmg = 0;
        for (Item item : items) {
            if (item instanceof Weapon && item.getStat() > maxDmg) {
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
            if (item instanceof Armor && item.getStat() > maxArmor) {
                maxArmor = item.getStat(); //get the Armor with highest stats
            }
        }
        this.setMaxHP(maxArmor);
    }

    @Override
    // EFFECTS: roll D20 (1-20) dice. ie. return random number between 1 and 20
    public int rollDice() {
        return random.nextInt(19) + 1;
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
