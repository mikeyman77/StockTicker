/**
 * GUI for Stock Ticker Portfolio Manager
 * 90.308-061 Agile Software Dev. w/Java Project
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
import java.awt.Font;

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
import java.util.Arrays;


/**
 * GUI for Stock Ticker Portfolio Manager
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
    private GridBagConstraints m_constraints;

    private StockTableModel m_model;
    private JTable m_table;
    private JScrollPane m_scrollPane;
    private JTextField m_quoteField;

    private final OperateStockTicker m_operate;
    private StockQuote m_selectedStock;
    private final QuoteCard m_quoteCard;

    private List<String> m_symbolList;
    private String m_splitChars = "[\",]+";
    private int m_quoteIndex;



    /**
     * Constructs the TickerCard object.
     *
     * @param cards         - contains the CardLayout
     * @param quoteCard     - base JPanel for this form
     * @param operate       - Instance of OperateStockTicker
     */
    public TickerCard(JPanel cards, QuoteCard quoteCard, OperateStockTicker operate) {
        m_operate = operate;
        setCard();
        m_cardPanel = cards;
        m_quoteCard = quoteCard;
    }


    /**
     * Creates the Card JPanel for this ui screen and adds the child JPanels for
     * the JTable, Quote button, and Quote text field.  Also creates the model for
     * the JTable.
     */
    public final void setCard() {
        m_model = new StockTableModel(m_header);

        m_tickerCard = new JPanel();
        m_tickerCard.setPreferredSize(new Dimension(550, 520));

        setStockTable();
        m_tickerCard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Stocks"));
        m_tickerCard.add(m_tablePanel);

        setButtonPanel();
        m_tickerCard.add(m_buttonPanel, BorderLayout.SOUTH);
    }


    /**
     * Adds the stock quote table to its JPanel and provides an action listener for
     * the selected rows.
     */
    public void setStockTable() {
        m_tablePanel = new JPanel(new GridLayout(1, 0));
        m_tablePanel.setOpaque(true);
        m_table = new JTable(m_model);
        m_table.setPreferredScrollableViewportSize(new Dimension(630, 240));
        m_table.setFillsViewportHeight(true);
        m_table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        m_table.setFont(m_table.getFont().deriveFont(Font.PLAIN, 11));

        TableColumn column = m_table.getColumnModel().getColumn(8);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        column.setCellRenderer(cellRenderer);
        cellRenderer.setBackground(m_tablePanel.getBackground());
        cellRenderer.setFont(cellRenderer.getFont().deriveFont(Font.PLAIN, 20));

        m_scrollPane = new JScrollPane(m_table);
        m_tablePanel.add(m_scrollPane);

        m_table.setRowSelectionAllowed(true);
        final ListSelectionModel rowSelection = m_table.getSelectionModel();
        rowSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Mouse listener for the table.  Allows user to select a stock quote from
        // the table to track and get more details on the indivudual Stock
        m_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                if(evt.getClickCount() == CLICK_COUNT) {
                    if(m_table.getSelectedRow() >= FIRST_ROW) {
                        m_quoteIndex = m_table.getSelectedRow();
                        m_selectedStock = m_model.getStock(m_quoteIndex);

                        //Insure there is at least one stock selected from table
                        if(m_selectedStock != null) {                     
                            m_quoteCard.displayStockQuote(m_selectedStock, FIRST_ROW, true);

                            if(m_operate.getTrackingStatus(m_selectedStock.getSymbol())) {
                                m_operate.resetLeftButton(UI.UNTRACK.getName()); 
                            }
                            else {
                                m_operate.resetLeftButton(UI.TRACK.getName());
                            }

                            m_operate.resetRightButton(UI.CLOSE.getName());
                            m_operate.enableSymbolJList(false);
                            m_operate.setHistoryButton(true);
                            m_operate.setControlBtnFocus(true);
                            m_cardLayout = (CardLayout) m_cardPanel.getLayout();
                            m_cardLayout.show(m_cardPanel, UI.QUOTE.getName());
                            //System.out.println("Display stock qoute table");
                        }
                        /*else {
                            System.out.println("Unable to get stock from stock quote list");
                        }*/
                    } 
                }

                setQuoteFieldFocus();
            }
        });
    }                


    /**
     * Adds a JPanel with a JButton and JText Field to the main JPanel and its listeners.
     * The text field lists the selected symbols from the symbols list.  Pressing enter
     * or Quote button will call StockTickerService for a list of stock quotes based on
     * the symbols in text field.  The stock quote list is then displayed in the table. 
     */
    public void setButtonPanel() {
        m_buttonPanel = new JPanel(new GridBagLayout());
        m_buttonPanel.setPreferredSize(new Dimension(300, 100));

 
        JButton quoteBtn = new JButton("Quote");
        quoteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                m_operate.showStockQuoteList(getSymbolsTextField());
                m_quoteField.setText("");
                setQuoteFieldFocus();
            }
        });

        m_quoteField = new JTextField(40);
        m_quoteField.setBorder( BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),
                                                                BorderFactory.createLoweredBevelBorder()));
        m_quoteField.setText("");

        Action enterAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                m_operate.showStockQuoteList(getSymbolsTextField());
                m_quoteField.setText(""); 
            }
        };

        m_quoteField.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), ENTER_PRESSED);
        m_quoteField.getActionMap().put(ENTER_PRESSED, enterAction);


        // Layout quote button and quote text field onto their JPanels
        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.weightx = 0.00001;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.NONE;
        m_buttonPanel.add(quoteBtn, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.weightx = 0.0;
        m_constraints.gridwidth = 2;
        m_constraints.fill = GridBagConstraints.HORIZONTAL;
        m_constraints.insets = new Insets(0, 80, 0, 0);
        m_buttonPanel.add(m_quoteField, m_constraints);
    }


    /**
     * Gets/returns this Card JPanel
     * 
     * @return      - Ticker card panel
     */
    public JPanel getCard() {
        return m_tickerCard;
    }


    /**
     * Gets/returns the individual symbols listed in the table as a List<String>.
     * 
     * @param symbols       - stock symbol to retrieve from table
     * @return              - the List<String> of symbols retrieved from table
     */
    //public List<String> getSymbolsInTable(List<String> symbols) {
    public List<String> getSymbolsInTable() {
        List<String> symbols = new ArrayList<>();

        if(m_model.getRowCount() > 0) {
            for(int i = 0; i < m_model.getRowCount(); i++) {
                symbols.add(m_model.getStock(i).getSymbol().toString());
            }
        }

        return symbols;
    }


    /**
     * Gets/returns the symbols from the quote text field.  The user can manually 
     * enter symbols into field, delimited by comma's or add symbols to it from
     * the symbols list.  Removes white space and comma's delimiters and insures
     * the entry is no longer than 5 characters in length.
     * 
     * @return      - returns a List<String> of stock symbols
     */
    public List<String> getSymbolsTextField() {
        List<String> list = new ArrayList<>();
        List<String> symbols = new ArrayList<>();

        if(!m_quoteField.getText().isEmpty()) {
            //list = Arrays.asList( m_quoteField.getText().split(","));
            list = Arrays.asList( m_quoteField.getText().split(m_splitChars));
            for(String str : list) {
                String tmp = str.trim().toUpperCase();
                if(tmp.length() < 6) {
                    symbols.add(tmp);
                }
            }
        }

        return symbols;
    }


    /**
     * Gets/returns the selected stock from the table
     */
    public StockQuote getSelectedStock() {
        return m_selectedStock;
    }


    /**
     * Sets the focus in the quote text field
     */
    public void setQuoteFieldFocus() {
        m_quoteField.grabFocus();
    }


    /**
     * Sets the quote text field with symbols from the argument List<String> .  The
     * symbols are delimited by commas.  into the text field.
     * 
     * @param symbolList    - List of stock quote symbols to be added to text field.
     */
    public void setSymbolsTextField(List<String> symbolList) {
        StringBuilder sb = new StringBuilder();
        m_symbolList = symbolList;

        for(String symbol : m_symbolList) {
            sb.append(symbol);
            sb.append(",");
        }

        sb.deleteCharAt(sb.lastIndexOf(","));
        //sb.toString().trim();
        //sb.deleteCharAt(sb.lastIndexOf(" "));
        m_quoteField.setText(sb.toString().trim());
        m_quoteField.grabFocus();
    }


    /**
     * Adds the argument List<StockQuote> into the StockTableModel.  The StockTableModel
     * inserts the data into the JTable and displays the JTable.
     * list 
     * @param stocks    - list of stock symbols to be added to table
     */
    public void displayStockQuoteList(List<StockQuote> stocks) {
        m_model.addStocks(stocks);
    }


    /**
     *  Empties the table of all stock quotes
     */
    public void clearStockList() {
        m_selectedStock = null;
        m_quoteField.setText("");
        m_model.deleteAllRows();
        m_model.fireTableDataChanged();
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