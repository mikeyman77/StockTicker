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
import com.stockticker.ui.IComponentsUI.UI;
import com.stockticker.StockQuote;
import com.stockticker.logic.BusinessLogicException;
import java.util.Arrays;


/**
 * Ticker screen for Stock Ticker Portfolio Manager
 * Provides a viewable list of stock quotes, selected from a list of stock symbols
 * @author prwallace
 */
public class TickerCard extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final String[] m_header = { "SYMBOL", "TIME", "PRICE", "CHG", "CHG%", "LOW", "HIGH", "VOLUME", "Tracked" };
    private static final String ENTER_PRESSED = "ENTER_RELEASED";
    private static final String DELETE_PRESSED = "DELETE_RELEASE";

    private static final int FIRST_ROW = 0;
    private static final int CLICK_COUNT = 2;

    private JPanel m_tablePanel;
    private JPanel m_tickerCard;
    private JPanel m_buttonPanel;
    private JPanel m_cardPanel;
    private CardLayout m_cardLayout;
    private GridBagConstraints m_constraints;

    private StockTableModel m_model;
    public JTable m_table;
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
     * Creates the Card JPanel for this UI screen and adds child JPanels for the
     * JTable, Quote button, and Quote text field.  Also creates the model for
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

                            try {
                                if(m_operate.getTrackingStatus(m_selectedStock.getSymbol())) {
                                    m_operate.resetLeftButton(UI.UNTRACK.getName()); 
                                }
                                else {
                                    m_operate.resetLeftButton(UI.TRACK.getName());
                                }
                            }
                            catch(BusinessLogicException ex) {
                                m_operate.showError(ex.getMessage());
                            }

                            m_operate.resetRightButton(UI.CLOSE.getName(), true);
                            m_operate.enableSymbolJList(false);
                            m_operate.enableHistoryButton(true);
                            m_operate.setControlBtnFocus(true);
                            m_cardLayout = (CardLayout) m_cardPanel.getLayout();
                            m_cardLayout.show(m_cardPanel, UI.QUOTE.getName());
                        }
                    }

                    setQuoteFieldFocus();
                }
            }
        });


        // Removes the mouse selected row from the table when the delete key is pressed
        AbstractAction enterAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String temp;
                int index = m_table.getSelectedRow();

                temp = m_model.getStock(index).getSymbol();
                if(m_operate.getTrackingStatus(temp)) {
                    m_operate.untrackStock(temp);
                }
                
                m_model.deleteRow(index);
               setQuoteFieldFocus();
            }
        };

        m_table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0, false), DELETE_PRESSED);
        m_table.getActionMap().put(DELETE_PRESSED, enterAction);        
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
     * Gets/returns the Card panel
     * 
     * @return  - the Card panel of this screen
     */
    public JPanel getCard() {
        return m_tickerCard;
    }


    
    /**
     * Gets each symbol field from each StockQuote entry listed in the table.  
     * Generate a List<String> of symbols and return this list.
     * 
     * @return  - the List<String> of symbols
     */
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
     * Gets each symbol listed in the quote text field.  This field contains a
     * String of Stock symbols, delimited by comma's. The String is entered from
     * the symbols JList or manually by user.  The String is parsed, validated,
     * and added to a List<String>. The resultant list is returned.
     *
     * @return  - a List<String> of Stock symbols
     */
    public List<String> getSymbolsTextField() {
        List<String> list = new ArrayList<>();
        List<String> symbols = new ArrayList<>();

        if(!m_quoteField.getText().isEmpty()) {
            list = Arrays.asList( m_quoteField.getText().split(m_splitChars));
            for(String str : list) {
                String tmp = str.trim().toUpperCase();
                    symbols.add(tmp);
            }
        }

        return symbols;
    }


    
    /**
     * Gets/returns the selected StockQuote from the table
     * 
     * @return  - the selected StockQuote
     */
    public StockQuote getSelectedStock() {
        return m_selectedStock;
    }


    /**
     * Sets the focus in the quote text field
     */
    public void setQuoteFieldFocus() {
        m_table.clearSelection();
        m_quoteField.grabFocus();
    }


    /**
     * Sets the quote text field with symbols from the argument List<String> and
     * delimits the symbols by commas.
     * 
     * @param symbolList    - List of Stock symbols
     */
    public void setSymbolsTextField(List<String> symbolList) {
        StringBuilder sb = new StringBuilder();
        m_symbolList = symbolList;

        for(String symbol : m_symbolList) {
            sb.append(symbol);
            sb.append(",");
        }

        sb.deleteCharAt(sb.lastIndexOf(","));
        m_quoteField.setText(sb.toString().trim());
        m_quoteField.grabFocus();
    }


    /**
     * Adds the List<StockQuote> into the tables model, which is then displayed
     * in the table.
     *
     * @param stocks    - list of stock symbols to be added to table
     */
    public void displayStockQuoteList(List<StockQuote> stocks) {
        m_model.addStocks(stocks);
    }


    /**
     *  Clears the table of all StockQuote's
     */
    public void clearStockList() {
        m_selectedStock = null;
        m_quoteField.setText("");
        m_model.deleteAllRows();
        m_model.fireTableDataChanged();
    }



    /**
     * Model for the Ticker JTable.  inner class StockTableModel extends Abstract-
     * TableModel.  Adds/displays/clear StockQuote entries to the table.
     */
    class StockTableModel extends AbstractTableModel {
        private List<StockQuote> m_stocks;
        private final String[] m_header;


        /**
         * Constructs the TickerTable object
         * 
         * @param header    - header for each column in table 
         */
        public StockTableModel(String[] header) {
            this.m_header = header;
            this.m_stocks = new ArrayList<>();
        }


        /**
         * Gets/return the header for each column in model
         * 
         * @param column    - index of column in model
         * @return  - the header at the specified column index
         */
        @Override
        public String getColumnName(int column) {
            return m_header[column];
        }


        /**
         * Gets/returns the total row count in model
         * 
         * @return  - total row count in model
         */
        @Override
        public int getRowCount() {
            return m_stocks.size();
        }


        /**
         * Gets/returns the total number of columns in model
         * 
         * @return  - the total number of columns in model
         */
        @Override
        public int getColumnCount() {
            return m_header.length;
        }

        
        /**
         * Gets each StockQuote field from the StockQuote model and inserts each
         * field into its appropriate place in the table.  Adds a plus or minus
         * sign in the Tracked column, to indicate the StockQuote is tracked or
         * not tracked.
         * 
         * @param rowIndex  - row location of this StockQuote entry 
         * @param columnIndex   - column location of this StockHistory entry
         * @return  - the StockQuote field at the specified index
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
                    System.err.println("Problems displaying Stock Quote's");
            }

            return value;
        }


        /**
         * Adds the List<StockQuote> to the table model
         * 
         * @param stock - StockQuote to be added to model
         */
        public void addStocks(List<StockQuote> stock) {
            m_stocks = stock;
            fireTableDataChanged();
        }


        /**
         * Gets/returns the StockQuote at the specified row
         * 
         * @param row- row location of StockQuote in model
         * @return 
         */
        public StockQuote getStock(int row) {
            return m_stocks.get(row);
        }


        /**
         * Deletes the row at the argument index from the model and table
         * 
         * @param row   - row index to be removed from model
         */
        public void deleteRow(int row) {
            m_stocks.remove(row);
            fireTableDataChanged();
        }


        /**
         * Deletes all rows in the model
         */
        public void deleteAllRows() {
            for(int i = this.getRowCount() - 1; i >= 0; i--) {
                m_stocks.remove(i);    
            }

            fireTableDataChanged();
        }
    }
}