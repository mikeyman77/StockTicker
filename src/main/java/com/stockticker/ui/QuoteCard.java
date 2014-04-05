/**
 * GUI for Stock Ticker Portfolio Manager
 * 90.308-061 Agile Software Dev. w/Java Project
 * Paul Wallace
 */
package com.stockticker.ui;

import com.stockticker.StockQuote;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Quote screen for Stock Ticker Portfolio Manager
 * Provides quote details of a selected stock symbol.  The date is displayed within
 * 2 tables.  Provides a means to enter the History screen and Track/Un-track this stock.
 * @author prwallace
 */
public final class QuoteCard extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final String[] m_quoteHeader = {"SYM", "DATE", "PRICE", "CHG", "CHG%", "VOL", "LOW", "HIGH", "VOL(3m)"};
    private static final String[] m_detailHeader = {"YR_LOW", "YR_HIGH","PREV", "CAP", "OPEN", "P/E (ttm)", "BID", "EPS", "ASK" };

    private JPanel m_quotePanel;
    private JPanel m_quoteCard;
    private JPanel m_detailPanel;

    private JTable m_quoteTable;
    private JTable m_detailTable;

    private JScrollPane m_scrollPane;
    private JScrollPane m_detailScrollPane;

    private GridBagConstraints m_constraints;

    private QuoteTableModel m_quoteModel;
    private DetailTableModel m_detailModel;


   
    /**
     * Constructs the QuoteCard object
     */
    public QuoteCard() {
        setCard();
    }

   
    /**
     * Creates the Card JPanel for this individual UI screen and the JPanels for 
     * the JTables.  Calls setQuotesTable to Add the tables JPanels to this Card
     * Panel.  Instantiates the models for the JTables.
     */
    public void setCard() {
        m_quoteModel = new QuoteTableModel(m_quoteHeader);
        m_detailModel = new DetailTableModel(m_detailHeader);

        m_quoteCard = new JPanel();
        m_quoteCard.setPreferredSize(new Dimension(550, 520));

        setQuotesTable();
        m_quoteCard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Quote"));
        m_quoteCard.add(m_quotePanel, BorderLayout.NORTH);
        m_quoteCard.add(m_detailPanel, BorderLayout.SOUTH);
    }


    /**
     * Create the panels for the tables, layout the tables on their respective 
     * panel, and add the tables to their panels.
     */
    public void setQuotesTable() {
        m_quotePanel = new JPanel(new GridBagLayout());
        m_quotePanel.setOpaque(true);
        m_quoteTable = new JTable(m_quoteModel);
        m_quoteTable.setPreferredScrollableViewportSize(new Dimension(630, 30));
        m_quoteTable.setFillsViewportHeight(true);
        m_quoteTable.setOpaque(true);
        m_quoteTable.setFont(m_quoteTable.getFont().deriveFont(Font.PLAIN, 11));
        m_quoteTable.setEnabled(false);
        m_scrollPane = new JScrollPane(m_quoteTable);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.anchor = GridBagConstraints.NORTH;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.insets = new Insets(30, 0, 0, 0);
        m_quotePanel.add(m_scrollPane, m_constraints);


        m_detailPanel = new JPanel(new GridBagLayout());
        m_detailPanel.setOpaque(true);
        m_detailTable = new JTable(m_detailModel);
        m_detailTable.setPreferredScrollableViewportSize(new Dimension(630, 30));
        m_detailTable.setFillsViewportHeight(true);
        m_detailTable.setOpaque(true);
        m_detailTable.setFont(m_detailTable.getFont().deriveFont(Font.PLAIN, 11));
        m_detailTable.setEnabled(false);
        m_detailScrollPane = new JScrollPane(m_detailTable);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.anchor = GridBagConstraints.SOUTH;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.insets = new Insets(90, 0, 0, 0);
        m_detailPanel.add(m_detailScrollPane, m_constraints);
    }


    /**
     * Gets/returns this Card JPanel
     * @return  - Quote card JPanl
     */
    public JPanel getCard() {
        return m_quoteCard;
    }


    /**
     * Adds the StockQuote to both the tables by calling their table models.  The
     * models then add the StockQuote to the first row, index 0 in their respective table.  
     * 
     * @param quote - listing of an individual StockQuote
     * @param index - Row location to display StockQuote in table
     * @param enable    - Indicates whether the user is logged in
     */
    public void displayStockQuote(StockQuote quote,  int index, boolean enable) {
        m_quoteModel.addQuoteFields(quote, index);
        m_detailModel.addQuoteFields(quote, index);
    }


    /**
     * Clears the Tables of their content
     */
    public void clearQuote() {
        m_quoteModel.deleteRow();
        m_detailModel.deleteRow();
    }


    /**
     * Model for the Quote Table.  inner class QuoteTableModel extends Abstract-
     * TableModel.  Adds/displays/removes StockQuote entries to the table.  
     */
    class QuoteTableModel extends AbstractTableModel {
        private List<StockQuote> m_quotes;
        private final String[] m_header;

        /**
         * Construct the quote table model
         * 
         * @param header    - the header for each column in model
         */
        public QuoteTableModel(String[] header) {
            this.m_header = header;
            this.m_quotes = new ArrayList<>();
        }


        /**
         * Gets/returns the total number of rows in table
         * @return  - the total row count
         */
        @Override
        public int getRowCount() {
            return m_quotes.size();
        }


        /**
         * Gets the total number of columns in table
         * @return  - the total column count
         */
        @Override
        public int getColumnCount() {
            return m_header.length;
        }


        /**
         * Gets/returns the header at the given column
         * @param column    - column to retrieve header
         * @return  - the header at this given column position
         */
        @Override
        public String getColumnName(int column) {
            return m_header[column];
        }


        /**
         * Gets/returns the individual StockQuote fields from the table model
         * and inserts them into their appropriate places in the table.
         * 
         * @param rowIndex  - row index to stock quote field
         * @param columnIndex   - column index to stock quote field
         * @return  - the StockQuote field at the specified index
         */
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
                default:
                    System.err.println("Problems displaying quote");
            }

            return value;
        }


        /**
         * Adds the StockQuote object to the table model at the specified index
         * 
         * @param quote - StockQuote object to add to the model
         * @param index - Location in model to add the StockQuote
         */
        public void addQuoteFields(StockQuote quote, int index) {
            if(m_quotes.size() > 0) {
                this.deleteRow();
            }
            m_quotes.add(index, quote);
            fireTableRowsInserted(index, index);
        }


        /**
         * Removes the StockQuote object from the table model
         * 
         * @param quote - StockQuote to remove from table
         */
        public void removeQuote(StockQuote quote) {
            int index = m_quotes.indexOf(quote);
            m_quotes.remove(index);
            fireTableRowsInserted(index, index);
        }


        /**
         * Gets/returns the StockQuote located at the specified row in the table
         * model.
         * 
         * @param row   - row location of StockQuote within the table model
         * @return  - the StockQuote at the specified location
         */
        public Object getStock(int row) {
            return m_quotes.get(row);
        }


        /**
         * Deletes all StockQuotes from the table model and the table.
         */
        public void deleteRow() {
            for(int i = m_quotes.size() - 1; i >= 0; i--) {
                m_quotes.remove(i);
            }
        }
    }



    /**
     * Model for the Detail Table.  inner class DetailTableModel extends Abstract-
     * TableModel.  Adds/displays/removes StockQuote entries to the table.  
     */
    class DetailTableModel extends AbstractTableModel {
        private List<StockQuote> m_quotes;
        private final String[] m_header;

        /**
         * Constructor for the DetailTable model
         * 
         * @param header 
         */
        public DetailTableModel(String[] header) {
            this.m_header = header;
            this.m_quotes = new ArrayList<>();
        }


        /**
         * Gets/returns total row count from model
         * 
         * @return  - the total row count from model
         */
        @Override
        public int getRowCount() {
            return m_quotes.size();
        }


        /**
         * Gets/returns the total column count from model
         * 
         * @return  - the total column count from model
         */
        @Override
        public int getColumnCount() {
            return m_header.length;
        }


        /**
         * Gets/returns the header at the specified column location of model
         * 
         * @param column    - the column location from model 
         * @return  - the header from the model from the specified location
         */
        @Override
        public String getColumnName(int column) {
            return m_header[column];
        }


        /**
         * Gets/returns the individual StockQuote fields from the table model
         * and inserts them into their appropriate places in the table.
         * 
         * @param rowIndex  - row index to stock quote field
         * @param columnIndex   - column index to stock quote field
         * @return  - the StockQuote field at the specified index
         */
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            StockQuote sq = m_quotes.get(rowIndex);
            Object value = null;

            switch(columnIndex) {
                case 0:
                    value = sq.getYearLow();
                    break;
                case 1:
                    value = sq.getYearHigh();
                    break;
                case 2:
                    value = sq.getPrevClose();
                    break;
                case 3:
                    value = sq.getMarketCap();
                    break;
                case 4:
                    value = sq.getOpen();
                    break;
                case 5:
                    value = sq.getPE();
                    break;
                case 6:
                    value = sq.getBid();
                    break;
                case 7:
                    value = sq.getEPS();
                    break;
                case 8:
                    value = sq.getAsk();
                    break;
                default:
                    System.err.println("Problems displaying quote details");
            }

            return value;
        }


        /**
         * Adds the StockQuote object to the table model at the specified index
         * 
         * @param quote - StockQuote object to add to the model
         * @param index - Location in model to add the StockQuote
         */
        public void addQuoteFields(StockQuote quote, int index) {
            if(m_quotes.size() > 0) {
                this.deleteRow();
            }
            m_quotes.add(index, quote);
            fireTableRowsInserted(index, index);
        }


        /**
         * Removes the StockQuote object from the table model and the table
         * 
         * @param quote - StockQuote to remove from table model and table
         */
        public void removeQuote(StockQuote quote) {
            int index = m_quotes.indexOf(quote);
            m_quotes.remove(index);
            fireTableRowsInserted(index, index);
        }


        /**
         * Gets/returns the StockQuote object from the specified row
         * 
         * @param row   - row location of the StockQuote
         * @return  - the StockQuote object
         */
        public Object getStock(int row) {
            return m_quotes.get(row);
        }


        /**
         * Deletes all StockQuotes from the table model and the table.
         */
        public void deleteRow() {
            for(int i = m_quotes.size() - 1; i >= 0; i--) {
                m_quotes.remove(i);
            }
        }
    }
}
