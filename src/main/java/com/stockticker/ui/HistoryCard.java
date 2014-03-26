/**
 * GUI for Stock Ticker Portfolio Manager
 * J308 Project
 * Paul Wallace
 */
package com.stockticker.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.SwingConstants;

import java.util.ArrayList;
import java.util.List;

import com.stockticker.ui.ViewStockTicker.OperateStockTicker;
import com.stockticker.ui.IStockTicker_UIComponents.UI;
import com.stockticker.StockQuote;
import java.awt.Font;



/**
 * 
 * J308 Project
 * @author prwallace
 */
public class HistoryCard {
    private static final long serialVersionUID = 1L;
    private static final String[] m_header = { "DATE", "PRICE", "CHG", "CHG%", "LOW", "HIGH", "VOLUME"};
    private static final String ENTER_PRESSED = "ENTER_RELEASED";

    private JPanel m_tablePane;
    private JPanel m_historyCard;
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

        m_historyCard = new JPanel();
        m_historyCard.setPreferredSize(new Dimension(550, 200));

        setHistoryTable();
        m_historyCard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "History"));
        m_historyCard.add(m_tablePane);
    }


    /**
     * Adds the stock history table to its JPanel and provides an action listener for
     * the selected rows.
     */
    public void setHistoryTable() {
        m_tablePane = new JPanel(new GridLayout(1, 0));
        m_tablePane.setOpaque(true);
        m_historyTable = new JTable(m_historyModel);
        m_historyTable.setPreferredScrollableViewportSize(new Dimension(630, 240));
        m_historyTable.setFillsViewportHeight(true);
        m_historyTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        m_historyTable.setFont(m_historyTable.getFont().deriveFont(Font.PLAIN, 11));
        m_historyTable.setEnabled(false);

        /*TableColumn column = m_historyTable.getColumnModel().getColumn(8);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        column.setCellRenderer(cellRenderer);
        cellRenderer.setBackground(m_tablePane.getBackground());
        cellRenderer.setFont(cellRenderer.getFont().deriveFont(Font.PLAIN, 20));*/

        m_scrollPane = new JScrollPane(m_historyTable);
        m_tablePane.add(m_scrollPane);
    
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
        private List<StockQuote> m_stocks;
        private final String[] m_header;


        /*
         * Constructor for StockTableModel.  Initializes header String[] and List<StockQuote>
         * fields.  Displays the JTable with header.
         */
        public HistoryTableModel(String[] header) {
            this.m_header = header;
            this.m_stocks = new ArrayList<>();
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
            return m_stocks.size();
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
            StockQuote sq = (StockQuote)m_stocks.get(rowIndex);
            Object value = null;

            switch(columnIndex) {
                case 0:
                    value = sq.getSymbol();
                    break;
                case 1:
                    value = sq.getTime();
                    break;
                case 2:
                    value = sq.getPrice();
                    break;
                case 3:
                    value = sq.getChange();
                    break;
                case 4:
                    value = sq.getChangePercent();
                case 5:
                    value = sq.getLow();
                    break;
                case 6:
                    value = sq.getHigh();
                    break;
                case 7:
                    value = sq.getVolume();
                    break;
                case 8:
                    if(m_operate.getTrackingStatus(sq.getSymbol())) {
                        value = "+";
                    }
                    else {
                        value = "-";
                    }
                    break;
                default:
                    System.err.println("Problems displaying Stock Quotes");
            }

            return value;
        }


        /*
         * Adds the list of StockQuote's to the table models List<StockQuote>
         */
        public void addStocks(List<StockQuote> stock) {
            m_stocks = stock;
            fireTableDataChanged();
        }


        /*
         * Gets/returns the StockQuote at the specified row
         */
        public StockQuote getStock(int row) {
            return m_stocks.get(row);
        }


        /*
         * Deletes all rows in the table
         */
        public void deleteAllRows() {
            for(int i = this.getRowCount() - 1; i >= 0; i--) {
                m_stocks.remove(i);
            }
        }
    }
}
