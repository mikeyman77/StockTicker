
package com.stockticker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Defines the data and JSON properties for the Yahoo stock quote.
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
     * Retrieves the stock quote name.
     *
     * @return the stock quote name
     */
    @Override
    public String getName() {
        return name;
    }
    
    /**
     * Retrieves the stock quote symbol.
     *
     * @return the stock quote symbol
     */
    @Override
    public String getSymbol() {
        return symbol;
    }
    
    /**
     * Retrieves the stock quote date.
     *
     * @return the stock quote date
     */
    @Override
    public String getDate() {
        return date;
    }
    
    /**
     * Retrieves the stock quote time.
     *
     * @return the stock quote time
     */
    @Override
    public String getTime() {
        return time;
    }
    
    /**
     * Retrieves the stock quote price.
     *
     * @return the stock quote price
     */
    @Override
    public String getPrice() {
        return price;
    }
    
    /**
     * Retrieves the stock quote change in price.
     *
     * @return the stock quote change in price
     */
    @Override
    public String getChange() {
        return change;
    }
    
    /**
     * Retrieves the stock quote change in percentage.
     *
     * @return the stock quote change in percentage
     */
    @Override
    public String getChangePercent() {
        return changeInPercent;
    }
    
    /**
     * Retrieves the stock quote low price.
     *
     * @return the stock quote low price
     */
    @Override
    public String getLow() {
        return low;
    }
    
    /**
     * Retrieves the stock quote high price.
     *
     * @return the stock quote high price
     */
    @Override
    public String getHigh() {
        return high;
    }
    
    /**
     * Retrieves the stock quote volume.
     *
     * @return the stock quote volume
     */
    @Override
    public String getVolume() {
        return volume;
    }
    
    /**
     * Retrieves the stock quote previous closing price.
     *
     * @return the stock quote previous closing price
     */
    @Override
    public String getPrevClose() {
        return prevClose;
    }
    
    /**
     * Retrieves the stock quote opening price.
     *
     * @return the stock quote opening price
     */
    @Override
    public String getOpen() {
        return open;
    }
    
    /**
     * Retrieves the stock quote bid price.
     *
     * @return the stock quote bid price
     */
    @Override
    public String getBid() {
        return bid;
    }
    
    /**
     * Retrieves the stock quote asking price.
     *
     * @return the stock quote asking price
     */
    @Override
    public String getAsk() {
        return ask;
    }
    
    /**
     * Retrieves the stock quote year low price.
     *
     * @return the stock quote year low price
     */
    @Override
    public String getYearLow() {
        return yearLow;
    }
    
    /**
     * Retrieves the stock quote year high price.
     *
     * @return the stock quote year high price
     */
    @Override
    public String getYearHigh() {
        return yearHigh;
    }
    
    /**
     * Retrieves the stock quote average volume.
     *
     * @return the stock quote average volume
     */
    @Override
    public String getAvgVolume() {
        return avgVolume;
    }
    
    /**
     * Retrieves the stock quote market cap.
     *
     * @return the stock quote market cap
     */
    @Override
    public String getMarketCap() {
        return marketCap;
    }
    
    /**
     * Retrieves the stock quote P/E.
     *
     * @return the stock quote P/E
     */
    @Override
    public String getPE() {
        return pe;
    }
    
    /**
     * Retrieves the stock quote EPS.
     *
     * @return the stock quote EPS
     */
    @Override
    public String getEPS() {
        return eps;
    }
    
    /**
     * Sets the stock quote name.
     *
     * @param name the stock quote name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Sets the stock quote symbol.
     *
     * @param symbol the stock quote symbol
     */
    @Override
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
    /**
     * Sets the stock quote date.
     *
     * @param date the stock quote date
     */
    @Override
    public void setDate(String date) {
        this.date = date;
    }
    
    /**
     * Sets the stock quote time.
     *
     * @param time the stock quote time
     */
    @Override
    public void setTime(String time) {
        this.time = time;
    }
    
    /**
     * Sets the stock quote price.
     *
     * @param price the stock quote price
     */
    @Override
    public void setPrice(String price) {
        this.price = price;
    }
    
    /**
     * Sets the stock quote change in price.
     *
     * @param change the stock quote change
     */
    @Override
    public void setChange(String change) {
        this.change = change;
    }
    
    /**
     * Sets the stock quote change in percentage.
     *
     * @param changeInPercent the stock quote change in percentage
     */
    @Override
    public void setChangePercent(String changeInPercent) {
        this.changeInPercent = changeInPercent;
    }
    
    /**
     * Sets the stock quote low price.
     *
     * @param low the stock quote low price
     */
    @Override
    public void setLow(String low) {
        this.low = low;
    }
    
    /**
     * Sets the stock quote high price.
     *
     * @param high the stock quote high price
     */
    @Override
    public void setHigh(String high) {
        this.high = high;
    }
    
    /**
     * Sets the stock quote volume.
     *
     * @param volume the stock quote volume
     */
    @Override
    public void setVolume(String volume) {
        this.volume = volume;
    }
    
    /**
     * Sets the stock quote previous closing price.
     *
     * @param prevClose the stock quote previous closing price
     */
    @Override
    public void setPrevClose(String prevClose) {
        this.prevClose = prevClose;
    }
    
    /**
     * Sets the stock quote opening price.
     *
     * @param open the stock quote opening price
     */
    @Override
    public void setOpen(String open) {
        this.open = open;
    }
    
    /**
     * Sets the stock quote bid price.
     *
     * @param bid the stock quote bid price
     */
    @Override
    public void setBid(String bid) {
        this.bid = bid;
    }
    
    /**
     * Sets the stock quote asking price.
     *
     * @param ask the stock quote asking price
     */
    @Override
    public void setAsk(String ask) {
        this.ask = ask;
    }
    
    /**
     * Sets the stock quote year low price.
     *
     * @param yearLow the stock quote year low price
     */
    @Override
    public void setYearLow(String yearLow) {
        this.yearLow = yearLow;
    }
    
    /**
     * Sets the stock quote year high price.
     *
     * @param yearHigh the stock quote year high price
     */
    @Override
    public void setYearHigh(String yearHigh) {
        this.yearHigh = yearHigh;
    }
    
    /**
     * Sets the stock quote average volume.
     *
     * @param avgVolume the stock quote average volume
     */
    @Override
    public void setAvgVolume(String avgVolume) {
        this.avgVolume = avgVolume;
    }
    
    /**
     * Sets the stock quote market cap.
     *
     * @param marketCap the stock quote market cap
     */
    @Override
    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }
    
    /**
     * Sets the stock quote P/E.
     *
     * @param pe the stock quote P/E
     */
    @Override
    public void setPE(String pe) {
        this.pe = pe;
    }
    
    /**
     * Sets the stock quote EPS.
     *
     * @param eps the stock quote EPS
     */
    @Override
    public void setEPS(String eps) {
        this.eps = eps;
    }

}
