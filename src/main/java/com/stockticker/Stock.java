package com.stockticker;

/**
 * Stores stock related data.
 * 
 * @author Michael Grissom
 */
public class Stock {

    /**
     * Stock symbol
     */
    private String symbol;

    /**
     * Stock full name
     */
    private String name;

    /**
     * Constructs a stock data object
     *
     * @param symbol  the stock symbol
     */
    public Stock(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Constructs a stock data object.
     *
     * @param symbol  the stock symbol
     * @param name    the stock full name
     */
    public Stock(String symbol, String name) {
        this.symbol = symbol;
    }

    /**
     * Retrieves the stock symbol.
     *
     * @return the stock symbol
     */
    public String getSymbol() {
        return this.symbol;
    }

    /**
     * Retrieves the stock full name.
     *
     * @return the stock full name
     */
    public String getName() {
        return this.name;
    }

}