package com.stockticker;

/**
 * This interface provides the basics for a StockQuote
 * 
 * @author Michael Grissom
 */
public interface StockQuote {
    
    /**
     * Returns the stock quote name.
     */
    public String getName();
    
    /**
     * Returns the stock quote symbol.
     */
    public String getSymbol();
    
    /**
     * Returns the stock quote date.
     */
    public String getDate();
    
    /**
     * Returns the stock quote time.
     */
    public String getTime();
    
    /**
     * Returns the stock quote price.
     */
    public String getPrice();
    
    /**
     * Returns the stock quote change in price.
     */
    public String getChange();
    
    /**
     * Returns the stock quote change in percentage.
     */
    public String getChangePercent();
    
    /**
     * Returns the stock quote low price.
     */
    public String getLow();
    
    /**
     * Returns the stock quote high price.
     */
    public String getHigh();
    
    /**
     * Returns the stock quote volume.
     */
    public String getVolume();
    
    
    /**
     * Returns the stock quote previous close price.
     */
    public String getPrevClose();
    
    /**
     * Returns the stock quote opening price.
     */
    public String getOpen();
    
    /**
     * Returns the stock quote bid price.
     */
    public String getBid();
    
    /**
     * Returns the stock quote asking price.
     */
    public String getAsk();
    
    /**
     * Returns the stock quote year low.
     */
    public String getYearLow();
    
    /**
     * Returns the stock quote year high.
     */
    public String getYearHigh();
    
    /**
     * Returns the stock quote average volume.
     */
    public String getAvgVolume();
    
    /**
     * Returns the stock quote market cap.
     */
    public String getMarketCap();
    
    /**
     * Returns the stock quote P/E.
     */
    public String getPE();
    
    /**
     * Returns the stock quote EPS.
     */
    public String getEPS();

    /**
     * Set the stock quote name.
     */
    public void setName(String name);

    /**
     * Set the stock quote symbol.
     */
    public void setSymbol(String symbol);

    /**
     * Set the stock quote date.
     */
    public void setDate(String date);

    /**
     * Set the stock quote time.
     */
    public void setTime(String time);

    /**
     * Set the stock quote price.
     */
    public void setPrice(String price);

    /**
     * Set the stock quote change in price.
     */
    public void setChange(String change);

    /**
     * Set the stock quote change in percentage.
     */
    public void setChangePercent(String changeInPercent);

    /**
     * Set the stock quote low price.
     */
    public void setLow(String low);

    /**
     * Set the stock quote high price.
     */
    public void setHigh(String high);

    /**
     * Set the stock quote volume.
     */
    public void setVolume(String volume);

    /**
     * Set the stock quote previous close.
     */
    public void setPrevClose(String prevClose);

    /**
     * Set the stock quote opening price.
     */
    public void setOpen(String open);

    /**
     * Set the stock quote bid price.
     */
    public void setBid(String bid);

    /**
     * Set the stock quote asking price.
     */
    public void setAsk(String ask);

    /**
     * Set the stock quote year low price.
     */
    public void setYearLow(String yearLow);

    /**
     * Set the stock quote year high price.
     */
    public void setYearHigh(String yearHigh);

    /**
     * Set the stock quote average volume.
     */
    public void setAvgVolume(String avgVolume);

    /**
     * Set the stock quote market cap.
     */
    public void setMarketCap(String marketCap);

    /**
     * Set the stock quote P/E.
     */
    public void setPE(String pe);

    /**
     * Set the stock quote EPS.
     */
    public void setEPS(String eps);
    
}
