/**
 * GUI for Stock Ticker Portfolio Manager
 * J308 Project
 * Paul Wallace
 */

package com.stockticker.ui;

import com.stockticker.StockQuote;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;

/**
* J308
* @author prwallace
*/
public final class QuoteCard extends JPanel {
    //private static final long serialVersionUID = 1L;
    //private static final String[] m_header = {"Volumn", "Day's Range", "Avg Vol(3m)", "52-Wk Range", "Prev Close",
                                                //"Market Cap", "Open", "P/E (ttm)", "Bid", "Ask" };

    private JPanel m_quoteCard;
    //private JTable m_table;
    //private JScrollPane m_scrollPane;
    //private final GridBagConstraints m_constraints;
    private StockTable m_stocks;
    
    //private QuoteTabeModel m_model;
    //private List<StockQuote> m_quote;


    /**
    *
    *
    */
    public QuoteCard() {
        setCard();
    }


    /**
    *
    *
    */
    public void setCard() {
        
        m_quoteCard = new JPanel();
        m_quoteCard.setPreferredSize(new Dimension(550, 520));
        m_stocks = new StockTable(true);
        m_quoteCard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Quote"));
        m_quoteCard.add(m_stocks, BorderLayout.NORTH);
        m_quoteCard.setToolTipText("Quote Screen");
    }


    /**
    *
    *
     * @return 
    */
    public JPanel getCard() {
        return m_quoteCard;
    }
}
