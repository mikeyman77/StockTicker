/**
 * GUI for Stock Ticker Portfolio Manager
 * J308 Project
 * Paul Wallace
 */

package com.stockticker.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
* J308
* @author prwallace
*/
public final class QuoteCard extends JPanel {
    private static final long serialVersionUID = 1L;
    private JPanel m_quoteCard;
    private StockTable m_stocks;


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
