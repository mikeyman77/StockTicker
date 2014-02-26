
package com.stockticker.logic;

import com.stockticker.StockQuote;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;


public interface StockQuoteService {
    
    public boolean constructURL(List<String> symbols) throws MalformedURLException;
    public List<StockQuote> getStocks() throws IOException;
    
}
