package com.stockticker.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class StockTable extends JPanel {
    private final boolean DEBUG = false;

    public StockTable() {
        super(new GridLayout(1,0));

        String[] columnNames = {"First Name",
                "Last Name",
                "Sport",
                "# of Years",
        "Vegetarian"};

        Object[][] data = {
                {"Kathy", "Smith",
                    "Snowboarding", new Integer(5), new Boolean(false)},
                    {"John", "Doe",
                        "Rowing", new Integer(3), new Boolean(true)},
                        {"Sue", "Black",
                            "Knitting", new Integer(2), new Boolean(false)},
                            {"Jane", "White",
                                "Speed reading", new Integer(20), new Boolean(true)},
                                {"Joe", "Brown",
                                    "Pool", new Integer(10), new Boolean(false)}
        };

        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        if (DEBUG) {
            table.addMouseListener(new MouseAdapter() {
                @ Override
                public void mouseClicked(MouseEvent e) {
                    //printDebugData(table);
                }
            });
        }

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }
}