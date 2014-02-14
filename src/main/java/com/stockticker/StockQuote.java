package com.stockticker;

import java.util.Date;

public class StockQuote {

    private Stock stock;
    private Date time;
    private float price;
    private float change;
    private float changePercent;
    private float prevClose;
    private float open;
    private float bid;
    private float ask;
    private float oneYearTarget;
    private String daysRange;
    private String fiftyTwoWeekRange;
    private int volume;
    private long marketCap;
    private float pe;
    private float eps;
    private String dividendAndYield;

    public StockQuote(Stock stock) { this.stock = stock; }

}
