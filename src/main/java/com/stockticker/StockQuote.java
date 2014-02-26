package com.stockticker;

/**
 * This interface provides the basics for a StockQuote
 * @author Michael Grissom
 */
public interface StockQuote {
    
    // Getter methods
    public String getName();
    public String getSymbol();
    public String getDate();
    public String getTime();
    public String getPrice();
    public String getChange();
    public String getChangePercent();
    public String getLow();
    public String getHigh();
    public String getVolume();
    public String getPrevClose();
    public String getOpen();
    public String getBid();
    public String getAsk();
    public String getYearLow();
    public String getYearHigh();
    public String getAvgVolume();
    public String getMarketCap();
    public String getPE();
    public String getEPS();
    
    // Setter methods
    public void setName(String name);
    public void setSymbol(String symbol);
    public void setDate(String date);
    public void setTime(String time);
    public void setPrice(String price);
    public void setChange(String change);
    public void setChangePercent(String changeInPercent);
    public void setLow(String low);
    public void setHigh(String high);
    public void setVolume(String volume);
    public void setPrevClose(String prevClose);
    public void setOpen(String open);
    public void setBid(String bid);
    public void setAsk(String ask);
    public void setYearLow(String yearLow);
    public void setYearHigh(String yearHigh);
    public void setAvgVolume(String avgVolume);
    public void setMarketCap(String marketCap);
    public void setPE(String pe);
    public void setEPS(String eps);
    
}
