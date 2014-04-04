
package com.stockticker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is a data class for Yahoo stock quotes.
 * 
 * @author Michael Grissom
 */
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
    
    /**
     * Returns the stock quote name.
     */
    @Override
    public String getName() {
        return name;
    }
    
    /**
     * Returns the stock quote symbol.
     */
    @Override
    public String getSymbol() {
        return symbol;
    }
    
    /**
     * Returns the stock quote date.
     */
    @Override
    public String getDate() {
        return date;
    }
    
    /**
     * Returns the stock quote time.
     */
    @Override
    public String getTime() {
        return time;
    }
    
    /**
     * Returns the stock quote price.
     */
    @Override
    public String getPrice() {
        return price;
    }
    
    /**
     * Returns the stock quote change in price.
     */
    @Override
    public String getChange() {
        return change;
    }
    
    /**
     * Returns the stock quote change in percentage.
     */
    @Override
    public String getChangePercent() {
        return changeInPercent;
    }
    
    /**
     * Returns the stock quote low price.
     */
    @Override
    public String getLow() {
        return low;
    }
    
    /**
     * Returns the stock quote high price.
     */
    @Override
    public String getHigh() {
        return high;
    }
    
    /**
     * Returns the stock quote volume.
     */
    @Override
    public String getVolume() {
        return volume;
    }
    
    /**
     * Returns the stock quote previous closing price.
     */
    @Override
    public String getPrevClose() {
        return prevClose;
    }
    
    /**
     * Returns the stock quote opening price.
     */
    @Override
    public String getOpen() {
        return open;
    }
    
    /**
     * Returns the stock quote bid price.
     */
    @Override
    public String getBid() {
        return bid;
    }
    
    /**
     * Returns the stock quote asking price.
     */
    @Override
    public String getAsk() {
        return ask;
    }
    
    /**
     * Returns the stock quote year low price.
     */
    @Override
    public String getYearLow() {
        return yearLow;
    }
    
    /**
     * Returns the stock quote year high price.
     */
    @Override
    public String getYearHigh() {
        return yearHigh;
    }
    
    /**
     * Returns the stock quote average volume.
     */
    @Override
    public String getAvgVolume() {
        return avgVolume;
    }
    
    /**
     * Returns the stock quote market cap.
     */
    @Override
    public String getMarketCap() {
        return marketCap;
    }
    
    /**
     * Returns the stock quote P/E.
     */
    @Override
    public String getPE() {
        return pe;
    }
    
    /**
     * Returns the stock quote EPS.
     */
    @Override
    public String getEPS() {
        return eps;
    }
    
    /**
     * Set the stock quote name.
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Set the stock quote symbol.
     */
    @Override
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
    /**
     * Set the stock quote date.
     */
    @Override
    public void setDate(String date) {
        this.date = date;
    }
    
    /**
     * Set the stock quote time.
     */
    @Override
    public void setTime(String time) {
        this.time = time;
    }
    
    /**
     * Set the stock quote price.
     */
    @Override
    public void setPrice(String price) {
        this.price = price;
    }
    
    /**
     * Set the stock quote change in price.
     */
    @Override
    public void setChange(String change) {
        this.change = change;
    }
    
    /**
     * Set the stock quote change in percentage.
     */
    @Override
    public void setChangePercent(String changeInPercent) {
        this.changeInPercent = changeInPercent;
    }
    
    /**
     * Set the stock quote low price.
     */
    @Override
    public void setLow(String low) {
        this.low = low;
    }
    
    /**
     * Set the stock quote high price.
     */
    @Override
    public void setHigh(String high) {
        this.high = high;
    }
    
    /**
     * Set the stock quote volume.
     */
    @Override
    public void setVolume(String volume) {
        this.volume = volume;
    }
    
    /**
     * Set the stock quote previous closing price.
     */
    @Override
    public void setPrevClose(String prevClose) {
        this.prevClose = prevClose;
    }
    
    /**
     * Set the stock quote opening price.
     */
    @Override
    public void setOpen(String open) {
        this.open = open;
    }
    
    /**
     * Set the stock quote bid price.
     */
    @Override
    public void setBid(String bid) {
        this.bid = bid;
    }
    
    /**
     * Set the stock quote asking price.
     */
    @Override
    public void setAsk(String ask) {
        this.ask = ask;
    }
    
    /**
     * Set the stock quote year low price.
     */
    @Override
    public void setYearLow(String yearLow) {
        this.yearLow = yearLow;
    }
    
    /**
     * Set the stock quote year high price.
     */
    @Override
    public void setYearHigh(String yearHigh) {
        this.yearHigh = yearHigh;
    }
    
    /**
     * Set the stock quote average volume.
     */
    @Override
    public void setAvgVolume(String avgVolume) {
        this.avgVolume = avgVolume;
    }
    
    /**
     * Set the stock quote market cap.
     */
    @Override
    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }
    
    /**
     * Set the stock quote P/E.
     */
    @Override
    public void setPE(String pe) {
        this.pe = pe;
    }
    
    /**
     * Set the stock quote EPS.
     */
    @Override
    public void setEPS(String eps) {
        this.eps = eps;
    }

}
