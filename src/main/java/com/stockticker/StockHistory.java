
package com.stockticker;

/**
 * This is the interface for the stock symbol history class.
 * 
 * @author Michael Grissom
 */
public interface StockHistory {
    
    /**
     * Returns the stock symbol.
     */
    public String getSymbol();
    
    /**
     * Returns the stock date.
     */
    public String getDate();
    
    /**
     * Returns the stock open price. 
     */
    public String getOpen();
    
    /**
     * Returns the stock high price.
     */
    public String getHigh();
    
    /**
     * Returns the stock low price.
     */
    public String getLow();
    
    /**
     * Returns the stock close price.
     */
    public String getClose();
    
    /**
     * Returns the stock volume.
     */
    public String getVolume();
    
    /**
     * Returns the stock adjust close price.
     */
    public String getAdjClose();
    
    /**
     * Set the stock symbol.
     */
    public void setSymbol(String symbol);
    
    /**
     * Set the stock date.
     */
    public void setDate(String date);
    
    /**
     * Set the stock open price.
     */
    public void setOpen(String open);
    
    /**
     * Set the stock high price.
     */
    public void setHigh(String high);
    
    /**
     * Set the stock low price.
     */
    public void setLow(String low);
    
    /**
     * Set the stock close price.
     */
    public void setClose(String close);
    
    /**
     * Set the stock volume.
     */
    public void setVolume(String volume);
    
    /**
     * Set the stock adjust close price.
     */
    public void setAdjClose(String adjClose);
    
}
