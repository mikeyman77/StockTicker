package com.stockticker.persistence;

import java.util.List;

public interface PersistenceService {
    public List<String> getWatchedStocks();
    public void setStockToWatch(String symbol);
    public void setStockToWatch(List<String> symbols);
}