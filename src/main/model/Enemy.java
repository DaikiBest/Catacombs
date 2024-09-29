package model;

// Represents the interface of an Enemy
public interface Enemy {

    // EFFECTS: if the enemy's health drops to 0 or below,
    // the enemy perishes and the enounter is over.
    public void Perish();

}