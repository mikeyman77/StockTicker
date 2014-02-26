package com.stockticker;


public abstract class StockQuote {

    private final Stock stock;
    
    
    protected StockQuote(Stock stock) {
        this.stock = stock;
    }
    
    // Getter methods
    public abstract String getName();
    public abstract String getSymbol();
    public abstract String getDate();
    public abstract String getTime();
    public abstract String getPrice();
    public abstract String getChange();
    public abstract String getChangePercent();
    public abstract String getLow();
    public abstract String getHigh();
    public abstract String getVolume();
    public abstract String getPrevClose();
    public abstract String getOpen();
    public abstract String getBid();
    public abstract String getAsk();
    public abstract String getYearLow();
    public abstract String getYearHigh();
    public abstract String getAvgVolume();
    public abstract String getMarketCap();
    public abstract String getPE();
    public abstract String getEPS();
    
    // Setter methods
    public abstract void setName(String name);
    public abstract void setSymbol(String symbol);
    public abstract void setDate(String date);
    public abstract void setTime(String time);
    public abstract void setPrice(String price);
    public abstract void setChange(String change);
    public abstract void setChangePercent(String changeInPercent);
    public abstract void setLow(String low);
    public abstract void setHigh(String high);
    public abstract void setVolume(String volume);
    public abstract void setPrevClose(String prevClose);
    public abstract void setOpen(String open);
    public abstract void setBid(String bid);
    public abstract void setAsk(String ask);
    public abstract void setYearLow(String yearLow);
    public abstract void setYearHigh(String yearHigh);
    public abstract void setAvgVolume(String avgVolume);
    public abstract void setMarketCap(String marketCap);
    public abstract void setPE(String pe);
    public abstract void setEPS(String eps);
    
}
