package model;

// Represents an enemy Dark Wizard (the final boss enemy)
public class DarkWizard extends GameCharacter implements Enemy {

    // EFFECTS: create the Dark Wizard with 8hp and 5dmg. It has
    // a low and high values of 6 and 12 respectively. These are the lowest and 
    // highest dice rolls the Dark Wizard can roll.
    public DarkWizard() {
        hp = 8;
        dmg = 5;
        low = 10;
        high = 20;
    }

    // EFFECTS: if the enemy's health drops to 0 or below,
    // the enemy perishes and the enounter is over.
    public void Perish() {
        //stub
    }
}
