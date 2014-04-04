
package com.stockticker;

/**
 * Defines the interface for the stock symbol history class.
 * 
 * @author Michael Grissom
 */
public interface StockHistory {
    
    /**
     * Retrieves the stock symbol.
     * 
     * @return the stock symbol
     */
    public String getSymbol();
    
    /**
     * Retrieves the stock date.
     *
     * @return the stock date
     */
    public String getDate();
    
    /**
     * Retrieves the stock open price. 
     *
     * @return the stock open price
     */
    public String getOpen();
    
    /**
     * Retrieves the stock high price.
     *
     * @return the stock high price
     */
    public String getHigh();
    
    /**
     * Retrieves the stock low price.
     *
     * @return the stock low price
     */
    public String getLow();
    
    /**
     * Retrieves the stock close price.
     *
     * @return the stock close price
     */
    public String getClose();
    
    /**
     * Retrieves the stock volume.
     *
     * @return the stock volume
     */
    public String getVolume();
    
    /**
     * Retrieves the stock adjust close price.
     *
     * @return the stock adjust close price
     */
    public String getAdjClose();
    
    /**
     * Sets the stock symbol.
     *
     * @param symbol the stock symbol
     */
    public void setSymbol(String symbol);
    
    /**
     * Sets the stock date.
     *
     * @param date the stock date
     */
    public void setDate(String date);
    
    /**
     * Sets the stock open price.
     *
     * @param open the stock open price
     */
    public void setOpen(String open);
    
    /**
     * Sets the stock high price.
     *
     * @param high the stock high price
     */
    public void setHigh(String high);
    
    /**
     * Sets the stock low price.
     *
     * @param low the stock low price
     */
    public void setLow(String low);
    
    /**
     * Sets the stock close price.
     *
     * @param close the stock close price
     */
    public void setClose(String close);
    
    /**
     * Sets the stock volume.
     *
     * @param volume the stock volume
     */
    public void setVolume(String volume);
    
    /**
     * Sets the stock adjust close price.
     *
     * @param adjClose the stock adjust close price
     */
    public void setAdjClose(String adjClose);
    
}
