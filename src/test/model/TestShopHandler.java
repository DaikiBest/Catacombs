package model;

import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestShopHandler {

    private ShopHandler testShop;
    private List<Item> testSList;
    private List<String> actualSList = new ArrayList<>();
    private Item testMace;
    private Item testExcalibur;
    private Item testCap;
    private Item testHelmet;
    private Player testPlayer;
    private Inventory testInventory;

    @BeforeEach
    void runBefore() {
        testShop = new ShopHandler();
        testSList = testShop.getShopList();
        testMace = new Weapon("Mace", 2, 2);
        testExcalibur = new Weapon("Excalibur", 5, 10);
        testCap = new Armor("Farmer's Cap", 7, 2);
        testHelmet = new Armor("Knight's helmet", 12, 6);
        testPlayer = new Player();
        testInventory = testPlayer.getInventory();

        //create the list with all item names to compare
        actualSList.add("Mace");
        actualSList.add("Longsword");
        actualSList.add("Excalibur");
        actualSList.add("Farmer's cap");
        actualSList.add("Thieve's hood");
        actualSList.add("Knight's helmet");
        actualSList.add("Crown");
    }

    @Test
    void testConstructor() {
        List<String> testSListNames = new ArrayList<>();
        for (Item item : testSList) {
            testSListNames.add(item.getName());
        }

        // compare both lists with all items names
        assertEquals(actualSList, testSListNames);
        assertEquals(2, testShop.getHealPrice());
    }

    @Test
    void testPurchaseItemSuccess() {
        testInventory.setCoins(4);
        assertEquals(null, testInventory.getItem("Mace"));
        assertTrue(testShop.purchaseItem(testMace, testPlayer));
        assertEquals("Mace", testInventory.getItem("Mace").getName());
        assertEquals(0, testInventory.getCoins());
    }

    @Test
    void testPurchaseFail() {
        testInventory.setCoins(3);
        assertEquals(null, testInventory.getItem("Mace"));
        assertFalse(testShop.purchaseItem(testMace, testPlayer));
        assertEquals(null, testInventory.getItem("Mace"));
        assertEquals(3, testInventory.getCoins());
    }

    @Test
    void testPurchaseTwoTooExpensive() {
        testInventory.setCoins(41);
        assertEquals(null, testInventory.getItem("Mace"));
        assertEquals(null, testInventory.getItem("Excalibur"));
        assertEquals(null, testInventory.getItem("Farmer's Cap"));
        assertEquals(null, testInventory.getItem("Knight's Helmet"));

        assertTrue(testShop.purchaseItem(testMace, testPlayer));
        assertTrue(testShop.purchaseItem(testExcalibur, testPlayer));
        assertTrue(testShop.purchaseItem(testCap, testPlayer));
        assertTrue(testShop.purchaseItem(testCap, testPlayer));
        assertFalse(testShop.purchaseItem(testHelmet, testPlayer));
        assertTrue(testShop.purchaseItem(testMace, testPlayer));
        assertFalse(testShop.purchaseItem(testExcalibur, testPlayer));

        int countM = testInventory.countItem("Mace", testInventory.getItems());
        int countE = testInventory.countItem("Excalibur", testInventory.getItems());
        int countC = testInventory.countItem("Farmer's Cap", testInventory.getItems());
        int countH = testInventory.countItem("Knight's Helmet", testInventory.getItems());

        assertEquals(2, countM); //two maces
        assertEquals(1, countE); //one excalibur
        assertEquals(2, countC); //two caps
        assertEquals(0, countH); //no helmets

        assertEquals(5, testInventory.getCoins());
    }

    @Test
    void testPurchaseHealing() {
        //before
        testPlayer.setHealth(1);
        assertEquals(5, testPlayer.getInventory().getCoins());
        testShop.purchaseHealing(testPlayer);
        //after
        assertEquals(2, testPlayer.getHealth());
        assertEquals(3, testPlayer.getInventory().getCoins());
    }

    @Test //TODO
    void testPurchaseHealingFail() {
        testPlayer.setHealth(1);
        assertEquals(5, testPlayer.getInventory().getCoins());
        testShop.purchaseHealing(testPlayer);
        assertEquals(2, testPlayer.getHealth());
        assertEquals(3, testPlayer.getInventory().getCoins());
    }

    @Test //sell for half the Price //TODO
    void testSellItem() {

    }
}
