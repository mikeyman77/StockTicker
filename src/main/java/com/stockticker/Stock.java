package com.stockticker;

/**
 * This is a data class for stocks.
 * 
 * @author Michael Grissom
 */
public class Stock {

    private String symbol;
    private String name;

    /**
     * Constructor with symbol
     */
    public Stock(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Constructor with symbol and name.
     */
    public Stock(String symbol, String name) {
        this.symbol = symbol;
    }

    /**
     * Returns stock symbol.
     */
    public String getSymbol() {
        return this.symbol;
    }

    /**
     * Returns stock name.
     */
    public String getName() {
        return this.name;
    }

}