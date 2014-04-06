
package com.stockticker.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockticker.StockQuote;
import com.stockticker.SymbolMap;
import com.stockticker.YahooStockQuote;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

/**
 * This class gets Yahoo stock quotes from the web.
 * 
 * @author Michael Grissom
 */
public enum YahooStockQuoteService implements StockQuoteService {

    /**
     * Instance of Yahoo stock quote service.
     */
    INSTANCE;
    
    private static final Logger logger = 
            LogManager.getLogger(YahooStockQuoteService.class.getName());
    
    private YahooStockQuoteService() {
        PropertyConfigurator.configure("./config/log4j.properties");
    }
    
    /**
     * This method returns a URL based on the list of stock symbols provided.
     *
     * @param symbols a list of stock symbols
     * @return URL
     * @throws com.stockticker.logic.BusinessLogicException
     */
    @Override
    public URL getURL(List<String> symbols) throws BusinessLogicException {
        
        StringBuilder symbolString = new StringBuilder();
        URL queryUrl = null;
        String yahooQueryUrl;
        String yahooQueryStr;
        String queryFields;
        
        if (symbols == null || symbols.isEmpty()) {
            return queryUrl;
        }
        
        // format symbol string for query
        for (int i = 0; i < symbols.size(); i++) {
            String stockSymbol = symbols.get(i);
            
            if (SymbolMap.isValidSymbol(stockSymbol)) {

                if (!symbolString.toString().isEmpty()) {
                    symbolString.append(",");
                }
                
                symbolString.append(String.format("\"%s\"", stockSymbol));
            }
        }
        
        queryFields = "Name,Symbol,LastTradeDate,LastTradeTime,LastTradePriceOnly,"
                + "Change,ChangeinPercent,DaysLow,DaysHigh,Volume,PreviousClose,"
                + "Open,Bid,Ask,YearLow,YearHigh,AverageDailyVolume,"
                + "MarketCapitalization,PERatio,EarningsShare";
        yahooQueryUrl = "http://query.yahooapis.com/v1/public/yql?q=";
        yahooQueryStr = String.format("SELECT %s from yahoo.finance.quotes where "
                + "symbol in (%s)&format=json"
                + "&env=store://datatables.org/alltableswithkeys", 
                queryFields, symbolString).replaceAll(" ", "%20");
        
        try {
            queryUrl = new URL(yahooQueryUrl + yahooQueryStr);
        }
        catch (MalformedURLException ex) {
            logger.error("Unable to generate URL", ex);
            throw new BusinessLogicException("Error: Could not generate URL, check logs", ex);
        }
        
        return queryUrl;
    }
    
    /**
     * This method returns a list of StockQuote based on the JSON input stream
     * provided.
     *
     * @param url URL used to get the stock quote data
     * @return a list of StockQuote objects
     * @throws com.stockticker.logic.BusinessLogicException
     */
    @Override
    public List<StockQuote> getStockQuotes(URL url) {
        ObjectMapper mapper = new ObjectMapper();
        List<StockQuote> stockQuoteList = new ArrayList<>();
        
        int quoteCount = 0;
        JsonNode rootNode = null;
        JsonNode quoteNode = null;
        InputStream inputStream;
        
        if (url == null) {
            return stockQuoteList; // return empty list
        }
        
        // read the full json tree
        try {
            inputStream = url.openConnection().getInputStream();
            rootNode = mapper.readTree(inputStream);
        }
        catch (IOException ex) {
            logger.error("Unable to get stock quotes", ex);
            throw new BusinessLogicException("Error: Could not get stock quotes, check logs", ex);
        }
        
        // get the count of quotes returned
        quoteCount = rootNode.path("query").path("count").asInt();
        
        for (int i = 0; i < quoteCount; i++) {
            if (quoteCount > 1) {
                // parse an array of quotes
                quoteNode = rootNode.path("query").path("results").path("quote").path(i);
            }
            else {
                // parse a single quote
                quoteNode = rootNode.path("query").path("results").path("quote");
            }
            
            if (!quoteNode.isMissingNode()) {
                YahooStockQuote yahooStockQuote = mapper.convertValue(quoteNode, YahooStockQuote.class);
                stockQuoteList.add(yahooStockQuote);
            }
        }
        
        return stockQuoteList;
    }
}
