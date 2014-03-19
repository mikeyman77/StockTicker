
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

/**
 * This class gets Yahoo stock quotes from the web.
 * 
 * @author Michael Grissom
 */
public enum YahooStockQuoteService implements StockQuoteService {
    INSTANCE;
    
    /**
     * This method returns a URL based on the list of stock symbols provided.
     *
     * @param symbols a list of stock symbols
     * @return URL
     */
    @Override
    public URL getURL(List<String> symbols) {
        
        StringBuilder symbolString = new StringBuilder();
        StringBuilder queryUrlStr = new StringBuilder();
        URL queryUrl = null;
        
        String yahooQueryUrl = "http://query.yahooapis.com/v1/public/yql?q=";
        String fields = "Name,Symbol,LastTradeDate,LastTradeTime,LastTradePriceOnly,"
                + "Change,ChangeinPercent,DaysLow,DaysHigh,Volume,PreviousClose,"
                + "Open,Bid,Ask,YearLow,YearHigh,AverageDailyVolume,"
                + "MarketCapitalization,PERatio,EarningsShare";
        String queryStr = String.format("SELECT %s from yahoo.finance.quotes "
                + "where symbol in ", fields).replaceAll(" ", "%20");
        String suffixStr = "&format=json&env=store://datatables.org/alltableswithkeys";
        
        char openParen = '(';
        char closeParen = ')';
        char quotationMark = '"';
        char comma = ',';
        
        if (symbols == null || symbols.isEmpty())
            return queryUrl;
        
        // format symbol string for query
        for (int i = 0; i < symbols.size(); i++) {
            String stockSymbol = symbols.get(i);
            
            if (SymbolMap.isValidSymbol(stockSymbol)) {

                if (!symbolString.toString().isEmpty())
                    symbolString.append(comma);
            
                symbolString.append(quotationMark).append(stockSymbol).append(quotationMark);
            }
        }
        
        // construct yahoo query url
        String queryUrlString = queryUrlStr.append(yahooQueryUrl).append(queryStr)
                .append(openParen).append(symbolString).append(closeParen)
                .append(suffixStr).toString();
        
        try {
            queryUrl = new URL(queryUrlString);
        }
        catch (MalformedURLException ex) {
            System.err.println("ERROR: A MalformedURLException exception was thrown!");
        }
        
        return queryUrl;
    }
        
    /**
     * This method returns an Input stream for the URL provided.
     *
     * @param url URL generated from getURL for the stock symbols
     * @return InputStream of stock data
     */
    @Override
    public InputStream getInputStream(URL url) {
        
        InputStream inputStream = null;
        
        if (url == null) {
            return inputStream;
        }
        
        try {
            inputStream = url.openConnection().getInputStream();
        }
        catch (IOException ex) {
            System.err.println("ERROR: An IO exception has occurred!");
        }
        
        return inputStream;
    }
    
    /**
     * This method returns a list of StockQuote based on the JSON input stream
     * provided.
     *
     * @param is an InputStream containing stock quote data
     * @return a list of StockQuote objects
     */
    @Override
    public List<StockQuote> getStockQuotes(InputStream is) {
        ObjectMapper mapper = new ObjectMapper();
        List<StockQuote> stockQuoteList = new ArrayList<>();
        
        int quoteCount = 0;
        JsonNode rootNode = null;
        JsonNode quoteNode = null;
        
        if (is == null)
            return stockQuoteList; // return empty list
        
        // read the full json tree
        try {
            rootNode = mapper.readTree(is);
        }
        catch (IOException ex) {
            System.err.println("ERROR: An IO exception has occurred!");
            return stockQuoteList; // return empty list
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
            YahooStockQuote yahooStockQuote = mapper.convertValue(quoteNode, YahooStockQuote.class);
            stockQuoteList.add(yahooStockQuote);
        }
        
        return stockQuoteList;
    }
}
