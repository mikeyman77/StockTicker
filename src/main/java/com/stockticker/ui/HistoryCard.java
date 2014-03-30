/**
 * GUI for Stock Ticker Portfolio Manager
 * 90.308-061 Agile Software Dev. w/Java Project
 * Paul Wallace
 */
package com.stockticker.ui;

import com.stockticker.StockHistory;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;

import java.util.ArrayList;
import java.util.List;

import com.stockticker.ui.ViewStockTicker.OperateStockTicker;
import com.stockticker.StockQuote;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SpinnerDateModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



/**
 * 
 * J308 Project
 * @author prwallace
 */
public class HistoryCard {
    private static final long serialVersionUID = 1L;
    private static final String[] m_header = { "DATE", "OPEN", "CLOSE", "ADJ CLOSE", "LOW", "HIGH", "VOLUME"};
    private static final String ENTER_PRESSED = "ENTER_RELEASED";

    private JPanel m_tablePanel;
    //private JPanel m_topPenel;
    private JPanel m_historyCard;
    private JPanel m_calendarPanel;
    private JPanel m_startDatePanel;
    private JPanel m_endDatePanel;
    //private JPanel m_namePanel;

    private JSpinner m_startDateSpinner;
    private JSpinner m_endDateSpinner;

    private JTable m_historyTable;
    private JScrollPane m_scrollPane;

    private HistoryTableModel m_historyModel;
    private SpinnerDateModel m_startDateModel;
    private SpinnerDateModel m_endDateModel;

    private final OperateStockTicker m_operate;
    //private StockHistory m_selectedStock;

    private GridBagConstraints m_constraints;
    private List<String> m_historyList;
    private String m_title;

    private Calendar m_calendar;

    private SimpleDateFormat m_date;


    /**
     * Constructs the HistoryCard object.
     *  
     * @param operate   - Instance of OperateStockTicker
     */
    public HistoryCard(OperateStockTicker operate) {
        //m_calendar = Calendar.getInstance();
        m_operate = operate;
        setCard();
    }


