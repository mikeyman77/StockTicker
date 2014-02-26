/**
 * 
 */
package com.stockticker.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


/**
 * @author prwallace
 * 
 */
public class TickerCard {
    private StockTable m_stocks;
    private JPanel m_stockPanel;
    private JPanel m_tickerCard;
    private JPanel m_buttonPanel;
    private GridBagConstraints m_constraints;

    public TickerCard() {
        m_constraints = new GridBagConstraints();
        setCard();
    }


    public final JPanel setCard() {
        m_tickerCard = new JPanel();
        m_tickerCard.setPreferredSize(new Dimension(550, 520));

        m_stocks = new StockTable();
        m_stocks.setOpaque(true);
        m_tickerCard.add(m_stocks);

        setButtonPanel();
        m_tickerCard.add(m_buttonPanel, BorderLayout.SOUTH);

        return m_tickerCard;
    }


    public void setStockTable() {
        m_stockPanel = new JPanel(new GridLayout(1, 0));

        String[] columnNames = { "SYMBOL", "TIME", "PRICE", "CHG", "CHG%",
                "LOW", "HIGH", "VOLUME" };

        Object[][] data = { { "", "", "", "", "", "", "", "" },
                { "", "", "", "", "", "", "", "" },
                { "", "", "", "", "", "", "", "" },
                { "", "", "", "", "", "", "", "" },
                { "", "", "", "", "", "", "", "" },
                { "", "", "", "", "", "", "", "" },
                { "", "", "", "", "", "", "", "" },
                { "", "", "", "", "", "", "", "" },
                { "", "", "", "", "", "", "", "" },
                { "", "", "", "", "", "", "", "" },
                { "", "", "", "", "", "", "", "" },
                { "", "", "", "", "", "", "", "" },
                { "", "", "", "", "", "", "", "" },
                { "", "", "", "", "", "", "", "" },
                { "", "", "", "", "", "", "", "" } };

        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 240));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);

        m_stockPanel.add(scrollPane);
    }


    public void setButtonPanel() {
        m_buttonPanel = new JPanel(new GridBagLayout());
        m_buttonPanel.setPreferredSize(new Dimension(300, 100));

        JButton quote = new JButton("Quote");
        JTextField quoteField = new JTextField(40);

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

    public JPanel getCard() {
        return m_tickerCard;
    }

}
