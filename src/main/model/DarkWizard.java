package model;

// Represents an enemy Dark Wizard (the final boss enemy)
public class DarkWizard extends GameCharacter implements Enemy {

    // EFFECTS: create the Dark Wizard with 8hp and 5dmg.
    public DarkWizard() {
        //stub
    }

    // EFFECTS: attack opponent using this dmg
    public void attack() {
        //stub
    }

    // REQUIRES: dmg >= 0, this hp > 0
    // MODIFIES: this
    // EFFECTS: this loses hp by the amount dmg
    public void takeDamage(int dmg) {
        //stub
    }

    // EFFECTS: if the enemy's health drops to 0 or below,
    // the enemy perishes and the enounter is over.
    public void Perish() {

    }
}
