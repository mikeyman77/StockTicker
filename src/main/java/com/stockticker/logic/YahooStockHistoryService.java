
package com.stockticker.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockticker.StockHistory;
import com.stockticker.SymbolMap;
import com.stockticker.YahooStockHistory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class provides stock history service from Yahoo.
 * 
 * @author Michael Grissom
 */
public enum YahooStockHistoryService implements StockHistoryService {
    INSTANCE;
    
    /**
     * This method gets the URL for a stock history query.
     * 
     * @param symbol stock symbol to get history on
     * @param startDate start date for the history (YYYY-MM-DD)
     * @param endDate end date for the history (YYYY-MM-DD)
     * @return a URL object to get stock history data
     */
    @Override
    public URL getURL(String symbol, Date startDate, Date endDate) {
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        URL queryUrl = null;
        String queryFields;
        String yahooQueryUrl;
        String yahooQueryStr;
        
        if (symbol == null || startDate == null ||  endDate == null ) {
            throw new IllegalArgumentException("Error: You did not specify a symbol, start date or end date!");
        }
        
        if (symbol.isEmpty() || !SymbolMap.isValidSymbol(symbol)) {
            throw new IllegalArgumentException("Error: The symbol you specified is invalid!");
        }
        
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("Error: The start date is after the end date!");
        }
        
        queryFields = "Symbol,Date,Open,High,Low,Close,Volume,Adj_Close";
        yahooQueryUrl = "http://query.yahooapis.com/v1/public/yql?q=";
        yahooQueryStr = String.format("SELECT %s "
                + "FROM yahoo.finance.historicaldata "
                + "WHERE symbol=\"%s\" and startDate=\"%s\" and endDate=\"%s\""
                + "&format=json&env=store://datatables.org/alltableswithkeys",
                queryFields, symbol, sdf.format(startDate), sdf.format(endDate))
                .replace(" ", "%20");

        try {
            queryUrl = new URL(yahooQueryUrl + yahooQueryStr);
        }
        catch (MalformedURLException ex) {
            throw new RuntimeException("Error: The URL was malformed, please try again!");
        }
        
        return queryUrl;
    }
    
    /**
     * This method get the stock history data from the URL.
     * 
     * @param url URL to retrieve the stock history data from
     * @return a list of StockHistory objects
     */
    @Override
    public List<StockHistory> getStockHistory(URL url) {
        
        ObjectMapper mapper = new ObjectMapper();
        List<StockHistory> stockHistoryList = new ArrayList<>();
        
        int quoteCount = 0;
        JsonNode rootNode = null;
        JsonNode historyNode = null;
        
        if (url == null)
            return stockHistoryList; // return empty list
        
        // read the full json tree
        try {
            rootNode = mapper.readTree(url.openConnection().getInputStream());
        }
        catch (IOException ex) {
            throw new RuntimeException("Error: An IOException has occurred, please try again!");
        }
        
        // get the count of quotes returned
        quoteCount = rootNode.path("query").path("count").asInt();
        
        for (int i = 0; i < quoteCount; i++) {
            if (quoteCount > 1) {
                historyNode = rootNode.path("query").path("results").path("quote").path(i);
            }
            else {
                historyNode = rootNode.path("query").path("results").path("quote");
            }
            historyNode = rootNode.path("query").path("results").path("quote").path(i);
            YahooStockHistory yahooStockHistory = mapper.convertValue(historyNode, YahooStockHistory.class);
            stockHistoryList.add(yahooStockHistory);
        }
        
        return stockHistoryList;
    }
    
}
