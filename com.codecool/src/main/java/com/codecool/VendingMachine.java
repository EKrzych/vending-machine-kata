package com.codecool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

public class VendingMachine {
    List<String> coinReturn = new ArrayList<>();
    Map<String, Integer> accteptable;
    Map<String, Integer> coinStock;
    Map<Integer, Product> products;
    Integer currentAmount = 0;
    Integer choosenProduct;

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
        Map<String, Integer> coinStock = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] item = line.split(", ");
                accteptable.put(item[0], Integer.valueOf(item[1]));
                coinStock.put(item[0], Integer.valueOf(item[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.accteptable = accteptable;
        this.coinStock = coinStock;
    }

    public void collect(String coin) {
        if(checkIfAccetable(coin)) {
            this.currentAmount += accteptable.get(coin);
        }
    }

    public String display() {
        if(this.choosenProduct!= null) {
            Double tempPriceProduct = this.products.get(choosenProduct).getPrice()*100;
            Integer priceProduct = tempPriceProduct.intValue();
            if(this.currentAmount >= priceProduct) {

                Product productToChange = this.products.get(choosenProduct);
                Integer previousQuantity = productToChange.getQuantity();
                if(previousQuantity == 0) {
                    choosenProduct = null;
                    return "SOLD OUT";
                }
                productToChange.setQuantity(previousQuantity-1);
                this.choosenProduct = null;
                giveChange(this.currentAmount - priceProduct);
                this.currentAmount = 0;

                return "THANK YOU";
            } else if(this.currentAmount < priceProduct) {
                String information = "PRICE " + this.products.get(choosenProduct).getPrice() + "$";
                this.choosenProduct = null;
                return information;
            }
        } else if (currentAmount != 0) {
            return String.valueOf(this.currentAmount);
        }
        if(isNeededExactChange()) {
           return "EXACT CHANGE ONLY";
        }
        return "INSERT COINS";
    }

    private void giveChange(int changeToGive) {

            while(changeToGive >= 25 && coinStock.get("quarter") > 0) {
                this.coinReturn.add("quarter");
                coinStock.put("quarter", coinStock.get("quarter")-1);
                changeToGive -= 25;
            }

            while (changeToGive >= 10 && coinStock.get("dime") > 0) {
                this.coinReturn.add("dime");
                coinStock.put("dime", coinStock.get("dime")-1);
                changeToGive -= 10;
            }

            while (changeToGive >= 5 && coinStock.get("nickle") > 0) {
                this.coinReturn.add("nickle");
                coinStock.put("nickle", coinStock.get("nickle")-1);
                changeToGive -= 5;
            }

            if(changeToGive > 0) {
                coinReturn = null;
                this.choosenProduct = null;
                returnAllMoneyButton();
            }
        }

    public void getProductFromFile(String productFilename) {
        Map<Integer, Product> products = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(productFilename))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] item = line.split(", ");
                products.put(Integer.valueOf(item[1]), new Product(item[0], Double.valueOf(item[2]), Integer.valueOf(item[3])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.products = products;
    }

    public double getPrice(int productPlace) {
        return products.get(Integer.valueOf(productPlace)).getPrice();
    }

    public int getQuantity(int productPlace) {
        return products.get(Integer.valueOf(productPlace)).getQuantity();
    }

    public void chooseProductFromPlace(int i) {
        this.choosenProduct = Integer.valueOf(i);
    }

    public String returnAllMoneyButton() {
        while (currentAmount > 0) {
            if (currentAmount >= 25) {
                this.coinReturn.add("quarter");
                currentAmount -= 25;

            } else if (currentAmount >= 10) {
                this.coinReturn.add("dime");
                currentAmount -= 10;

            } else if (currentAmount >= 5) {
                this.coinReturn.add("nickle");
                currentAmount -= 5;
            }
        }
        this.currentAmount = 0;
        return display();
    }

    public boolean isNeededExactChange() {
        for(Map.Entry<String, Integer> entry : coinStock.entrySet()) {
            if(entry.getValue() > 0) {
                return false;
            }
        }
        return true;
    }
}
