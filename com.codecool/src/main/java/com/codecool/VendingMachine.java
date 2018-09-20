package com.codecool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VendingMachine {
    public Integer coinReturn = 0;
    Map<String, Integer> accteptable;
    Map<Integer, Double> productsPrice;
    Integer currentAmount = 0;

    public VendingMachine() {
    }

    public boolean checkIfAccetable(String coin) {
        if(accteptable.get(coin)!= null) {
            return true;
        }
        return false;
    }

    public void getAccteptableMap(String filename) {
        Map<String, Integer> accteptable = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] item = line.split(", ");
                accteptable.put(item[0], Integer.valueOf(item[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.accteptable = accteptable;
    }

    public void collect(String coin) {
        if(checkIfAccetable(coin)) {
            this.currentAmount += accteptable.get(coin);
        }
    }

    public String display() {
        if (currentAmount != 0) {
            return String.valueOf(this.currentAmount);
        }
        return "INSERT COINS";
    }

    public void getProductFromFile(String productFilename) {
        Map<Integer, Double> products = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(productFilename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] item = line.split(", ");
                products.put(Integer.valueOf(item[1]), Double.valueOf(item[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.productsPrice = products;
    }

    public double getPrice(int productPlace) {
        return productsPrice.get(productPlace);
    }
}
