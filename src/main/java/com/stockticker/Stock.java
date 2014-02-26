package com.stockticker;

public class Stock {

    private String symbol;
    private String name;

    public Stock(String symbol) {
        this.symbol = symbol;
    }

    public Stock(String symbol, String name) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public String getName() {
        return this.name;
    }

}