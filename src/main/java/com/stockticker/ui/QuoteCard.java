/**
 * GUI for Stock Ticker Portfolio Manager
 * J308 Project
 * Paul Wallace
 */

package com.stockticker.ui;

import com.stockticker.StockQuote;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

/**
* J308
* @author prwallace
*/
public final class QuoteCard extends JPanel {
    private static final long serialVersionUID = 1L;
    /*private static final String[] m_header = {"SYMBOL", "DATE", "PRICE", "CHG", "CHG%", "VOLUMN", "LOW", "HIGH", "AVG_VOL(3m)", "52-WK RANGE", "PREV CLOSE",
                                                "MARKET CAP", "OPEN", "P/E (ttm)", "BID", "EPS", "ASK" };*/
    private static final String[] m_header = {"SYMBOL", "DATE", "PRICE", "CHG", "CHG%", "VOLUMN", "LOW", "HIGH", "AVG_VOL(3m)"};
    
    private static final String[] m_detailRow = {"52-WK RANGE", "PREV CLOSE", "MARKET CAP", "OPEN", "P/E (ttm)", "BID", "EPS", "ASK" };

    private JPanel m_tablePanel;
    private JPanel m_quoteCard;
    private JTable m_table;
    private JScrollPane m_scrollPane;
    private final GridBagConstraints m_constraints;
    //private StockTable m_stocks;

    private QuoteTableModel m_model;
    private List<StockQuote> m_quote;

    //private String m_symbol;
    private boolean m_isLoggedIn = false;


    /**
    *
    *
    */
    public QuoteCard() {
        m_constraints = new GridBagConstraints();
        setCard();
    }


    /**
    *
    *
    */
    public void setCard() {
        m_model = new QuoteTableModel(m_header);
        //m_model = new QuoteTableModel();

        m_quoteCard = new JPanel();
        m_quoteCard.setPreferredSize(new Dimension(680, 520));

        setQuotesTable();
        m_quoteCard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Quote"));
        m_quoteCard.add(m_tablePanel);
        m_quoteCard.setToolTipText("Quote Screen");
    }


    public void setQuotesTable() {
        m_tablePanel = new JPanel(new GridLayout(1, 0));
        m_tablePanel.setOpaque(true);
        m_table = new JTable(m_model);
        m_table.setPreferredScrollableViewportSize(new Dimension(680, 240));
        m_table.setFillsViewportHeight(true);
        m_table.setOpaque(true);

        m_scrollPane = new JScrollPane(m_table);
        m_tablePanel.add(m_scrollPane);

        m_table.setRowSelectionAllowed(true);
        ListSelectionModel rowSelection = m_table.getSelectionModel();
        rowSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rowSelection.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent evt) {
                System.out.println("Select in quote table");
            }
        });  
    }


    /**
    *
    *
     * @return 
    */
    public JPanel getCard() {
        return m_quoteCard;
    }


    public void displayStockQuotes(List<StockQuote> quotes) {
        m_model.addAllQuotes(quotes);
    }


    public void displayStockQuote(StockQuote quote,  int index, boolean enable) {
      int count = m_model.getRowCount();
      System.out.println("Row count = " + count);
      m_isLoggedIn = enable;
      if(m_isLoggedIn) {
        m_model.addQuoteFields(quote, index);
      }
      else {
          System.out.println("No Quote list to display, user not logged in");
      }
    }


    public void clearQuote(boolean disable) {
        //int count = m_model.getRowCount();
        m_isLoggedIn = disable;
        m_model.deleteRow();
    }
}



class QuoteTableModel extends AbstractTableModel {
    private List<StockQuote> m_quotes;
    //private final String[] m_header = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",  "", ""};
    private final String[] m_header;
    //private DefaultTableModel defaultModel;

    public QuoteTableModel(String[] header) {
        this.m_header = header;
        this.m_quotes = new ArrayList<>();
        //defaultModel = new DefaultTableModel();
    }
   

    /*public QuoteTableModel() {
        this.m_quotes = new ArrayList<>();
    }*/

    @Override
    public int getRowCount() {
        return m_quotes.size();
    }

    @Override
    public int getColumnCount() {
        return m_header.length;
    }


    @Override
    public String getColumnName(int column) {
        return m_header[column];
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StockQuote sq = m_quotes.get(rowIndex);
        Object value = null;

        switch(columnIndex) {
            case 0:
                value = sq.getSymbol();
                break;
            case 1:
                value = sq.getDate();
                break;
            case 2:
                value = sq.getPrice();
                break;
            case 3:
                value = sq.getChange();
                break;
            case 4:
                value = sq.getChangePercent();
                break;
            case 5:
                value = sq.getVolume();
                break;
            case 6:
                value = sq.getLow();
                break;
            case 7:
                value = sq.getHigh();
                break;
            case 8:
                value = sq.getAvgVolume();
                break;
            /*case 9:
                value = sq.getYearLow();
                break;
            case 10:
                value = sq.getYearHigh();
                break;
            case 11:
                value = sq.getPrevClose();
                break;
            case 12:
                value = sq.getMarketCap();
                break;
            case 13:
                value = sq.getOpen();
                break;
            case 14:
                value = sq.getPE();
                break;
            case 15:
                value = sq.getBid();
                break;
            case 16:
                value = sq.getEPS();
                break;
            case 17:
                value = sq.getAsk();
                break;*/
            default:
                System.err.println("Problems displaying quote");
        }

        return value;             
 
    }


    public void insertRow(List<StockQuote> quotes) {
        //defaultModel.addColumn("Test");
    }


    public void addAllQuotes(List<StockQuote> quotes) {
        m_quotes = quotes;
        fireTableDataChanged();
    }


    //public void addQuoteFields(StockQuote quote, int index) {
    public void addQuoteFields(StockQuote quote, int index) {
        m_quotes.add(index, quote);
        fireTableRowsInserted(index, index);
    }


    public void removeQuote(StockQuote quote) {
        int index = m_quotes.indexOf(quote);
        m_quotes.remove(index);
        fireTableRowsInserted(index, index);
    }


    public Object getStock(int row) {
        return m_quotes.get(row);
    }


    public void deleteRow() {
        for(int i = m_quotes.size() - 1; i >= 0; i--) {
            m_quotes.remove(i);
        }
    } 
}