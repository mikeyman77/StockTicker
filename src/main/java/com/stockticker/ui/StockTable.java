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

    // Temporary argument, so table can be used as a place holder in different screens
    public StockTable(boolean hasHeader) {
        super(new GridLayout(1,0));

        if(hasHeader) {
            String[] tableHeader = {"SYMBOL","TIME","PRICE","CHG","CHG%", "LOW", "HIGH", "VOLUME"};
        } else {
            String[] tableHeader = {"","","","","", "", "", ""};
        }
           
String[] tableHeader = {"","","","","", "", "", ""};
        Object[][] data = { {"", "","", "", "", "", "", ""}, {"", "","", "", "", "", "", ""}, 
                            {"", "","", "", "", "", "", ""}, {"", "","", "", "", "", "", ""},
                            {"", "","", "", "", "", "", ""}, {"", "","", "", "", "", "", ""}, 
                            {"", "","", "", "", "", "", ""}, {"", "","", "", "", "", "", ""},
                            {"", "","", "", "", "", "", ""}, {"", "","", "", "", "", "", ""}, 
                            {"", "","", "", "", "", "", ""}, {"", "","", "", "", "", "", ""},
                            {"", "","", "", "", "", "", ""}, {"", "","", "", "", "", "", ""}, 
                            {"", "","", "", "", "", "", ""}};

        final JTable stockTable = new JTable(data, tableHeader);
        stockTable.setPreferredScrollableViewportSize(new Dimension(500, 240));
        stockTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(stockTable);
        add(scrollPane);
    }
}