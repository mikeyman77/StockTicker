
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
    
    /**
     * This method returns a URL based on the list of stock symbols provided.
     *
     * @param symbols a list of stock symbols
     * @return URL
     * @throws com.stockticker.logic.BusinessLogicException
     */
    public URL getURL(List<String> symbols) throws BusinessLogicException;
    
    /**
     * This method returns a list of StockQuote based on the JSON input stream
     * provided.
     *
     * @param url URL used to get the stock quote data
     * @return a list of StockQuote objects
     * @throws com.stockticker.logic.BusinessLogicException
     */
    public List<StockQuote> getStockQuotes(URL url) throws BusinessLogicException;
    
}
