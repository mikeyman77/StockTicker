package com.stockticker.ui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
*
* @author prwallace
*/
public class StockTable extends JPanel {
	/**
     * 
     */
    private static final long serialVersionUID = -8372941733055803502L;

    public StockTable() {
        super(new GridLayout(1,0));

        String[] columnNames = {"SYMBOL","TIME","PRICE","CHG","CHG%", "LOW", "HIGH", "VOLUME"};

        Object[][] data = { {"", "","", "", "", "", "", ""}, {"", "","", "", "", "", "", ""}, 
                            {"", "","", "", "", "", "", ""}, {"", "","", "", "", "", "", ""},
                            {"", "","", "", "", "", "", ""}, {"", "","", "", "", "", "", ""}, 
                            {"", "","", "", "", "", "", ""}, {"", "","", "", "", "", "", ""},
                            {"", "","", "", "", "", "", ""}, {"", "","", "", "", "", "", ""}, 
                            {"", "","", "", "", "", "", ""}, {"", "","", "", "", "", "", ""},
                            {"", "","", "", "", "", "", ""}, {"", "","", "", "", "", "", ""}, 
                            {"", "","", "", "", "", "", ""}
        };

        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 240));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }
}