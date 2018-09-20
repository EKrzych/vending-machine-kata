package com.codecool;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


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
        assertNotNull(machine.productsPrice);
    }

    @Test
    public void shouldCheckPrice() {
        machine.getProductFromFile(productFilename);
        assertEquals(1.00, machine.getPrice(1));
    }
}