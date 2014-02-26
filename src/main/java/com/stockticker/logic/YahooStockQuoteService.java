
package com.stockticker.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockticker.StockQuote;
import com.stockticker.YahooStockQuote;
import java.io.IOException;
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
    
    private URL queryUrl = null;
    
    @Override
    public boolean constructURL(List<String> symbols) throws MalformedURLException {
        
        StringBuilder symbolString = new StringBuilder();
        StringBuilder queryUrlStr = new StringBuilder();
        
        String yahooQueryUrl = "http://query.yahooapis.com/v1/public/yql?q=";
        String fields = "Name,Symbol,LastTradeDate,LastTradeTime,LastTradePriceOnly,"
                + "Change,ChangeinPercent,DaysLow,DaysHigh,Volume,PreviousClose,"
                + "Open,Bid,Ask,YearLow,YearHigh,AverageDailyVolume,"
                + "MarketCapitalization,PERatio,EarningsShare";
        String queryStr = String.format("SELECT %s from yahoo.finance.quotes "
                + "where symbol in ", fields).replaceAll(" ", "%20");
        String suffixStr = "&format=json&env=store://datatables.org/alltableswithkeys";
        
        String openParen = "%28";
        String closeParen = "%29";
        String quotationMark = "%22";
        String comma = "%2C";
        
        if (symbols.isEmpty())
            return false;
        
        // format symbol string for query
        for (int i = 0; i < symbols.size(); i++) {
            String stockSymbol = symbols.get(i);
            
            if (i != 0)
                symbolString.append(comma);
            
            symbolString.append(quotationMark).append(stockSymbol).append(quotationMark);
        }
        
        // construct yahoo query url
        String queryUrlString = queryUrlStr.append(yahooQueryUrl).append(queryStr)
                .append(openParen).append(symbolString).append(closeParen)
                .append(suffixStr).toString();
        
        try {
            queryUrl = new URL(queryUrlString);
            return true;
        }
        catch (MalformedURLException ex) {
            System.err.println("ERROR: A MalformedURLException exception was thrown!");
            return false;
        }
    }
    
    @Override
    public List<StockQuote> getStocks() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<StockQuote> stockQuoteList = new ArrayList<>();
        
        int quoteCount = 0;
        JsonNode rootNode = null;
        JsonNode quoteNode = null;
        
        if (queryUrl == null)
            return null;
        
        // read the full json tree
        try {
            rootNode = mapper.readTree(queryUrl);
        }
        catch (IOException ex) {
            System.err.println("ERROR: An IO exception was thrown!");
            return stockQuoteList; // return empty list
        }
        
        // get the count of quotes returned
        quoteCount = rootNode.path("query").path("count").asInt();
        
        for (int i = 0; i < quoteCount; i++) {
            quoteNode = rootNode.path("query").path("results").path("quote").path(i);
            YahooStockQuote yahooStockQuote = mapper.convertValue(quoteNode, YahooStockQuote.class);
            stockQuoteList.add(yahooStockQuote);
        }
        
        return stockQuoteList;
    }
}
