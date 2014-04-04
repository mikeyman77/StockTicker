package com.stockticker;

/**
 * Defines the interface for a StockQuote
 * 
 * @author Michael Grissom
 */
public interface StockQuote {
    
    /**
     * Retrieves the stock quote name.
     *
     * @return the stock quote name
     */
    public String getName();
    
    /**
     * Retrieves the stock quote symbol.
     *
     * @return the stock quote symbol
     */
    public String getSymbol();
    
    /**
     * Retrieves the stock quote date.
     *
     * @return the stock quote date
     */
    public String getDate();
    
    /**
     * Retrieves the stock quote time.
     *
     * @return the stock quote time
     */
    public String getTime();
    
    /**
     * Retrieves the stock quote price.
     *
     * @return the stock quote price
     */
    public String getPrice();
    
    /**
     * Retrieves the stock quote change in price.
     *
     * @return the stock quote change in price
     */
    public String getChange();
    
    /**
     * Retrieves the stock quote change in percentage.
     *
     * @return the stock quote change in percentage
     */
    public String getChangePercent();
    
    /**
     * Retrieves the stock quote low price.
     *
     * @return the stock quote low price
     */
    public String getLow();
    
    /**
     * Retrieves the stock quote high price.
     *
     * @return the stock quote high price
     */
    public String getHigh();
    
    /**
     * Retrieves the stock quote volume.
     *
     * @return the stock quote volume
     */
    public String getVolume();
    
    /**
     * Retrieves the stock quote previous close price.
     *
     * @return the stock quote previous close price
     */
    public String getPrevClose();
    
    /**
     * Retrieves the stock quote opening price.
     *
     * @return the stock quote opening price
     */
    public String getOpen();
    
    /**
     * Retrieves the stock quote bid price.
     *
     * @return the stock quote bid price
     */
    public String getBid();
    
    /**
     * Retrieves the stock quote asking price.
     *
     * @return the stock quote asking price
     */
    public String getAsk();
    
    /**
     * Retrieves the stock quote year low.
     *
     * @return the stock quote year low
     */
    public String getYearLow();
    
    /**
     * Retrieves the stock quote year high.
     *
     * @return the stock quote year high
     */
    public String getYearHigh();
    
    /**
     * Retrieves the stock quote average volume.
     *
     * @return the stock quote average volume
     */
    public String getAvgVolume();
    
    /**
     * Retrieves the stock quote market cap.
     *
     * @return the stock quote market cap
     */
    public String getMarketCap();
    
    /**
     * Retrieves the stock quote P/E.
     *
     * @return the stock quote P/E
     */
    public String getPE();
    
    /**
     * Retrieves the stock quote EPS.
     *
     * @return the stock quote EPS
     */
    public String getEPS();

    /**
     * Sets the stock quote name.
     *
     * @param name the stock quote name
     */
    public void setName(String name);

    /**
     * Sets the stock quote symbol.
     *
     * @param symbol the stock quote symbol
     */
    public void setSymbol(String symbol);

    /**
     * Sets the stock quote date.
     *
     * @param date the stock quote date
     */
    public void setDate(String date);

    /**
     * Sets the stock quote time.
     *
     * @param time the stock quote time
     */
    public void setTime(String time);

    /**
     * Sets the stock quote price.
     *
     * @param price the stock quote price
     */
    public void setPrice(String price);

    /**
     * Sets the stock quote change in price.
     *
     * @param change the stock quote change in price
     */
    public void setChange(String change);

    /**
     * Sets the stock quote change in percentage.
     *
     * @param changeInPercent the stock quote change in percent
     */
    public void setChangePercent(String changeInPercent);

    /**
     * Sets the stock quote low price.
     *
     * @param low the stock quote low price
     */
    public void setLow(String low);

    /**
     * Sets the stock quote high price.
     *
     * @param high the stock quote high price
     */
    public void setHigh(String high);

    /**
     * Sets the stock quote volume.
     *
     * @param volume the stock quote volume
     */
    public void setVolume(String volume);

    /**
     * Sets the stock quote previous close.
     *
     * @param prevClose the stock quote previous close
     */
    public void setPrevClose(String prevClose);

    /**
     * Sets the stock quote opening price.
     *
     * @param open the stock quote opening price
     */
    public void setOpen(String open);

    /**
     * Sets the stock quote bid price.
     *
     * @param bid the stock quote bid price
     */
    public void setBid(String bid);

    /**
     * Sets the stock quote asking price.
     *
     * @param ask the stock quote asking price
     */
    public void setAsk(String ask);

    /**
     * Sets the stock quote year low price.
     *
     * @param yearLow the stock quote year low price
     */
    public void setYearLow(String yearLow);

    /**
     * Sets the stock quote year high price.
     *
     * @param yearHigh the stock quote year high price
     */
    public void setYearHigh(String yearHigh);

    /**
     * Sets the stock quote average volume.
     *
     * @param avgVolume the stock quote average volume
     */
    public void setAvgVolume(String avgVolume);

    /**
     * Sets the stock quote market cap.
     *
     * @param marketCap the stock quote market cap
     */
    public void setMarketCap(String marketCap);

    /**
     * Sets the stock quote P/E.
     *
     * @param pe the stock quote P/E
     */
    public void setPE(String pe);

    /**
     * Sets the stock quote EPS.
     *
     * @param eps the stock quote eps
     */
    public void setEPS(String eps);
    
}
