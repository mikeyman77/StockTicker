/**
 * GUI for Stock Ticker Portfolio Manager
 * 90.308-061 Agile Software Dev. w/Java Project
 * Paul Wallace
 */
package com.stockticker.ui;

import com.stockticker.StockHistory;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;

import java.util.ArrayList;
import java.util.List;

import com.stockticker.ui.ViewStockTicker.OperateStockTicker;
import com.stockticker.StockQuote;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Calendar;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;



/**
 * 
 * J308 Project
 * @author prwallace
 */
public class HistoryCard {
    private static final long serialVersionUID = 1L;
    private static final String[] m_header = { "DATE", "PRICE", "CHG", "CHG%", "LOW", "HIGH", "VOLUME"};
    private static final String ENTER_PRESSED = "ENTER_RELEASED";

    private JPanel m_tablePanel;
    private JPanel m_startDatePanel;
    private JPanel m_labelPanel;
    private JPanel m_historyCard;

    private JLabel m_startDateLabel;
    private JLabel m_endDateLabel;

    private final GridBagConstraints m_constraints;

    private JTable m_historyTable;
    private JScrollPane m_scrollPane;

    private HistoryTableModel m_historyModel;
    private List<String> m_historyList;

    private final OperateStockTicker m_operate;
    private StockQuote m_selectedStock;


    /**
     * Constructs the HistoryCard object.
     *  
     * @param operate   - Instance of OperateStockTicker
     */
    public HistoryCard(OperateStockTicker operate) {
        m_constraints = new GridBagConstraints();
        m_operate = operate;
        setCard();
    }


    /**
     * Adds a Card JPanel as main panel for this ui screen and a child JPanel for
     * the JTable.
     */
    public final void setCard() {
        m_historyModel = new HistoryTableModel(m_header);
        m_historyCard = new JPanel(new GridLayout(2, 1, 0, 0));

        this.setClarendars();
        this.setHistoryTable();
        m_historyCard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "History"));
        //m_historyCard.add(m_startDatePanel, BorderLayout.CENTER);
        m_historyCard.add(m_tablePanel, BorderLayout.SOUTH);
    }


    public void setClarendars() {
        
    }


    /**
     * Adds the stock history table to its JPanel and provides an action listener for
     * the selected rows.
     */
    public void setHistoryTable() {
        m_tablePanel = new JPanel(new FlowLayout());
        m_tablePanel.setPreferredSize(new Dimension(630, 520));
        m_tablePanel.setOpaque(true);

        m_historyTable = new JTable(m_historyModel);
        m_historyTable.setPreferredScrollableViewportSize(new Dimension(630, 500));
        m_historyTable.setFillsViewportHeight(true);
        m_historyTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        m_historyTable.getTableHeader().setFont(new Font("SansSerif", Font.PLAIN, 12));
        m_historyTable.setFont(m_historyTable.getFont().deriveFont(Font.PLAIN, 11));
        m_historyTable.setEnabled(false);

        m_scrollPane = new JScrollPane(m_historyTable);
        m_tablePanel.add(m_scrollPane);
    }


    /**
     * Gets/returns this Card JPanel
     * @return 
     */
    public JPanel getCard() {
        return m_historyCard;
    }



    /**
     * Model for the StockQuote JTable.  Handles adding, inserting, displaying, and
     * deleting day in table.
     */
    class HistoryTableModel extends AbstractTableModel {
        private List<StockHistory> m_stockHistory;
        private final String[] m_header;


        /*
         * Constructor for StockTableModel.  Initializes header String[] and List<StockQuote>
         * fields.  Displays the JTable with header.
         */
        public HistoryTableModel(String[] header) {
            this.m_header = header;
            this.m_stockHistory = new ArrayList<>();
        }


        /**
         * Gets/returns each column name from the header String[].
         */
        @Override
        public String getColumnName(int column) {
            return m_header[column];
        }


        /*
         * Gets/returns the row count of JTable
         */
        @Override
        public int getRowCount() {
            return m_stockHistory.size();
        }


        /*
         * Gets/returns the column count of JTable
         */
        @Override
        public int getColumnCount() {
            return m_header.length;
        }


        /*
         * Gets the individual StockQuote fields within the List<StockQuotes> and 
         * inserts them into their appropriate places in the table.  Adds a plus or
         * minus sign to the Tracked column; "+" for tracking "-" for not tracking.
         * 
         */
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            StockHistory history = (StockHistory)m_stockHistory.get(rowIndex);
            Object value = null;

            switch(columnIndex) {
                case 0:
                    value = history.getDate();
                    break;
                case 1:
                    value = history.getAdjClose();
                    break;
                case 2:
                    value = history.getOpen();
                    break;
                case 3:
                    value = history.getClose();
                    break;
                case 4:
                    value = history.getLow();
                case 5:
                    value = history.getHigh();
                    break;
                case 6:
                    value = history.getVolume();
                    break;
                default:
                    System.err.println("Problems displaying Stock Quotes");
            }

            return value;
        }


        /*
         * Adds the list of StockQuote's to the table models List<StockQuote>
         */
        public void addStocks(List<StockHistory> history) {
            m_stockHistory = history;
            fireTableDataChanged();
        }


        /*
         * Gets/returns the StockQuote at the specified row
         */
        public StockHistory getStock(int row) {
            return m_stockHistory.get(row);
        }


        /*
         * Deletes all rows in the table
         */
        public void deleteAllRows() {
            for(int i = this.getRowCount() - 1; i >= 0; i--) {
                m_stockHistory.remove(i);
            }
        }
    }
}