    /**
     * Adds a Card JPanel as main panel for this ui screen and a child JPanel for
     * the JTable.
     */
    public final void setCard() {
        m_historyModel = new HistoryTableModel(m_header);
        m_historyCard = new JPanel();

        m_calendarPanel = new JPanel();
        m_calendarPanel.setPreferredSize(new java.awt.Dimension(550, 100));

        this.setStartDateCalendar();
        this.setEndDateCalendar();
        m_historyCard.add(m_calendarPanel, BorderLayout.NORTH);

        this.setHistoryTable();
        m_historyCard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "History"));
        m_historyCard.add(m_tablePanel, BorderLayout.SOUTH);
    }


    /**
     * Adds the stock history table to its JPanel and provides an action listener for
     * the selected rows.
     */
    public void setHistoryTable() {
        m_tablePanel = new JPanel(new GridLayout(1, 0, 0, 0));
        m_tablePanel.setOpaque(true);
        m_tablePanel.setPreferredSize(new Dimension(630, 280));

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
     * Initializes and layout the spinner calendar fields.
     */
    private void setStartDateCalendar() {
        m_startDateModel = new SpinnerDateModel();// may need to initial with a Calendar Instance, see CalendarSpinner
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

        // Pressed the "Submit" button if enter is depressed while the start date spinner
        // has the focus.
        Action startDateAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                m_operate.getLeftControlBtn().doClick();
            }
        };

        m_startDateSpinner.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), ENTER_PRESSED);
        m_startDateSpinner.getActionMap().put(ENTER_PRESSED, startDateAction);

        // Layout the calendar spinner on its panel
        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.ipadx = 13;
        m_constraints.ipady = 1;
        m_constraints.anchor = GridBagConstraints.NORTHWEST;
        m_constraints.insets = new Insets(10, 10, 0, 10);
        m_startDatePanel.add(m_startDateSpinner, m_constraints);
        
        JLabel dateFormat = new JLabel("MM DD YYYY");
        dateFormat.setFont(new Font("SansSerif", 0, 10));
        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 1;
        m_constraints.anchor = GridBagConstraints.NORTH;
        m_constraints.insets = new Insets(0, 0, 15, 10);
        m_startDatePanel.add(dateFormat, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.anchor = GridBagConstraints.NORTHWEST;
        m_constraints.insets = new Insets(0, 0, 0, 10);
        m_calendarPanel.add(m_startDatePanel, m_constraints);
    }


    /**
     * Initializes and layout the spinner calendar fields.
     */
    private void setEndDateCalendar() {
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

        // Pressed the "Submit" button if enter is depressed while the end date spinner
        // has the focus.
        Action endDateAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                m_operate.getLeftControlBtn().doClick();
            }
        };

        m_endDateSpinner.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), ENTER_PRESSED);
        m_endDateSpinner.getActionMap().put(ENTER_PRESSED, endDateAction);

        // Layout the calendar spinner on its panel
        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.ipadx = 13;//13
        m_constraints.ipady = 1;
        m_constraints.anchor = GridBagConstraints.NORTHWEST;
        m_constraints.insets = new Insets(10, 10, 0, 10);
        m_endDatePanel.add(m_endDateSpinner, m_constraints);
        
        JLabel dateFormat = new JLabel("MM DD YYYY");
        dateFormat.setFont(new Font("SansSerif", 0, 10));
        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 1;
        m_constraints.anchor = GridBagConstraints.NORTH;
        m_constraints.insets = new Insets(0, 0, 15, 10);
        m_endDatePanel.add(dateFormat, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 1;
        m_calendarPanel.add(m_endDatePanel, m_constraints);
    }


    /**
     * Gets/returns this Card JPanel
     * @return 
     */
    public JPanel getCard() {
        return m_historyCard;
    }


    public void displayHistory(List<StockHistory> history) {
        m_historyModel.addStocks(history);
    }

    public Date getStartDate() {
        return m_startDateModel.getDate();
    }


    public Date getEndDate() {
        return m_endDateModel.getDate();
    }


    /**
     * Clears the Tables of their content
     * @param disable
     */
    public void clearHistory() {
        m_historyModel.deleteAllRows();
        m_historyModel.fireTableDataChanged();
        m_startDateSpinner.setValue(new Date());
        m_endDateSpinner.setValue(new Date());
    }



    /**
     * Model for the StockQuote JTable.  Handles adding, inserting, displaying, and
     * deleting day in table.
     */
    class HistoryTableModel extends AbstractTableModel {
        private List<StockHistory> m_stockHistory;
        private final String[] m_header;


        /*
         * Constructor for StockTableModel.  Initializes header String[] and List<StockQuote>
         * fields.  Displays the JTable with header.
         */
        public HistoryTableModel(String[] header) {
            this.m_header = header;
            this.m_stockHistory = new ArrayList<>();
        }


        /**
         * Gets/returns each column name from the header String[].
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


        /*
         * Gets the individual StockQuote fields within the List<StockQuotes> and 
         * inserts them into their appropriate places in the table.  Adds a plus or
         * minus sign to the Tracked column; "+" for tracking "-" for not tracking.
         * 
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
         * Adds the list of StockQuote's to the table models List<StockQuote>
         */
        public void addStocks(List<StockHistory> history) {
            m_stockHistory = history;
            fireTableDataChanged();
        }


        /*
         * Gets/returns the StockQuote at the specified row
         */
        public StockHistory getStock(int row) {
            return m_stockHistory.get(row);
        }


        /*
         * Deletes all rows in the table
         */
        public void deleteAllRows() {
            for(int i = this.getRowCount() - 1; i >= 0; i--) {
                m_stockHistory.remove(i);
            }
        }
    }
}
