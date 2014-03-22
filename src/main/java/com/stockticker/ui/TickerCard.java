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


/**
 * 
 * J308 Project
 * @author prwallace
 */
public class TickerCard extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final String[] m_header = { "SYMBOL", "TIME", "PRICE", "CHG", "CHG%", "LOW", "HIGH", "VOLUME", "Tracked" };
    private static final String ENTER_PRESSED = "ENTER_RELEASED";

    private static final int FIRST_ROW = 0;
    private static final int CLICK_COUNT = 2;

    private JPanel m_tablePanel;
    private JPanel m_tickerCard;
    private JPanel m_buttonPanel;
    private JPanel m_cardPanel;
    private CardLayout m_cardLayout;
    private final GridBagConstraints m_constraints;

    private JTable m_table;
    private JScrollPane m_scrollPane;
    private JTextField m_quoteField;

    private StockTableModel m_model;
    private List<String> m_symbolList;

    private final OperateStockTicker m_operate;
    private StockQuote m_selectedStock;

    private final QuoteCard m_quoteCard;

    private int m_quoteIndex;
    private boolean m_isLoggedIn = false;


    /**
     * Construct the TickerCard.
     * Set the argument CardLayout to the main JPanel of this class.
     *  
     * @param cards     - JPanel containing a CardLayout
     * @param quoteCard
     */
    public TickerCard(JPanel cards, QuoteCard quoteCard, OperateStockTicker operate) {
        m_constraints = new GridBagConstraints();
        m_operate = operate;
        setCard();
        m_cardPanel = cards;
        m_quoteCard = quoteCard;
    }


    /**
     * 
     * 
     * @return 
     */
    public final JPanel setCard() {
        m_model = new StockTableModel(m_header);

        m_tickerCard = new JPanel();
        m_tickerCard.setPreferredSize(new Dimension(600, 520));

        setStockTable();
        m_tickerCard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Stocks"));
        m_tickerCard.add(m_tablePanel);

        setButtonPanel();
        m_tickerCard.add(m_buttonPanel, BorderLayout.SOUTH);

        return m_tickerCard;
    }


    /**
     * 
     * 
     */
    public void setStockTable() {
        m_tablePanel = new JPanel(new GridLayout(1, 0));
        m_tablePanel.setOpaque(true);
        m_table = new JTable(m_model);
        m_table.setPreferredScrollableViewportSize(new Dimension(580, 240));
        m_table.setFillsViewportHeight(true);

        TableColumn column = m_table.getColumnModel().getColumn(8);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        column.setCellRenderer(cellRenderer);
        cellRenderer.setBackground(m_tablePanel.getBackground());

        m_scrollPane = new JScrollPane(m_table);
        m_tablePanel.add(m_scrollPane);

        m_table.setRowSelectionAllowed(true);
        final ListSelectionModel rowSelection = m_table.getSelectionModel();
        rowSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        m_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                if(evt.getClickCount() == CLICK_COUNT) {
                    if(m_table.getSelectedRow() >= FIRST_ROW) {
                        m_quoteIndex = m_table.getSelectedRow();
                        m_selectedStock = m_model.getStock(m_quoteIndex);

                        //Insure there is at least one stock selected from table
                        if(m_selectedStock != null) {
                            if(m_isLoggedIn) {                      
                                m_quoteCard.displayStockQuote(m_selectedStock, FIRST_ROW, true);

                                if(m_operate.getTrackingStatus(m_selectedStock.getSymbol())) {
                                    m_operate.resetLeftButton(UI.UNTRACK.getName());   
                                }
                                else {
                                    m_operate.resetLeftButton(UI.TRACK.getName());
                                }
                                
                                m_operate.resetRightButton(UI.CLOSE.getName());
                                m_operate.enableSymbolJList(false);
                                m_cardLayout = (CardLayout) m_cardPanel.getLayout();
                                m_cardLayout.show(m_cardPanel, UI.QUOTE.getName());
                                System.out.println("Display stock qoute table");
                            }
                            else {
                                System.out.println("Unable to display stock quote, user not logged in");
                            }
                        }
                        else {
                            System.out.println("Unable to get stock from stock quote list");
                        }
                    } 
                }
            }
        });
    }
                


    /**
     * 
     * 
     */
    public void setButtonPanel() {
        m_buttonPanel = new JPanel(new GridBagLayout());
        m_buttonPanel.setPreferredSize(new Dimension(300, 100));
        

        JButton quote = new JButton("Quote");
        quote.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                m_operate.showStockQuoteList();
            }
        });


        m_quoteField = new JTextField(40);
        //m_quoteField.setEditable(false);
        m_quoteField.setBorder( BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));

        Action enterAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               m_operate.showStockQuoteList();
            }
        };

        m_quoteField.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), ENTER_PRESSED);
        m_quoteField.getActionMap().put(ENTER_PRESSED, enterAction);


        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.weightx = 0.00001;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.NONE;
        m_buttonPanel.add(quote, m_constraints);

        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.weightx = 0.0;
        m_constraints.gridwidth = 2;
        m_constraints.fill = GridBagConstraints.HORIZONTAL;
        m_constraints.insets = new Insets(0, 80, 0, 0);
        m_buttonPanel.add(m_quoteField, m_constraints);
    }


    /**
     * 
     * @return 
     */
    public JPanel getCard() {
        return m_tickerCard;
    }


    /**
     *
     * @param symbolList
     * @param enable
     */
    public void setSymbolsTextField(List<String> symbolList, boolean enable) {
        StringBuilder sb = new StringBuilder();
        m_symbolList = symbolList;
        m_isLoggedIn = enable;

        for(String symbol : m_symbolList) {
            sb.append(symbol);
            sb.append(", ");
        }

        sb.deleteCharAt(sb.lastIndexOf(", "));
        m_quoteField.setText(sb.toString());
        m_quoteField.grabFocus();
    }


    /**
     * Adds the argument List<StockQuote> in to the StockTableModel.  The StockTableModel
     * inserts the data into the JTable and displays the JTable.
     * list 
     * @param stocks
     * @param enable
     */
    public void displayStockQuoteList(List<StockQuote> stocks, boolean enable) {
        m_isLoggedIn = enable;
        m_model.addStocks(stocks);
        System.out.println("Stock quotes displayed in table");
    }


    /**
     *
     * @param disable
     */
    public void clearStockList(boolean disable) {
        m_isLoggedIn = disable;
        m_selectedStock = null;
        m_quoteField.setText("");
        m_model.deleteAllRows();
    }


    /**
     * Gets/returns the selected stock from stock quote list
     * 
     * @return
     */
    public StockQuote getSelectedStock() {
        return m_selectedStock;
    }



    /**
     * Model for the StockQuote JTable.  Handles adding, inserting, displaying, and
     * deleting day in table.
     */
    class StockTableModel extends AbstractTableModel {
        private List<StockQuote> m_stocks;
        private final String[] m_header;


        /*
         * Constructor for StockTableModel.  Initializes header String[] and List<StockQuote>
         * fields.  Displays the JTable with header.
         */
        public StockTableModel(String[] header) {
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
            StockQuote sq = m_stocks.get(rowIndex);
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


        public void addStocks(List<StockQuote> stock) {
            m_stocks = stock;
            fireTableDataChanged();
        }


        /*
         * Gets/returns the StockQuote at the specified row 
         * 
         */
        public StockQuote getStock(int row) {
                return m_stocks.get(row);
        }


        /*
         * Deletes all rows in the table
         */
        public void deleteAllRows() {
            for(int i = m_stocks.size() - 1; i >= 0; i--) {
                m_stocks.remove(i);
            }
        }
    }
}