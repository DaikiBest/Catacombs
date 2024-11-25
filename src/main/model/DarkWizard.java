package model;

// Represents an enemy Dark Wizard (the final boss enemy)
public class DarkWizard extends GameCharacter {

    // EFFECTS: create the Dark Wizard with 8hp and 5dmg. It has
    // a low and high values of 6 and 12 respectively. These are the lowest and 
    // highest dice rolls the Dark Wizard can roll.
    public DarkWizard() {
        setName("Dark Wizard");
        setHealth(8);
        setDamage(5);
        setLow(10);
        setHigh(20);
    }

}