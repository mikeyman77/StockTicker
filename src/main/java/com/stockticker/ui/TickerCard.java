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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import com.stockticker.ui.IStockTicker_UIComponents.UI;
import com.stockticker.StockQuote;


/**
 * 
 * J308 Project
 * @author prwallace
 */
public class TickerCard extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final String[] m_header = { "SYMBOL", "TIME", "PRICE", "CHG", "CHG%", "LOW", "HIGH", "VOLUME" };

    private JPanel m_tablePanel;
    private JPanel m_tickerCard;
    private JPanel m_buttonPanel;
    private JPanel m_cardPanel;
    private CardLayout m_cardLayout;
    //private ViewStockTicker m_main;
    private final GridBagConstraints m_constraints;

    private JTable m_table;
    private JScrollPane m_scrollPane;

    private StockTableModel m_model;
    //private List<StockQuote> m_stocks;
    StockQuote m_selectedStock;

    private String m_symbol;
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
    //public TickerCard(JPanel cards) {
    public TickerCard(JPanel cards, QuoteCard quoteCard) {
        m_constraints = new GridBagConstraints();
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
        m_tickerCard.setPreferredSize(new Dimension(550, 520));

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
        m_table.setPreferredScrollableViewportSize(new Dimension(500, 240));
        m_table.setFillsViewportHeight(true);

        m_scrollPane = new JScrollPane(m_table);
        m_tablePanel.add(m_scrollPane);

        m_table.setRowSelectionAllowed(true);
        final ListSelectionModel rowSelection = m_table.getSelectionModel();
        rowSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rowSelection.addListSelectionListener(new ListSelectionListener() {
            
            int eventCount = 0;
            @Override
            public void valueChanged(ListSelectionEvent evt) {
                
                System.out.println("row selected = " + m_table.getSelectedRow());
                if(m_table.getSelectedRow() >= 0 && evt.getValueIsAdjusting()) {
                    m_quoteIndex = m_table.getSelectedRow();
                    m_selectedStock = m_model.getStock(m_quoteIndex);
                    System.out.println("row = " + m_quoteIndex);
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
                if(m_selectedStock != null) { //Insure there is a quote to display
                    if(m_isLoggedIn) {                      
                        m_quoteCard.displayStockQuote(m_selectedStock, 0, true);
                        m_cardLayout = (CardLayout) m_cardPanel.getLayout();
                        m_cardLayout.show(m_cardPanel, UI.QUOTE.getName());
                        System.out.println("switching to QuoteCard");
                    }
                    else {
                        System.out.println("No Quotes to display");
                    }
                }
                else {
                    System.out.println("Stock quote list is empty");
                }
            }
        });

        final JTextField quoteField = new JTextField(40);
        quoteField.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent evt) {
                 m_symbol = quoteField.getText();
                 System.out.println(m_symbol);
             }
        });


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
        m_constraints.insets = new Insets(0, 80, 0, 10);
        m_buttonPanel.add(quoteField, m_constraints);
    }


    /**
     * 
     * @return 
     */
    public JPanel getCard() {
        return m_tickerCard;
    }


    /**
     * Sets the CardLayout from the argument JPanel to the main card JPanel of this
     * class.
     * @param panel     - sets panel to main JPanel of this class.
     */
    public void setCardLayout(JPanel panel) {
        m_cardPanel = panel;
    }


    /**
     *
     * @param stocks
     * @param enable
     */
    public void displayStockQuoteList(List<StockQuote> stocks, boolean enable) {
        m_isLoggedIn = enable;
        m_model.addStocks(stocks);
    }


    public void clearStockList(boolean disable) {
        m_isLoggedIn = disable;
        m_selectedStock = null;
        m_model.deleteRow();
    }
            
}


/**
 *
 */
class StockTableModel extends AbstractTableModel {
    private List<StockQuote> m_stocks;
    private final String[] m_header;


    public StockTableModel(String[] header) {
        this.m_header = header;
        this.m_stocks = new ArrayList<>();
    }


    @Override
    public String getColumnName(int column) {
        return m_header[column];
    }


    @Override
    public int getRowCount() {
        return m_stocks.size();
    }


    @Override
    public int getColumnCount() {
        return m_header.length;
    }


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
            default:
                System.err.println("Problems displaying Stock Quotes");
        }

        return value;

    }


    public void addStocks(List<StockQuote> stock) {
        m_stocks = stock;
        fireTableDataChanged();
    }


    public void addStockFields(StockQuote stock, int index) {
        m_stocks.add(index, stock);
        //fireTableRowsInserted(index, index);
    }


    public void removeStock(StockQuote stock) {
        int index = m_stocks.indexOf(stock);
        m_stocks.remove(index);
        System.out.println("test");
        //fireTableRowsInserted(index, index);
    }


    //public Object getStock(int row) {
    public StockQuote getStock(int row) {
            return m_stocks.get(row);
    }

    public void deleteRow() {
        for(int i = m_stocks.size() - 1; i >= 0; i--) {
            m_stocks.remove(i);
        }
    }
}