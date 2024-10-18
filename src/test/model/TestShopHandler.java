package model;

import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestShopHandler {

    private ShopHandler testShop = new ShopHandler();
    private List<String> testSList;
    private List<String> actualSList = new ArrayList<>();
    private Item testLongsword;
    private Item testExcalibur;
    private Item testCrown;
    private Item testHelmet;
    private Item testInvalidItem;
    private Player testPlayer = new Player();
    private Inventory testInventory;
    private ItemFactory itemFactory = new ItemFactory();

    @BeforeEach
    void runBefore() {
        testSList = testShop.getShopList();
        testLongsword = itemFactory.makeItem("longsword");
        testExcalibur = itemFactory.makeItem("excalibur");
        testCrown = itemFactory.makeItem("crown");
        testHelmet = itemFactory.makeItem("knight's helmet");
        testInvalidItem = itemFactory.makeItem("invalid item"); //null
        testInventory = testPlayer.getInventory();

        //create the list with all item names to compare
        actualSList.add("dagger");
        actualSList.add("mace");
        actualSList.add("longsword");
        actualSList.add("excalibur");
        actualSList.add("farmer's cap");
        actualSList.add("thieve's hood");
        actualSList.add("knight's helmet");
        actualSList.add("crown");
        actualSList.add("heal");

        testInventory.collect(itemFactory.makeDagger(), testPlayer);
    }

    @Test
    void testConstructor() {
        // compare both lists with all items names
        assertEquals(actualSList, testSList);
        assertEquals(1, testShop.getHealPrice());
    }

    @Test
    void testPurchaseItemSuccess() {
        testInventory.setCoins(10);
        assertEquals(null, testInventory.getItem("Longsword"));
        assertTrue(testShop.purchaseItem(testLongsword, testPlayer));
        assertEquals("Longsword", testInventory.getItem("Longsword").getName());
        assertEquals(0, testInventory.getCoins());
    }

    @Test
    void testPurchaseFail() {
        testInventory.setCoins(3);
        assertEquals(null, testInventory.getItem("Longsword"));
        assertFalse(testShop.purchaseItem(testLongsword, testPlayer));
        assertEquals(null, testInventory.getItem("Longsword"));
        assertEquals(3, testInventory.getCoins());
    }

    @Test
    void testPurchaseTwoTooExpensive() {
        testInventory.setCoins(70);
        assertEquals(null, testInventory.getItem("Longsword"));
        assertEquals(null, testInventory.getItem("Excalibur"));
        assertEquals(null, testInventory.getItem("Crown"));
        assertEquals(null, testInventory.getItem("Knight's Helmet"));

        assertTrue(testShop.purchaseItem(testLongsword, testPlayer));  //-10
        assertTrue(testShop.purchaseItem(testExcalibur, testPlayer));  //-20
        assertTrue(testShop.purchaseItem(testHelmet, testPlayer));     //-12
        assertTrue(testShop.purchaseItem(testHelmet, testPlayer));     //-12
        assertFalse(testShop.purchaseItem(testCrown, testPlayer));     //fail
        assertTrue(testShop.purchaseItem(testLongsword, testPlayer));  //-10
        assertFalse(testShop.purchaseItem(testExcalibur, testPlayer)); //fail

        int countL = testInventory.countItem("Longsword", testInventory.getItems());
        int countE = testInventory.countItem("Excalibur", testInventory.getItems());
        int countH = testInventory.countItem("Knight's Helmet", testInventory.getItems());
        int countC = testInventory.countItem("Crown", testInventory.getItems());

        assertEquals(2, countL); //two Longswords
        assertEquals(1, countE); //one Excalibur
        assertEquals(2, countH); //two Helmets
        assertEquals(0, countC); //zero Crowns

        assertEquals(6, testInventory.getCoins());
    }

    @Test
    void testPurchaseHealing() {
        //before
        testPlayer.setHealth(1);
        testInventory.setCoins(2);
        assertTrue(testShop.purchaseHealing(testPlayer));
        //after
        assertEquals(2, testPlayer.getHealth());
        assertEquals(1, testInventory.getCoins());
    }

    @Test
    void testPurchaseHealingFailFullHP() {
        //default maxHP and hp are both 5.
        assertFalse(testShop.purchaseHealing(testPlayer));
        assertEquals(5, testPlayer.getHealth());
        assertEquals(5, testInventory.getCoins());
    }

    @Test
    void testPurchaseHealingFailPoor() {
        testPlayer.setHealth(2);
        testInventory.setCoins(0);
        assertFalse(testShop.purchaseHealing(testPlayer));
        assertEquals(2, testPlayer.getHealth());
        assertEquals(0, testInventory.getCoins());
    }

    @Test
    void testSellItemOneCopy() {
        testInventory.collect(testLongsword, testPlayer);
        testInventory.collect(testLongsword, testPlayer);
        assertTrue(testShop.sellItem("Longsword", testPlayer));
        assertEquals(1, testInventory.countItem("Longsword", testInventory.getItems()));
        assertEquals(10, testInventory.getCoins());
    }

    @Test
    void testSellItemsNotLastWeapon() {
        testInventory.collect(testLongsword, testPlayer);
        testInventory.collect(testCrown, testPlayer);
        testInventory.collect(testCrown, testPlayer);
        testInventory.collect(testExcalibur, testPlayer);
        testInventory.collect(testLongsword, testPlayer);
        assertTrue(testShop.sellItem("Longsword", testPlayer)); //+5
        assertTrue(testShop.sellItem("Crown", testPlayer)); //+10
        assertTrue(testShop.sellItem("Crown", testPlayer)); //+10
        assertTrue(testShop.sellItem("Dagger", testPlayer)); //+1
        assertTrue(testShop.sellItem("Excalibur", testPlayer)); //+10
        assertFalse(testShop.sellItem("Longsword", testPlayer)); //no sell.
        assertEquals(1, testInventory.getItems().size());
        assertEquals("Longsword", testInventory.getItem("Longsword").getName());
        assertEquals(41, testInventory.getCoins());
    }

    @Test
    void testInvalidItem() {
        assertEquals(null, testInvalidItem);
    }
}
