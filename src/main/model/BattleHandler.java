package model;
public class BattleHandler {
    
    int playerRoll;
    int enemyRoll;

    public BattleHandler() {
        //stub
    }

    // // EFFECTS: handle the back and forth battle between player and enemy.
    // public void battleHanddler(GameCharacter player, GameCharacter enemy) {
    //     while (inBattle) {
    //         enemyRoll = enemy.rollDice();
    //         // player rolls their dice
    //         playerRoll = player.rollDice();
    //         this.diceHandler(player, enemy);
    //     }
    // }

    // EFFECTS: handle dice. If player rolls higher, return "won" and damage enemy.
    // If enemy rolls higher, return "loss" and damage player. If both rolls are equal,
    // return "tie" and no one gets damaged.
    public String diceHandler(GameCharacter player, GameCharacter enemy) {
        if (playerRoll > enemyRoll) {
            enemy.takeDamage(player.getDamage());
            return "won";
        }
        else if (playerRoll < enemyRoll) {
            player.takeDamage(enemy.getDamage());
            return "lose";
        } else {
            return "tie";
        }
    }

    // EFFECTS: player has won. Give player their rewards (coins) and end encounter. 
    // If enemy defeated was Dark Wizard, end Game.
    public void endEncounter(Inventory inventory, GameCharacter enemy) {
        int reward = enemy.getReward();
        inventory.collectCoins(reward);
    }

    public int getPlayerRoll() {
        return playerRoll;
    }

    public int getEnemyRoll() {
        return enemyRoll;
    }
}
