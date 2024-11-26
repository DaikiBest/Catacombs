package model;

// handles a battle encounter
public class BattleHandler {

    // MODIFIES: player, enemy
    // EFFECTS: handle dice. If player rolls higher, return "won" and damage enemy.
    // If enemy rolls higher, return "loss" and damage player. If both rolls are equal,
    // return "tie" and no one gets damaged.
    public String diceHandler(GameCharacter playerChar, GameCharacter enemy, int playerRoll, int enemyRoll) {
        Player player = (Player) playerChar; //cast it to a player, so it can access the inventory
        playerRoll = playerDiceRefined(playerRoll, player.getInventory());
        enemyRoll = enemyDiceRefined(enemyRoll, player.getInventory());

        if (playerRoll > enemyRoll) {
            enemy.takeDamage(player.getDamage());
            return "win";
        } else if (playerRoll < enemyRoll) {
            player.takeDamage(enemy.getDamage());
            return "lose";
        } else {
            return "tie";
        }
    }

    // MODIFIES: player, enemy
    // EFFECTS: refines player's dice
    public int playerDiceRefined(int playerRoll, Inventory inventory) {
        int weaponBoost = inventory.getEquippedWeapon().getRefine();
        playerRoll = ((playerRoll + weaponBoost > 20) ? 20 : playerRoll + weaponBoost);
        return playerRoll;
    }

    // MODIFIES: player, enemy
    // EFFECTS: refines enemy's dice
    public int enemyDiceRefined(int enemyRoll, Inventory inventory) {
        int armorBoost = ((inventory.getEquippedArmor() == null) ? 0 : inventory.getEquippedArmor().getRefine());
        enemyRoll = ((enemyRoll - armorBoost < 0) ? 0 : enemyRoll - armorBoost);
        return enemyRoll;
    }

    // MODIFIES: inventory
    // EFFECTS: player has won. Give player their rewards (coins).
    public void giveRewards(Inventory inventory, GameCharacter enemy) {
        int reward = enemy.getReward();
        inventory.collectCoins(reward);
    }
}
