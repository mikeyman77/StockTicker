
package com.stockticker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown=true)
public class YahooStockQuote implements StockQuote {
    
    @JsonProperty("Name")                   private String name;
    @JsonProperty("Symbol")                 private String symbol;
    @JsonProperty("LastTradeDate")          private String date;
    @JsonProperty("LastTradeTime")          private String time;
    @JsonProperty("LastTradePriceOnly")     private String price;
    @JsonProperty("Change")                 private String change;
    @JsonProperty("ChangeinPercent")        private String changeInPercent;
    @JsonProperty("DaysLow")                private String low;
    @JsonProperty("DaysHigh")               private String high;
    @JsonProperty("Volume")                 private String volume;
    @JsonProperty("PreviousClose")          private String prevClose;
    @JsonProperty("Open")                   private String open;
    @JsonProperty("Bid")                    private String bid;
    @JsonProperty("Ask")                    private String ask;
    @JsonProperty("YearLow")                private String yearLow;
    @JsonProperty("YearHigh")               private String yearHigh;
    @JsonProperty("AverageDailyVolume")     private String avgVolume;
    @JsonProperty("MarketCapitalization")   private String marketCap;
    @JsonProperty("PERatio")                private String pe;
    @JsonProperty("EarningsShare")          private String eps;
    
    
    public YahooStockQuote() {
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public String getTime() {
        return time;
    }

    @Override
    public String getPrice() {
        return price;
    }

    @Override
    public String getChange() {
        return change;
    }

    @Override
    public String getChangePercent() {
        return changeInPercent;
    }

    @Override
    public String getLow() {
        return low;
    }

    @Override
    public String getHigh() {
        return high;
    }

    @Override
    public String getVolume() {
        return volume;
    }

    @Override
    public String getPrevClose() {
        return prevClose;
    }

    @Override
    public String getOpen() {
        return open;
    }

    @Override
    public String getBid() {
        return bid;
    }

    @Override
    public String getAsk() {
        return ask;
    }

    @Override
    public String getYearLow() {
        return yearLow;
    }

    @Override
    public String getYearHigh() {
        return yearHigh;
    }

    @Override
    public String getAvgVolume() {
        return avgVolume;
    }

    @Override
    public String getMarketCap() {
        return marketCap;
    }

    @Override
    public String getPE() {
        return pe;
    }

    @Override
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
