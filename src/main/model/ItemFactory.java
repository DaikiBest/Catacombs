package model;

// Handles the making of weapons and armors
public class ItemFactory {

    // EFFECTS: create an item given string. If invalid string, return null.
    public Item selectItem(String item) {
        if (item.equalsIgnoreCase("dagger")) {
            return makeDagger();
        } else if (item.equalsIgnoreCase("mace")) {
            return makeMace();
        } else if (item.equalsIgnoreCase("longsword")) {
            return makeLongsword();
        } else if (item.equalsIgnoreCase("excalibur")) {
            return makeExcalibur();
        } else if (item.equalsIgnoreCase("farmer's cap")) {
            return makeCap();
        } else if (item.equalsIgnoreCase("thieve's hood")) {
            return makeHood();
        } else if (item.equalsIgnoreCase("knight's helmet")) {
            return makeHelmet();
        } else if (item.equalsIgnoreCase("crown")) {
            return makeCrown();
        }   else {
            return null;
        }
    }

    // MAKE WEAPONS
    // EFFECTS: makes a Dagger with 1dmg and 1 value.
    public Item makeDagger() {
        return new Weapon("Dagger", 1, 1);
    }
    
    // EFFECTS: makes a Mace with 2dmg and 2 value.
    public Item makeMace() {
        return new Weapon("Mace", 2, 2);
    }

    // EFFECTS: makes a Longsword with 3dmg and 5 value.
    public Item makeLongsword() {
        return new Weapon("Longsword", 3, 5);
    }

    // EFFECTS: makes an Excalibur with 5dmg and 10 value.
    public Item makeExcalibur() {
        return new Weapon("Excalibur", 5, 10);
    }


    // MAKE ARMOR
    // EFFECTS: makes a Farmer's Cap with 7maxHP and 2 value.
    public Item makeCap() {
        return new Armor("Farmer's Cap", 7, 2);
    }
    
    // EFFECTS: makes a Mace with 9maxHP and 4 value.
    public Item makeHood() {
        return new Armor("Thieve's Hood", 9, 4);
    }

    // EFFECTS: makes a Longsword with 12maxHP and 6 value.
    public Item makeHelmet() {
        return new Armor("Knight's Helmet", 12, 6);
    }

    // EFFECTS: makes an Excalibur with 15maxHP and 10 value.
    public Item makeCrown() {
        return new Armor("Crown", 15, 10);
    }
}
