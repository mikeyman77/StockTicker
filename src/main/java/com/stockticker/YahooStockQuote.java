
package com.stockticker;

import com.fasterxml.jackson.annotation.JsonProperty;


public class YahooStockQuote extends StockQuote {
    
    private String name;
    private String symbol;
    private String date;
    private String time;
    private String price;
    private String change;
    private String changeInPercent;
    private String low;
    private String high;
    private String volume;
    private String prevClose;
    private String open;
    private String bid;
    private String ask;
    private String yearLow;
    private String yearHigh;
    private String avgVolume;
    private String marketCap;
    private String pe;
    private String eps;
    
    
    public YahooStockQuote(Stock stock) {
        super(stock);
    }

    @Override
    @JsonProperty("Name")
    public String getName() {
        return name;
    }

    @Override
    @JsonProperty("Symbol")
    public String getSymbol() {
        return symbol;
    }

    @Override
    @JsonProperty("LastTradeDate")
    public String getDate() {
        return date;
    }

    @Override
    @JsonProperty("LastTradeTime")
    public String getTime() {
        return time;
    }

    @Override
    @JsonProperty("LastTradePriceOnly")
    public String getPrice() {
        return price;
    }

    @Override
    @JsonProperty("Change")
    public String getChange() {
        return change;
    }

    @Override
    @JsonProperty("ChangeinPercent")
    public String getChangePercent() {
        return changeInPercent;
    }

    @Override
    @JsonProperty("DaysLow")
    public String getLow() {
        return low;
    }

    @Override
    @JsonProperty("DaysHigh")
    public String getHigh() {
        return high;
    }

    @Override
    @JsonProperty("Volume")
    public String getVolume() {
        return volume;
    }

    @Override
    @JsonProperty("PreviousClose")
    public String getPrevClose() {
        return prevClose;
    }

    @Override
    @JsonProperty("Open")
    public String getOpen() {
        return open;
    }

    @Override
    @JsonProperty("Bid")
    public String getBid() {
        return bid;
    }

    @Override
    @JsonProperty("Ask")
    public String getAsk() {
        return ask;
    }

    @Override
    @JsonProperty("YearLow")
    public String getYearLow() {
        return yearLow;
    }

    @Override
    @JsonProperty("YearHigh")
    public String getYearHigh() {
        return yearHigh;
    }

    @Override
    @JsonProperty("AverageDailyVolume")
    public String getAvgVolume() {
        return avgVolume;
    }

    @Override
    @JsonProperty("MarketCapitalization")
    public String getMarketCap() {
        return marketCap;
    }

    @Override
    @JsonProperty("PERatio")
    public String getPE() {
        return pe;
    }

    @Override
    @JsonProperty("EarningsShare")
    public String getEPS() {
        return eps;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public void setChange(String change) {
        this.change = change;
    }

    @Override
    public void setChangePercent(String changeInPercent) {
        this.changeInPercent = changeInPercent;
    }

    @Override
    public void setLow(String low) {
        this.low = low;
    }

    @Override
    public void setHigh(String high) {
        this.high = high;
    }

    @Override
    public void setVolume(String volume) {
        this.volume = volume;
    }

    @Override
    public void setPrevClose(String prevClose) {
        this.prevClose = prevClose;
    }

    @Override
    public void setOpen(String open) {
        this.open = open;
    }

    @Override
    public void setBid(String bid) {
        this.bid = bid;
    }

    @Override
    public void setAsk(String ask) {
        this.ask = ask;
    }

    @Override
    public void setYearLow(String yearLow) {
        this.yearLow = yearLow;
    }

    @Override
    public void setYearHigh(String yearHigh) {
        this.yearHigh = yearHigh;
    }

    @Override
    public void setAvgVolume(String avgVolume) {
        this.avgVolume = avgVolume;
    }

    @Override
    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    @Override
    public void setPE(String pe) {
        this.pe = pe;
    }

    @Override
    public void setEPS(String eps) {
        this.eps = eps;
    }
    
}
