
package com.stockticker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Defines the data and JSON properties for the Yahoo stock history.
 * 
 * @author Michael Grissom
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class YahooStockHistory implements StockHistory {
    
    @JsonProperty("Symbol")     private String stockSymbol;
    @JsonProperty("Date")       private String stockDate;
    @JsonProperty("Open")       private String stockOpen;
    @JsonProperty("High")       private String stockHigh;
    @JsonProperty("Low")        private String stockLow;
    @JsonProperty("Close")      private String stockClose;
    @JsonProperty("Volume")     private String stockVolume;
    @JsonProperty("Adj_Close")  private String stockAdjClose;
    
    /**
     * Retrieves the stock symbol.
     *
     * @return the stock symbol
     */
    @Override
    public String getSymbol() {
        return stockSymbol;
    }
    
    /**
     * Retrieves the stock date.
     *
     * @return the stock date
     */
    @Override
    public String getDate() {
        return stockDate;
    }
    
    /**
     * Retrieves the stock open price.
     *
     * @return the stock open price
     */
    @Override
    public String getOpen() {
        return stockOpen;
    }
    
    /**
     * Retrieves the stock high price.
     *
     * @return the stock high price
     */
    @Override
    public String getHigh() {
        return stockHigh;
    }
    
    /**
     * Retrieves the stock low price.
     *
     * @return the stock low price
     */
    @Override
    public String getLow() {
        return stockLow;
    }
    
    /**
     * Retrieves the stock close price.
     *
     * @return the stock close price
     */
    @Override
    public String getClose() {
        return stockClose;
    }
    
    /**
     * Retrieves the stock volume.
     *
     * @return the stock volume
     */
    @Override
    public String getVolume() {
        return stockVolume;
    }
    
    /**
     * Retrieves the stock adjust close.
     *
     * @return the stock adjust close
     */
    @Override
    public String getAdjClose() {
        return stockAdjClose;
    }
    
    /**
     * Sets the stock symbol.
     *
     * @param symbol the stock symbol
     */
    @Override
    public void setSymbol(String symbol) {
        this.stockSymbol = symbol;
    }
    
    /**
     * Sets the stock date.
     *
     * @param date the stock date
     */
    @Override
    public void setDate(String date) {
        this.stockDate = date;
    }
    
    /**
     * Sets the stock open price.
     *
     * @param open the stock open price
     */
    @Override
    public void setOpen(String open) {
        this.stockOpen = open;
    }
    
    /**
     * Sets the stock high price.
     *
     * @param high the stock high price
     */
    @Override
    public void setHigh(String high) {
        this.stockHigh = high;
    }
    
    /**
     * Sets the stock low price.
     *
     * @param low the stock low price
     */
    @Override
    public void setLow(String low) {
        this.stockLow = low;
    }
    
    /**
     * Sets the stock close price.
     *
     * @param close the stock close price
     */
    @Override
    public void setClose(String close) {
        this.stockClose = close;
    }
    
    /**
     * Sets the stock volume.
     *
     * @param volume the stock volume
     */
    @Override
    public void setVolume(String volume) {
        this.stockVolume = volume;
    }
    
    /**
     * Sets the stock adjusted close price.
     *
     * @param adjClose the stock adjusted close price
     */
    @Override
    public void setAdjClose(String adjClose) {
        this.stockAdjClose = adjClose;
    }
    
}
