# Catacombs

## Description
Catacombs is a rogue-like dungeon-crawler inspired by D&D where you traverse different rooms inhabitted by a random encounter. You may fight monsters, encounter a shop, or encounter loot. Your goal is to defeat the "Dark Wizard" who's entrance will appear every 5 rooms (ie. 5, 10, 15 ...).
To run the game, clone the repository and run the file "MainGUI" for the default experience. "MainCLI" is also available as a CLI experience.

## Instructions
### General
You have health and damage stats (shown on the nav bar above), as well as your enemies (shown above their portraits). You will deal exactly DMG damage to the enemy, and so will the enemy to your health. Do not let your health drop to zero; otherwise, you lose. At any time, you may see your inventory of all the items you hold; do so by clicking the "bag" image on the top right corner of the app.
### Shop
Press open shop to open the menu. You will have the option to buy, sell, or "refine" items.
- Buy: you may choose to buy any time, given you have enough coins to purchase them. These will either increase your damage, increase your max health, or heal your current health. Note that buying a piece of armor that increases your current maxHP will increase your HP up to maxHP (ie. heal you to max Health). The DMG and MaxHP stats do not stack; your "best" weapon and armor are chosen as equipped according to their highest stat (priority #1) and the highest refinement (priority #2).
- Sell: you may choose to sell your items for half their original price on the shops. You cannot sell your last weapon (you must have at least one weapon at all times). Refinements on items are also refuned at half the original price.
- Refine: apply refinement levels to your weapons or armors. Refined weapons increase dice rolled by 1 for each refine level (for a level 2 weapon, if you roll a 10, it will become a 12). On the other hand, refined armors decrease the enemy's dice roll by 1 for each refine level. Refinements only apply for equipped item, and have a ceiling of 3 levels.
### Enemy Encounter
You may choose to flee an enemy encounter with a chance to be unsuccesful and take damage.
Otherwise, you may attack the enemy, where dice will be thrown. Your dice is blue (left), the enemy's is red (right). After dice are thrown, if the player has refinements, the dice are updated accordingly. The one who has the lower dice roll will take damage according to their opponent's DMG (damage) stat. 
