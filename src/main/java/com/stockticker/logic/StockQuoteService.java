
package com.stockticker.logic;

import com.stockticker.StockQuote;
import java.net.URL;
import java.util.List;

/**
 * This is the interface defines the functionality for stock quote services. It 
 * provides methods to generate the URL based on the service and the stock 
 * symbols, get input streams from files and from URLs and also to get a list 
 * of stock quotes.
 * 
 * @author Michael Grissom
 */
public interface StockQuoteService {
    
    public URL getURL(List<String> symbols) throws BusinessLogicException;
    public List<StockQuote> getStockQuotes(URL url) throws BusinessLogicException;
    
}
