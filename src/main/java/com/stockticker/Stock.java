package com.stockticker;

import java.util.Date;

public class Stock {

    private String symbol;
    private Date date;

    public Stock(String symbol) {
        this.symbol = symbol;
    }

    public Stock(String symbol, Date date) {
        this.date = date;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public Date getDate() {
        return this.date;
    }

}