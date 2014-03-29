/**
 * GUI for Stock Ticker Portfolio Manager
 * J308 Project
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
* J308
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

    private final GridBagConstraints m_constraints;

    private QuoteTableModel m_quoteModel;
    private DetailTableModel m_detailModel;

    //private boolean m_isLoggedIn = false;


    /**
    *
    *
    */
    public QuoteCard() {
        m_constraints = new GridBagConstraints();
        setCard();
    }


   
    /**
     * Creates the Card JPanel for this individual ui screen and the JPanels for 
     * the JTables.  Adds the tables JPanels to this Card Panel.  Creates the models
     * models for the JTables.
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
     * Lays out the JTables and add them to their JPanel.
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

        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.anchor = GridBagConstraints.SOUTH;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.insets = new Insets(90, 0, 0, 0);
        m_detailPanel.add(m_detailScrollPane, m_constraints);
    }

    
    /**
     * Gets/returns this Card JPanel
     * @return      - Quote card JPanl
     */
    public JPanel getCard() {
        return m_quoteCard;
    }


    /**
     * Adds the StockQuote to the JTables by calling the JTable models.  The models
     * then add the StockQuote to the first row, index 0 in their respective table.  
     * 
     * @param quote     - listing of an individual StockQuote
     * @param index     - Row location to display StockQuote in table
     * @param enable    - Indicates whether the user is logged in
     */
    public void displayStockQuote(StockQuote quote,  int index, boolean enable) {
        //m_isLoggedIn = enable;
        //if(m_isLoggedIn) {
            m_quoteModel.addQuoteFields(quote, index);
            m_detailModel.addQuoteFields(quote, index);
        //}
        //else {
            System.out.println("No Quote list to display, user not logged in");
        //}
    }


    /**
     * Clears the Tables of their content
     * @param disable
     */
    public void clearQuote(boolean disable) {
        //m_isLoggedIn = disable;
        m_quoteModel.deleteRow();
        m_detailModel.deleteRow();
    }       
//}


    /*
     * inner class QuoteTableModel extends AbstractTableModel
     * Model for JTable.  Adds/displays/removes StockQuote entries to the JTable.  
     */
    class QuoteTableModel extends AbstractTableModel {
        private List<StockQuote> m_quotes;
        private final String[] m_header;

        public QuoteTableModel(String[] header) {
            this.m_header = header;
            this.m_quotes = new ArrayList<>();
        }

        /*
         * Gets the number of rows of the models List<StockQuote>
         */
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
                default:
                    System.err.println("Problems displaying quote");
            }

            return value;
        }


        public void addQuoteFields(StockQuote quote, int index) {
            if(m_quotes.size() > 0) {
                this.deleteRow();
            }
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



    /*
     * class DetailTableModel extends AbstractTableModel
     * Model for JTable.  Adds/displays/removes 2nd portion of StockQuote details
     * to the JTable.  
     *
     */
    class DetailTableModel extends AbstractTableModel {
        private List<StockQuote> m_quotes;
        private final String[] m_header;


        public DetailTableModel(String[] header) {
            this.m_header = header;
            this.m_quotes = new ArrayList<>();
        }


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


        public void addQuoteFields(StockQuote quote, int index) {
            if(m_quotes.size() > 0) {
                this.deleteRow();
            }
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
}
