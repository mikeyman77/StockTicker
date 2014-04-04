
package com.stockticker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is a data class for Yahoo stock history.
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
     * Returns the stock symbol.
     */
    @Override
    public String getSymbol() {
        return stockSymbol;
    }
    
    /**
     * Returns the stock date.
     */
    @Override
    public String getDate() {
        return stockDate;
    }
    
    /**
     * Returns the stock open price.
     */
    @Override
    public String getOpen() {
        return stockOpen;
    }
    
    /**
     * Returns the stock high price.
     */
    @Override
    public String getHigh() {
        return stockHigh;
    }
    
    /**
     * Returns the stock low price.
     */
    @Override
    public String getLow() {
        return stockLow;
    }
    
    /**
     * Returns the stock close price.
     */
    @Override
    public String getClose() {
        return stockClose;
    }
    
    /**
     * Returns the stock volume.
     */
    @Override
    public String getVolume() {
        return stockVolume;
    }
    
    /**
     * Returns the stock adjust close.
     */
    @Override
    public String getAdjClose() {
        return stockAdjClose;
    }
    
    /**
     * Set the stock symbol.
     */
    @Override
    public void setSymbol(String symbol) {
        this.stockSymbol = symbol;
    }
    
    /**
     * Set the stock date.
     */
    @Override
    public void setDate(String date) {
        this.stockDate = date;
    }
    
    /**
     * Set the stock open price.
     */
    @Override
    public void setOpen(String open) {
        this.stockOpen = open;
    }
    
    /**
     * Set the stock high price.
     */
    @Override
    public void setHigh(String high) {
        this.stockHigh = high;
    }
    
    /**
     * Set the stock low price.
     */
    @Override
    public void setLow(String low) {
        this.stockLow = low;
    }
    
    /**
     * Set the stock close price.
     */
    @Override
    public void setClose(String close) {
        this.stockClose = close;
    }
    
    /**
     * Set the stock volume.
     */
    @Override
    public void setVolume(String volume) {
        this.stockVolume = volume;
    }
    
    /**
     * Set the stock adjust price.
     */
    @Override
    public void setAdjClose(String adjClose) {
        this.stockAdjClose = adjClose;
    }
    
}
