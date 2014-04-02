
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
    
    
    @Override
    public URL getURL(String symbol, Date startDate, Date endDate) throws BusinessLogicException {
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        URL queryUrl = null;
        String queryFields;
        String yahooQueryUrl;
        String yahooQueryStr;
        
        if (symbol == null || startDate == null ||  endDate == null ) {
            throw new BusinessLogicException("Error: You did not specify a symbol, start date or end date!");
        }
        
        if (symbol.isEmpty() || !SymbolMap.isValidSymbol(symbol)) {
            throw new BusinessLogicException("Error: The symbol you specified is invalid!");
        }
        
        if (startDate.after(endDate)) {
            throw new BusinessLogicException("Error: The start date is after the end date!");
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
            throw new BusinessLogicException("Error: Could not generate URL, check logs", ex);
        }
        
        return queryUrl;
    }
    
    @Override
    public List<StockHistory> getStockHistory(URL url) throws BusinessLogicException {
        
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
            throw new BusinessLogicException("Error: Could not get stock history, check logs", ex);
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
            
            if (!historyNode.isMissingNode()) {
                YahooStockHistory yahooStockHistory = mapper.convertValue(historyNode, YahooStockHistory.class);
                stockHistoryList.add(yahooStockHistory);
            }
        }
        
        return stockHistoryList;
    }
}
