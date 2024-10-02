package model;

public class BattleHandler {

    public BattleHandler() {
        //stub
    }

    // MODIFIES: player, enemy
    // EFFECTS: handle dice. If player rolls higher, return "won" and damage enemy.
    // If enemy rolls higher, return "loss" and damage player. If both rolls are equal,
    // return "tie" and no one gets damaged.
    public String diceHandler(GameCharacter player, GameCharacter enemy, int playerRoll, int enemyRoll) {
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

    // MODIFIES: inventory
    // EFFECTS: player has won. Give player their rewards (coins).
    public void giveRewards(Inventory inventory, GameCharacter enemy) {
        int reward = enemy.getReward();
        inventory.collectCoins(reward);
    }
}
