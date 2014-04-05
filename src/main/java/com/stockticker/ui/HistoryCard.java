/**
 * GUI for Stock Ticker Portfolio Manager
 * 90.308-061 Agile Software Dev. w/Java Project
 * Paul Wallace
 */
package com.stockticker.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.SpinnerDateModel;
import javax.swing.border.TitledBorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

import com.stockticker.ui.ViewStockTicker.OperateStockTicker;
import com.stockticker.StockHistory;


/**
 * History Screen for Stock Ticker Portfolio Manager
 * Provides StockHistory on a selected Stock between a specified date range.
 * Displays the details in a JTable.
 * @author prwallace
 */
public class HistoryCard {
    private static final long serialVersionUID = 1L;
    private static final String[] m_header = { "DATE", "OPEN", "CLOSE", "ADJ CLOSE", "LOW", "HIGH", "VOLUME"};
    private static final String ENTER_PRESSED = "ENTER_RELEASED";

    private JPanel m_tablePanel;
    private JPanel m_historyCard;
    private JPanel m_spinnerPanel;
    private JPanel m_startDatePanel;
    private JPanel m_endDatePanel;

    private JSpinner m_startDateSpinner;
    private JSpinner m_endDateSpinner;

    private JTable m_historyTable;
    private JScrollPane m_scrollPane;

    private HistoryTableModel m_historyModel;
    private SpinnerDateModel m_startDateModel;
    private SpinnerDateModel m_endDateModel;

    private final OperateStockTicker m_operate;
    private GridBagConstraints m_constraints;


    /**
     * Constructs the HistoryCard object.
     *  
     * @param operate   - Instance of OperateStockTicker class
     */
    public HistoryCard(OperateStockTicker operate) {
        m_operate = operate;
        setCard();
    }


