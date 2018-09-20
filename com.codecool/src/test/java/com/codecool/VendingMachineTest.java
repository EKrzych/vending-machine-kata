package com.codecool;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineTest {

    private String filename  = "/Users/elzbietakrzych/Documents/codecool/ADVANCED/2018_09_17_TW/vending-machine-kata/com.codecool/src/main/resources/accteptable.csv";
    private VendingMachine machine = new VendingMachine();
    private String productFilename = "/Users/elzbietakrzych/Documents/codecool/ADVANCED/2018_09_17_TW/vending-machine-kata/com.codecool/src/main/resources/productList.csv";

    @ParameterizedTest
    @ValueSource(strings = {"nickle", "dime", "quarter"})
    public void shoudAcceptNicles(String coin) {
        machine.getAccteptableMap(filename);
        assertTrue(machine.checkIfAccetable(coin));
    }

    @ParameterizedTest
    @ValueSource(strings = {"penny"})
    public void shoudNOTAcceptNicles(String coin) {
        machine.getAccteptableMap(filename);
        assertFalse(machine.checkIfAccetable(coin));
    }

    @Test
    public void shouldReadaccettableFromFile() {
        machine.getAccteptableMap(filename);
        assertNotNull(machine.accteptable);
        assertNotNull(machine.coinStock);
    }

    @Test
    public void shouldAddValueToCurrentAmount() {
        machine.getAccteptableMap(filename);
        machine.collect("nickle");
        machine.collect("nickle");
        machine.collect("dime");
        machine.collect("quarter");
        assertEquals(Integer.valueOf(45), machine.currentAmount);
    }

    @Test
    public void shoudDisplayAmount() {
        machine.getAccteptableMap(filename);
        machine.getProductFromFile(productFilename);
        machine.collect("nickle");
        machine.collect("nickle");
        machine.collect("dime");
        machine.collect("quarter");
        assertEquals(String.valueOf(45), machine.display());
    }

    @Test
    public void shoudDisplayInsertCoin() {
        machine.getAccteptableMap(filename);
        assertEquals("INSERT COINS", machine.display());
    }

    @Test
    public void shoudPutPenniesIntoReturn() {
        machine.getAccteptableMap(filename);
        machine.collect("penny");
        assertEquals("INSERT COINS", machine.display());
    }

    @Test
    public void shouldReadProductsFromFile() {
        machine.getProductFromFile(productFilename);
        assertNotNull(machine.products);
    }

    @Test
    public void shouldCheckPrice() {
        machine.getProductFromFile(productFilename);
        assertEquals(1.00, machine.getPrice(1));
        assertEquals(0.5, machine.getPrice(2));
        assertEquals(0.65, machine.getPrice(3));
    }

    @Test
    public void shoudCheckQuantity (){
        machine.getProductFromFile(productFilename);
        assertEquals(5, machine.getQuantity(1));
        assertEquals(7, machine.getQuantity(2));
        assertEquals(3, machine.getQuantity(3));
    }

    @Test
    public void shouldDisplayTHANKYOU() {
        machine.getAccteptableMap(filename);
        machine.getProductFromFile(productFilename);
        machine.collect("quarter");
        machine.collect("quarter");
        machine.chooseProductFromPlace(2);
        assertEquals("THANK YOU", machine.display());
    }

    @Test
    public void shouldTakeProductFromStock() {
        machine.getAccteptableMap(filename);
        machine.getProductFromFile(productFilename);
        machine.collect("quarter");
        machine.collect("quarter");
        machine.chooseProductFromPlace(2);
        machine.display();
        assertEquals(6, machine.getQuantity(2));

    }

    @Test
    public void shouldTakeProductFromStockANDsetCurrentAmountTo0() {
        machine.getAccteptableMap(filename);
        machine.getProductFromFile(productFilename);
        machine.collect("quarter");
        machine.collect("quarter");
        machine.chooseProductFromPlace(2);
        machine.display();
        assertEquals("INSERT COINS", machine.display());
        assertEquals(Integer.valueOf(0), machine.currentAmount);
    }

    @Test
    public void shoudDisplayWhenNotEnoughMoney() {
        machine.getAccteptableMap(filename);
        machine.getProductFromFile(productFilename);
        machine.collect("quarter");
        machine.chooseProductFromPlace(2);
        assertEquals("PRICE 0.5$", machine.display());
    }

    @Test
    public void shoudDisplayMoneyWhenNotEnoughMoney() {
        machine.getAccteptableMap(filename);
        machine.getProductFromFile(productFilename);
        machine.collect("quarter");
        machine.chooseProductFromPlace(2);
        machine.display();
        assertEquals("25", machine.display());
    }

    @Test
    public void shouldReturnTheRest() {
        machine.getAccteptableMap(filename);
        machine.getProductFromFile(productFilename);
        machine.collect("quarter");
        machine.collect("quarter");
        machine.collect("quarter");
        machine.chooseProductFromPlace(2);
        assertEquals("THANK YOU", machine.display());
        assertEquals(1, machine.coinReturn.size());
        assertEquals("quarter", machine.coinReturn.get(0));
    }
    @Test
    public void shouldReturnTheRestdime() {
        machine.getAccteptableMap(filename);
        machine.getProductFromFile(productFilename);
        machine.coinReturn = new ArrayList<>();
        machine.collect("quarter");
        machine.collect("quarter");
        machine.collect("quarter");
        machine.collect("dime");
        machine.collect("nickle");
        machine.chooseProductFromPlace(2);
        assertEquals("THANK YOU", machine.display());
        assertEquals(3, machine.coinReturn.size());
        assertEquals("quarter", machine.coinReturn.get(0));

        assertEquals("dime", machine.coinReturn.get(1));
        assertEquals("nickle", machine.coinReturn.get(2));
        assertEquals("INSERT COINS", machine.display());
    }

    @Test
    public void shouldReturnAllMoney() {
        machine.getAccteptableMap(filename);
        machine.getProductFromFile(productFilename);
        machine.coinReturn = new ArrayList<>();
        machine.collect("quarter");
        machine.collect("quarter");
        machine.collect("quarter");
        machine.collect("dime");
        machine.collect("nickle");
        assertEquals("INSERT COINS", machine.returnAllMoneyButton());
    }

    @Test
    public void shouldDisplaySOLDOUT() {
        machine.getAccteptableMap(filename);
        machine.getProductFromFile(productFilename);
        machine.coinReturn = new ArrayList<>();
        machine.collect("quarter");
        machine.collect("quarter");
        machine.chooseProductFromPlace(4);
        assertEquals("THANK YOU", machine.display());

        machine.collect("quarter");
        machine.collect("quarter");
        machine.chooseProductFromPlace(4);
        assertEquals("SOLD OUT", machine.display());
        assertEquals("50", machine.display());
        machine.chooseProductFromPlace(2);
        assertEquals("THANK YOU", machine.display());
        assertEquals("INSERT COINS", machine.display());
    }

    @Test
    public void shoudRequireEXACTCHANGE() {
        String filename = "/Users/elzbietakrzych/Documents/codecool/ADVANCED/2018_09_17_TW/vending-machine-kata/com.codecool/src/main/resources/accteptableEMPTYstock.csv";
        machine.getAccteptableMap(filename);
        machine.getProductFromFile(productFilename);
        assertTrue( machine.isNeededExactChange());
        assertEquals("EXACT CHANGE ONLY", machine.display());
    }

    @Test
    public void shoudUseAccessibleCoins() {
        String filename = "/Users/elzbietakrzych/Documents/codecool/ADVANCED/2018_09_17_TW/vending-machine-kata/com.codecool/src/main/resources/almostEmptyStock.csv";
        machine.getAccteptableMap(filename);
        machine.getProductFromFile(productFilename);
        machine.collect("quarter");
        machine.collect("quarter");
        machine.collect("quarter");
        machine.collect("quarter");
        machine.chooseProductFromPlace(2);
        assertEquals("THANK YOU", machine.display());
        assertEquals(10, machine.coinReturn.size());
        assertTrue(machine.coinStock.get("nickle") > 0);
        assertFalse(machine.coinStock.get("quarter") > 0);
        assertFalse(machine.coinStock.get("dime") > 0);
    }
}