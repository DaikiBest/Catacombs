package model;
public class GameCharacter {
    
    int hp;
    int dmg;

    // EFFECTS: roll D20 (1-20) dice. ie. return random number between 1 and 20
    public int rollDice() {
        return 0;
    }

    // EFFECTS: attack opponent using this dmg
    public void attack() {
        //stub
    }

    // REQUIRES: dmgInflicted >= 0, this hp > 0
    // MODIFIES: this
    // EFFECTS: this loses hp by the amount dmgInflicted
    public void takeDamage(int dmgInflicted) {
        hp = hp - dmgInflicted;
    }

    public void setDamage(int dmg) {
        this.dmg = dmg;
    }

    public void setHealth(int hp) {
        this.hp = hp;
    }

    public int getDamage() {
        return dmg;
    }

    public int getHealth() {
        return hp;
    }
}