    /**
     * Adds the Card JPanel as main panel for this UI screen and adds its child
     * JPanel's to this Card.  Makes method calls to build its child components.
     */
    public final void setCard() {
        m_historyModel = new HistoryTableModel(m_header);
        m_historyCard = new JPanel();

        m_spinnerPanel = new JPanel();
        m_spinnerPanel.setPreferredSize(new java.awt.Dimension(550, 100));

        this.setStartDateSpinner();
        this.setEndDateSpinner();
        m_historyCard.add(m_spinnerPanel, BorderLayout.NORTH);

        this.setHistoryTable();
        m_historyCard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "History"));
        m_historyCard.add(m_tablePanel, BorderLayout.SOUTH);
    }


    /**
     * Adds the stock history table to its JPanel
     */
    public void setHistoryTable() {
        m_tablePanel = new JPanel(new GridLayout(1, 0, 0, 0));
        m_tablePanel.setOpaque(true);
        m_tablePanel.setPreferredSize(new Dimension(630, 280));
        m_tablePanel.setBorder(BorderFactory.createTitledBorder(null, "History2", TitledBorder.ABOVE_TOP, TitledBorder.CENTER,
                                    new Font("Sansserif", 0, 12), new Color(0, 0, 0)));

        m_historyTable = new JTable(m_historyModel);
        m_historyTable.setPreferredScrollableViewportSize(new Dimension(630, 200));
        m_historyTable.setFillsViewportHeight(true);
        m_historyTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        m_historyTable.getTableHeader().setFont(new Font("SansSerif", Font.PLAIN, 12));
        m_historyTable.setFont(m_historyTable.getFont().deriveFont(Font.PLAIN, 11));
        m_historyTable.setEnabled(false);

        m_scrollPane = new JScrollPane(m_historyTable);
        m_tablePanel.add(m_scrollPane);
    }


    /**
     * Creates/initializes a date spinner component that represents the start date
     * for the StockHistory date range.  Creates a JPanel and layouts the spinner
     * onto the panel.  Provides an editor to mask the Date format for the spinner.
     */
    private void setStartDateSpinner() {
        m_startDateModel = new SpinnerDateModel();
        m_startDateModel.setCalendarField(Calendar.DAY_OF_YEAR);

        m_startDateSpinner = new JSpinner(m_startDateModel);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(m_startDateSpinner, "MM dd yyyy");
        m_startDateSpinner.setEditor(editor);
        editor.getTextField().setEditable(false);

        m_startDateSpinner.setBorder(BorderFactory.createEtchedBorder());
        m_startDateSpinner.setInheritsPopupMenu(true);
        m_startDateSpinner.setToolTipText("Double click on a field to select");

        m_startDatePanel = new JPanel(new GridBagLayout());
        m_startDatePanel.setPreferredSize(new Dimension(141, 75));
        m_startDatePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(),
                                        BorderFactory.createTitledBorder(null, "Start Date", TitledBorder.CENTER,
                                        TitledBorder.TOP, new Font("SansSerif", 0, 10), new Color(0, 0, 0))));

        // Listens for the enter button when start date spinner has focus.  Clicks 
        // the "Submit" button(left control button) in ViewStockTicker screen.
        Action startDateAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                m_operate.getLeftControlBtn().doClick();
            }
        };

        m_startDateSpinner.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), ENTER_PRESSED);
        m_startDateSpinner.getActionMap().put(ENTER_PRESSED, startDateAction);

        // Layout the spinner on its panel
        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.ipadx = 13;
        m_constraints.ipady = 1;
        m_constraints.anchor = GridBagConstraints.NORTHWEST;
        m_constraints.insets = new Insets(10, 10, 0, 10);
        m_startDatePanel.add(m_startDateSpinner, m_constraints);

        // Add/layout label on the spinner panel
        JLabel dateFormat = new JLabel("MM DD YYYY");
        dateFormat.setFont(new Font("SansSerif", 0, 10));
        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 1;
        m_constraints.anchor = GridBagConstraints.NORTH;
        m_constraints.insets = new Insets(0, 0, 15, 10);
        m_startDatePanel.add(dateFormat, m_constraints);

        // Add the spinner's panel to its parent panel
        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.anchor = GridBagConstraints.NORTHWEST;
        m_constraints.insets = new Insets(0, 0, 0, 10);
        m_spinnerPanel.add(m_startDatePanel, m_constraints);
    }


    /**
     * Creates/initializes a date spinner component that represents the end date
     * for the StockHistory date range.  Creates a JPanel and layouts the spinner
     * onto the panel.  Provides an editor to mask the Date format for the spinner.
     */
    private void setEndDateSpinner() {
        m_endDateModel = new SpinnerDateModel();
        m_endDateModel.setCalendarField(Calendar.DAY_OF_YEAR);

        m_endDateSpinner = new JSpinner(m_endDateModel);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(m_endDateSpinner, "MM dd yyyy");
        m_endDateSpinner.setEditor(editor);
        editor.getTextField().setEditable(false);

        m_endDateSpinner.setBorder(BorderFactory.createEtchedBorder());
        m_endDateSpinner.setInheritsPopupMenu(true);
        m_endDateSpinner.setToolTipText("Double click in field to select month, day, or year");

        m_endDatePanel = new JPanel(new GridBagLayout());
        m_endDatePanel.setPreferredSize(new Dimension(141, 75));
        m_endDatePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(),
                                        BorderFactory.createTitledBorder(null, "End Date", TitledBorder.CENTER,
                                        TitledBorder.TOP, new Font("SansSerif", 0, 10), new Color(0, 0, 0))));

        // Listens for the enter button when end date spinner has focus.  Clicks 
        // the "Submit" button in ViewStockTicker screen.
        Action endDateAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                m_operate.getLeftControlBtn().doClick();
            }
        };

        m_endDateSpinner.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), ENTER_PRESSED);
        m_endDateSpinner.getActionMap().put(ENTER_PRESSED, endDateAction);

        // Layout the spinner on its panel
        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.ipadx = 13;
        m_constraints.ipady = 1;
        m_constraints.anchor = GridBagConstraints.NORTHWEST;
        m_constraints.insets = new Insets(10, 10, 0, 10);
        m_endDatePanel.add(m_endDateSpinner, m_constraints);

        // Add/layout label on the spinner panel
        JLabel dateFormat = new JLabel("MM DD YYYY");
        dateFormat.setFont(new Font("SansSerif", 0, 10));
        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 1;
        m_constraints.anchor = GridBagConstraints.NORTH;
        m_constraints.insets = new Insets(0, 0, 15, 10);
        m_endDatePanel.add(dateFormat, m_constraints);

        // Add the spinners panel to its parent panel
        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 1;
        m_spinnerPanel.add(m_endDatePanel, m_constraints);
    }


    /**
     * Gets/returns the Card panel
     * 
     * @return  - the Card panel of this screen
     */
    public JPanel getCard() {
        return m_historyCard;
    }


    /**
     * Adds the List<StockHistory> into the tables model, which is then displayed
     * in the table.
     * 
     * @param history   - List of StockHistory of a stock during a specific date range
     */
    public void displayHistory(List<StockHistory> history) {
        m_historyModel.addStocks(history);
    }


    /**
     * Gets/returns the start Date of the date range set by the user.
     * 
     * @return  - the start Date
     */
    public Date getStartDate() {
        return m_startDateModel.getDate();
    }


    /**
     * Gets/returns the end Date of the date range set by user.
     * 
     * @return  - the end Date
     */
    public Date getEndDate() {
        return m_endDateModel.getDate();
    }


    /**
     * Adds a title border around the table, the title is set to the argument String.
     * 
     * @param title - Title of the table
     */
    public void setTableBorder(String title) {
        m_tablePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title, TitledBorder.ABOVE_TOP,
                                                            TitledBorder.CENTER, new Font("Sansserif", 0, 12), new Color(0, 0, 0)));
    }


    /**
     * Clears the Table of its content
     */
    public void clearHistory() {
        m_historyModel.deleteAllRows();
        m_historyModel.fireTableDataChanged();
        m_startDateSpinner.setValue(new Date());
        m_endDateSpinner.setValue(new Date());
    }



    /**
     * Model for the History JTable.  inner class HistoryTableModel extends Abstract-
     * TableModel.  Adds/displays/clear StockHistory entries to the table.
     */
    class HistoryTableModel extends AbstractTableModel {
        private List<StockHistory> m_stockHistory;
        private final String[] m_header;


        /**
         * Constructs the HistoryTableModel object
         * 
         * @param header    - header for each column in table
         */
        public HistoryTableModel(String[] header) {
            this.m_header = header;
            this.m_stockHistory = new ArrayList<>();
        }


        /**
         * Gets/returns column name located at the argument index.
         */
        @Override
        public String getColumnName(int column) {
            return m_header[column];
        }


        /*
         * Gets/returns the row count of JTable
         */
        @Override
        public int getRowCount() {
            return m_stockHistory.size();
        }


        /*
         * Gets/returns the column count of JTable
         */
        @Override
        public int getColumnCount() {
            return m_header.length;
        }

        
        /**
         * Gets each StockHistory field from the StockHistory model and inserts
         * each field into its appropriate place in the table.
         * 
         * @param rowIndex  - row location of this StockHistory entry
         * @param columnIndex   - column location of this StockHistory entry
         * @return  - the StockHistory field at the specified index
         */
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            StockHistory history = (StockHistory)m_stockHistory.get(rowIndex);
            Object value = null;

            switch(columnIndex) {
                case 0:
                    value = history.getDate();
                    break;
                case 1:
                    value = history.getOpen();
                    break;
                case 2:
                    value = history.getClose();
                    break;
                case 3:
                    value = history.getAdjClose();
                    break;
                case 4:
                    value = history.getLow();
                case 5:
                    value = history.getHigh();
                    break;
                case 6:
                    value = history.getVolume();
                    break;
                default:
                    System.err.println("Problems displaying Stock Quotes");
            }

            return value;
        }


        /*
         * Adds a range of StockHistory objects to the model
         */
        public void addStocks(List<StockHistory> history) {
            m_stockHistory = history;
            fireTableDataChanged();
        }


        /*
         * Gets/returns the StockHistory at the specified row
         */
        public StockHistory getStock(int row) {
            return m_stockHistory.get(row);
        }


        /*
         * Deletes all StockHistory from the table and the model
         */
        public void deleteAllRows() {
            for(int i = this.getRowCount() - 1; i >= 0; i--) {
                m_stockHistory.remove(i);
            }
        }
    }
}
