package com.stockticker.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class DetailCard extends JPanel {
    private static final long serialVersionUID = 1L;
    private JPanel m_detailCard;;
    private StockTable m_stocks;
    
    public DetailCard() {
        setCard();
    }

    public void setCard() {
        
        m_detailCard = new JPanel();
        m_detailCard.setPreferredSize(new Dimension(550, 520));
        m_stocks = new StockTable(true);
        m_detailCard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Details"));
        m_detailCard.add(m_stocks, BorderLayout.NORTH);
        m_detailCard.setToolTipText("Quote Screen");
    }


    public JPanel getCard() {
        return m_detailCard;
    }
}
