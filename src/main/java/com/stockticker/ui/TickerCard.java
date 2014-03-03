/**
 * 
 */
package com.stockticker.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
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

import com.stockticker.ui.IStockTicker_UIComponents.UI;

/**
 * @author prwallace
 * 
 */
public class TickerCard extends JPanel {
    private static final long serialVersionUID = 1L;
    private JPanel m_stockPanel;
    private JPanel m_tickerCard;
    private JPanel m_buttonPanel;
    private JPanel m_cardPanel;

    private CardLayout m_cardLayout;
    //private StockTable m_stocks;
    private GridBagConstraints m_constraints;


    public TickerCard(JPanel cards) {
        m_constraints = new GridBagConstraints();
        setCard();
        m_cardPanel = cards;
    }


    public final JPanel setCard() {
        m_tickerCard = new JPanel();
        m_tickerCard.setPreferredSize(new Dimension(550, 520));

        //m_stocks = new StockTable(true);
        setStockTable();
        //m_stocks.setOpaque(true);
        m_tickerCard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Stocks"));
        //m_tickerCard.add(m_stocks);
        m_tickerCard.add(m_stockPanel);

        setButtonPanel();
        m_tickerCard.add(m_buttonPanel, BorderLayout.SOUTH);

        return m_tickerCard;
    }


    public void setStockTable() {
        m_stockPanel = new JPanel(new GridLayout(1, 0));
        m_stockPanel.setOpaque(true);

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
        
        table.setRowSelectionAllowed(true);
        ListSelectionModel rowSelection = table.getSelectionModel();
        rowSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rowSelection.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                System.out.println("Selection in table");
            }
        });
    }


    public void setButtonPanel() {
        m_buttonPanel = new JPanel(new GridBagLayout());
        m_buttonPanel.setPreferredSize(new Dimension(300, 100));

        JButton quote = new JButton("Quote");
        quote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                m_cardLayout = (CardLayout) m_cardPanel.getLayout();
                m_cardLayout.show(m_cardPanel, UI.QUOTE.getName());
            }
        });
        final JTextField quoteField = new JTextField(40);
        quoteField.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent evt) {
                 String symbol = quoteField.getText();
                 System.out.println(symbol);
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

    public JPanel getCard() {
        return m_tickerCard;
    }
    
    public void setCardLayout(JPanel panel) {
        m_cardPanel = panel;
    }

}
