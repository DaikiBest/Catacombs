# My Personal Project

## Proposal

This program will be a desktop RPG adventure video game inspired by the tabletop game Dungeons and Dragons. The game's premise is a room-by-room progression in linear order where each room holds a random encounter (enemy, loot, or shop). The player must survive and thrive in these encounters – increasing their player's power by gathering items and gold – ultimately to defeat a final (stronger) enemy. 
This project is of interest to me as I enjoy playing video games. I would like to apply the game design knowledge that I have aquired over the years to develop my own video game.

The intended user:
- enjoys adventure games and a medieval setting (likes general role-playing and RPG games).
- does not mind randomizers in games.
- would like a short experience that can be finished within a few minutes.

User stories:
- As a user, I want to be able to store my items (weapons, armor and coins) in my inventory.
- As a user, I want to be able to view the list of my items.
- As a user, I want to be able to combat enemies by rolling a random dice.
- As a user, I want to be able to view relevant information to the encounter (eg. my and the enemy’s health and damage during a combat encounter).
- As a user, I want to be able to purchase items using gold accumulated throughout encounters.
- As a user, I want to be able to sell items of my choice for gold.
- As a user, I want the game to end when your health drops to zero, and win when you defeat the final (boss) enemy.
- As a user, I want to be able to have the option to save my game when I reach a "rest spot" (ie. in-between encounters).
- As a user, I want to be able to load my game from a file, in order to continue the game where I left off last time (also at a "rest spot").

## Instructions for End User
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by running the application, then going through doors and encounters by clicking "enter" below the door and then going through the given encounter, until eventually going through a door leads to a shop encounter. In the shop encounter, click "open shop", go to the "buy" menu, and purchase an item in order to "add multiple X's to a Y" (X is the item, Y is the inventory).
- You can generate the second required action by once again going through encounters and reaching a shop. Then, go to the third menu labelled "refine". Then click any of your items to "refine" this item (given you have enough coins) which will level up that item and make it either increase all your dice rolls by the refine level during enemy encounters (in the case of refining a weapon), or decrease all the dice rolls of the enemies by the refine level (Eg. with a dagger refined level 2 and thieve's hood level 1, if you roll a 7, it will turn into a 9, and if the enemy rolled a 12, it will turn into an 11).
- You can locate my visual component throughout the game, such as the door image, the fireplace, the various enemies, etc.
- You can save and load the state of my application by clicking on "rest" when in a regular room (the room with a door with "enter" or fireplace with "rest"). Then, a popup will appear asking to either "save" or "load". Clicking either, will give the desired loading and saving effects.

## Phase 2 Task 2:
Sun Nov 24 16:43:18 PST 2024
Added Dagger to inventory
Sun Nov 24 16:43:21 PST 2024
Added Mace to inventory
Sun Nov 24 16:43:24 PST 2024
Removed Dagger from inventory
Sun Nov 24 16:43:35 PST 2024
Refined Mace to level 1
Sun Nov 24 16:43:53 PST 2024
Added Farmer's Cap to inventory

Citation (for JSON reading and writing):
  UBC Computer Science (2020) JsonSerializationDemo[Source Code]. https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.
