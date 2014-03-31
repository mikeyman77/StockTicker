/**
 * GUI for Stock Ticker Portfolio Manager
 * 90.308-061 Agile Software Dev. w/Java Project
 * ViewStockTicker.java
 * Paul Wallace
 */
package com.stockticker.ui;

import com.stockticker.StockHistory;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

import com.stockticker.StockQuote;
import com.stockticker.SymbolMap;
import com.stockticker.UserInfo;
import com.stockticker.logic.AuthorizationService;
import com.stockticker.logic.StockTicker;
import com.stockticker.logic.StockTickerService;
import com.stockticker.logic.UserAuthorization;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 * GUI for Stock Ticker Portfolio Manager api
 * 
 * @author prwallace
 */
public class ViewStockTicker extends WindowAdapter implements IStockTicker_UIComponents {
    private static final String ENTER_PRESSED = "ENTER_RELEASED";
    private static final String SPACE = " ";

    private static final int ROW_OFFSET = 7;
    private static final int ZERO = 0;

    private static CardLayout cardLayout;
    private static JPanel m_cardPanel;

    private final JFrame m_frame;
    private JPanel m_bottomPanel;
    private JPanel m_leftPanel;
    private JPanel m_topPanel;
    private JPanel m_rightPanel;
    private JPanel m_componentPanel;

    private JList<Object> m_symbolJList;
    private JScrollPane m_scrollPane;

    public JButton m_leftControlBtn;
    public JButton m_rightControlBtn;
    public JButton m_historyBtn;
    private JTextField m_symbolField;

    private final Toolkit toolKit = Toolkit.getDefaultToolkit();
    private final Dimension screenSize = toolKit.getScreenSize();
    private GridBagConstraints m_constraints;
    private Image m_titleIcon;

    private HomeCard m_homeCard;
    //private DetailCard m_detailCard;
    private QuoteCard m_quoteCard;
    private TickerCard m_tickerCard;
    private RegistrationCard m_regCard;
    private LoginCard m_loginCard;
    private HistoryCard m_historyCard;  

    private boolean m_logInSelect = false;
    private boolean m_regSelect = false;
    public boolean m_closeSelect = false;
    public boolean m_isLoggedIn = false;
    public boolean m_isMultSelect = false;
    public boolean m_historySelect = false;
    public boolean[] m_isInvalidInput = new boolean[5];

    private String m_username = "";
    private String m_password = "";
    private String m_verifyPasswd = "";
    private String m_firstname = "";
    private String m_lastname = "";
    private final String m_icon = "./images/stock_ticker.png";

    private Date m_startDate;
    private Date m_endDate;

    private StringBuilder m_message;

    private List<String> m_symbolList;
    private List<String> m_multSymbols;
    private List<StockQuote> m_stockQuoteList;
    private List<StockHistory> m_stockHistoryList;

    private final AuthorizationService m_userAuth = UserAuthorization.INSTANCE;
    private final StockTickerService m_stockService =  StockTicker.INSTANCE;
    private UserInfo m_userInfo;

    private final OperateStockTicker m_operate;
    private int m_clickCntr = 0;


    /**
     * Constructor for theViewStockTicker class
     */
    public ViewStockTicker() {
        m_symbolList = new ArrayList<>();
        m_stockQuoteList = new ArrayList<>();
        m_multSymbols = new ArrayList<>();
        m_stockHistoryList = new ArrayList<>();
        m_frame = new JFrame();
        m_operate = new OperateStockTicker();
        m_frame.pack();
    }


    /**
     * Builds the main frame and provides a Window Listener close events from
     * the Title Bar.
     */
    public void build() {
        m_titleIcon = new ImageIcon(toolKit.getImage(m_icon)).getImage();
        m_frame.setIconImage(m_titleIcon);
        m_frame.setSize(940, 600);
        m_frame.setLocation(screenSize.width / 4, screenSize.height / 4);
        JFrame.setDefaultLookAndFeelDecorated(true);
        m_constraints = new GridBagConstraints();

        m_frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if(m_isLoggedIn ) {
                    m_userAuth.logOut(m_username);
                }

                m_frame.dispose();
                System.exit(0);
            }
        });
    }


    /**
     * Create/add the main panel of the UI and its child JPanel's. Create/add
     * the individual controls for the child JPanels and their ActionListener's.
     * Create/add CardLayout and it's individual cards and controls.
     */
    public void setPanels() {

        // Create the individual JPanels that make up the main screen
        m_bottomPanel = new JPanel();
        m_bottomPanel.setPreferredSize(new Dimension(700, 80));

        m_leftPanel = new JPanel();
        m_leftPanel.setPreferredSize(new Dimension(120, 520));

        m_topPanel = new JPanel();
        m_topPanel.setPreferredSize(new Dimension(700, 60));

        m_rightPanel = new JPanel();
        m_rightPanel.setPreferredSize(new Dimension(120, 520));

        // Main JPanel for the CardLayout.  Positioned in center of UI.
        m_cardPanel = new JPanel(new CardLayout());
        m_cardPanel.setLayout(cardLayout = new CardLayout());
        m_cardPanel.setPreferredSize(new Dimension(550, 520));
        m_cardPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));

        // Create the symbols JList, its scroll pane, and a symbols text field
        m_symbolJList = new JList<>(SymbolMap.getSymbols().keySet().toArray());
        m_symbolJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        m_symbolJList.setVisibleRowCount(8);
        m_symbolJList.setEnabled(false);
        m_symbolJList.setVisible(false);

        m_scrollPane = new JScrollPane(m_symbolJList);
        m_scrollPane.setSize(150, 20);
        m_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        m_scrollPane.setEnabled(false);
        m_scrollPane.setVisible(false);

        m_symbolField = new JTextField(6);
        m_symbolField.setEditable(false);
        m_symbolField.setVisible(false);


        // Listens for mouse selections in list and set multiple selection flag to true.
        // When typing in a symbol, the listener sets the flag to false.
        m_symbolJList.addListSelectionListener(new ListSelectionListener() { 
            @Override
            public void valueChanged(ListSelectionEvent evt) {
               boolean isAdjusting = evt.getValueIsAdjusting();
               if(!isAdjusting) {
                   m_isMultSelect = false;
               }
               else {
                   m_isMultSelect = true;
               }
            }
                    
        });


        // Mouse listener that listens for single or double clicks within the stock
        // symbols list.  A double click selects the one symbol and a single click
        // selects a group of symbols by using cntrl or shift keys.
        m_symbolJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String symbol = null;
                if(e.getClickCount() == 2) {
                    symbol = m_symbolJList.getSelectedValue().toString();
                    m_isMultSelect = false;

                    if(symbol == null) {
                        System.out.println("Unable to select symbol from list");
                    }
                    else {
                        m_symbolList.add(symbol);
                        m_operate.setSymbolList();
                        m_symbolJList.clearSelection();
                    }
                    
                    m_clickCntr = 0;
                }
                else { // User is making multiple mouse selections in list
                    if(e.getClickCount() == 1) {
                        m_clickCntr++;
                        if(m_clickCntr > 1) {
                            m_multSymbols.clear();

                            for(Object obj : m_symbolJList.getSelectedValuesList()) {
                                m_multSymbols.add(obj.toString());
                            }

                            m_symbolField.setText(m_symbolJList.getSelectedValue().toString());
                            m_isMultSelect = true;
                        }
                        else {
                            String selection = m_symbolJList.getSelectedValue().toString();
                            if(selection != null) {
                                m_symbolField.setText(selection);
                            }        
                        }
                    }
                }
            }
        });


        // Key listener that parses the stock symbol lists for symbols that match
        // or a close match, to the user input.  User single cliks a symbol to get
        // focus, then can begin typing to find a specific symbol.
        m_symbolField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                String userInput = null;

                int row_pose = m_symbolJList.getSelectedIndex();
                m_symbolJList.ensureIndexIsVisible(row_pose + ROW_OFFSET);
                userInput = m_symbolField.getText();

                if(userInput != null) {
                    String key = m_symbolField.getText().toUpperCase();
                    ListModel listModel = m_symbolJList.getModel();

                    for(int i = 0; i < listModel.getSize(); i++) {
                        listModel.getElementAt(i);
                        if(listModel.getElementAt(i).toString().startsWith(key)) {             
                            m_symbolJList.setSelectedValue(listModel.getElementAt(i), true);  
                            break;
                        }
                    }
                }
            } 
        });


        // Abstract Action listener that saves the selected stock symbols from stock
        // symbol list when the enter button is depressed and then released.  The
        // symbols are then displaed in the quote text field.
        Action enterAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String symbol = null;
                m_clickCntr = 0;

                if(!m_isMultSelect) {
                    symbol = m_symbolJList.getSelectedValue().toString();

                   if(symbol != null) {
                        m_symbolField.setText(symbol);
                    }          

                    // Reposition viewable area of list after selection
                    int row_pos = m_symbolJList.getSelectedIndex();
                    m_symbolJList.ensureIndexIsVisible(row_pos + ROW_OFFSET);

                    if(!m_symbolField.getText().equals(symbol)) {
                        System.err.println("Unable to read symbol from symbols text field");
                    }
                    else {
                        m_symbolList.add(symbol);
                        m_operate.setSymbolList();
                    }
                }
                else {
                    for(String str : m_multSymbols){
                        m_symbolList.add(str);
                    }
                    m_multSymbols.clear();
                    m_operate.setSymbolList();
                }

                m_symbolJList.clearSelection();
            }
        };

        // Action and Input maps for the symbols list.  Keypresses are bound to the enter button on release
        m_symbolField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), ENTER_PRESSED);
        m_symbolField.getActionMap().put(ENTER_PRESSED, enterAction);
        m_symbolJList.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), ENTER_PRESSED);
        m_symbolJList.getActionMap().put(ENTER_PRESSED, enterAction);


        // Create the JPanels for the symbols list and symbols text field.
        JPanel fillerPanel = new JPanel();
        fillerPanel.setPreferredSize(new Dimension(160, 180));

        JPanel listPanel = new JPanel(new GridBagLayout());
        listPanel.setPreferredSize(new Dimension(160, 200));

        JPanel textFieldPanel = new JPanel(new GridBagLayout());
        textFieldPanel.setPreferredSize(new Dimension(160, 50));

        // Layout the JScrollPane for the Symbols JList
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.ipadx = 10;
        m_constraints.insets = new Insets(50, 0, 0, 0);
        listPanel.add(m_scrollPane, m_constraints);

        // Layout the Symbols text field, under Symbols JList
        m_constraints.gridx = 0;
        m_constraints.weighty = 0.1;
        m_constraints.anchor = GridBagConstraints.NORTH;
        m_constraints.insets = new Insets(0, 0, 0, 0);
        textFieldPanel.add(m_symbolField, m_constraints);
        

        // Create a JPanel for the UI's control buttons.
        JPanel controlBtnPanel = new JPanel(new GridBagLayout());
        controlBtnPanel.setPreferredSize(new Dimension(350, 40));

        // Create the control buttons for the UI
        m_historyBtn = new JButton(UI.HISTORY.getName());
        m_operate.setHistoryButton(false);
        m_leftControlBtn = new JButton(UI.USER_REG.getName());
        m_rightControlBtn = new JButton(UI.LOGIN.getName());


        // Listen for enter when left or right control buttons have focus
        Action m_leftButtonAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                m_leftControlBtn.doClick();
            }
        };

        // Action and Input maps for the left control button
        m_leftControlBtn.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), ENTER_PRESSED);
        m_leftControlBtn.getActionMap().put(ENTER_PRESSED, m_leftButtonAction);

        Action rightButtonAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                m_rightControlBtn.doClick();
            }
        };

        // Action and Input maps for the right control button
        m_rightControlBtn.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), ENTER_PRESSED);
        m_rightControlBtn.getActionMap().put(ENTER_PRESSED, rightButtonAction);

        m_historyBtn.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), ENTER_PRESSED);
        m_historyBtn.getActionMap().put(ENTER_PRESSED, m_leftButtonAction);

        Action historyButtonAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                m_historyBtn.doClick();
                System.out.println("history button clicked");
            }
        };

        // Action and Input maps for the right control button
        m_historyBtn.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), ENTER_PRESSED);
        m_historyBtn.getActionMap().put(ENTER_PRESSED, historyButtonAction);

        // Add a panel to layout the UI control buttons
        m_componentPanel = new JPanel();
        m_componentPanel.setPreferredSize(new Dimension(400, 65));

        // Layout the history button
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.anchor = GridBagConstraints.PAGE_START;
        m_constraints.insets = new Insets(0, 0, 2, 10);
        controlBtnPanel.add(m_historyBtn, m_constraints);

        // Layout the left control button
        m_constraints.gridx = 1;
        m_constraints.gridy = 0;
        m_constraints.insets = new Insets(0, 0, 2, 10);
        controlBtnPanel.add(m_leftControlBtn, m_constraints);

        // Layout the right control button
        m_constraints.gridx = 2;
        m_constraints.gridy = 0;
        m_constraints.insets = new Insets(0, 0, 2, 2);
        controlBtnPanel.add(m_rightControlBtn, m_constraints);

        // Layout the buttons control panel onto its parent panel
        m_constraints.gridx = 1;
        m_constraints.gridy = 0;
        m_constraints.insets = new Insets(0, 0, 0, 0);
        m_componentPanel.add(controlBtnPanel, m_constraints);


        // Add action listeners to the control buttons
        m_leftControlBtn.addActionListener(m_operate);
        m_rightControlBtn.addActionListener(m_operate);
        m_historyBtn.addActionListener(m_operate);

        // Add all child JPanels their parent JPanel
        m_leftPanel.add(fillerPanel, BorderLayout.NORTH);
        m_leftPanel.add(listPanel, BorderLayout.CENTER);
        m_leftPanel.add(textFieldPanel, BorderLayout.SOUTH);
        m_bottomPanel.add(m_componentPanel, BorderLayout.CENTER);

        // Add all base JPanels to the JFrame
        m_frame.getContentPane().add(m_bottomPanel, BorderLayout.SOUTH);
        m_frame.getContentPane().add(m_topPanel, BorderLayout.NORTH);
        m_frame.getContentPane().add(m_cardPanel, BorderLayout.CENTER);
        m_frame.getContentPane().add(m_leftPanel, BorderLayout.WEST);
        m_frame.getContentPane().add(m_rightPanel, BorderLayout.EAST);

        // Layout the individual Card's and make GUI visiable
        this.setCardLayout();
        m_frame.setResizable(false);
        m_frame.setVisible(true);
    }


    /**
     * Creates the card objects and adds them to the main card panel.
     */
    private void setCardLayout() {
        try {
            m_homeCard = new HomeCard();
        } catch(IOException ex) {
            System.err.println("Exception attempting to load or retrieve splash image");
            System.err.println(ex.getMessage());
        }

        m_quoteCard = new QuoteCard();
        m_tickerCard = new TickerCard(m_cardPanel, m_quoteCard, m_operate);
        m_regCard = new RegistrationCard(m_operate);
        m_loginCard = new LoginCard(m_operate);
        m_historyCard = new HistoryCard(m_operate);

        m_cardPanel.add(UI.HOME.getName(), m_homeCard.getCard());
        m_cardPanel.add(UI.QUOTE.getName(), m_quoteCard.getCard());
        m_cardPanel.add(UI.TICKER.getName(), m_tickerCard.getCard());
        m_cardPanel.add(UI.USER_REG.getName(), m_regCard.getCard());
        m_cardPanel.add(UI.LOGIN.getName(), m_loginCard.getCard());
        m_cardPanel.add(UI.HISTORY.getName(), m_historyCard.getCard());
        m_frame.add(m_cardPanel, BorderLayout.CENTER);
    }


    /**
     * Inner class of ViewStockTicker.  This class provides methods that control
     * the functionality of the UI and all of components.
     */
    public class OperateStockTicker implements ActionListener {

        /**
         * Changes the name of the left control button on the main panel.
         *  
         * @param name      - Name of left main panel button
         */
        public void resetLeftButton(String name) {
            m_leftControlBtn.setVisible(true);
            m_leftControlBtn.setText(name);
        }


        /**
         * Changes the name of the right control button on the main panel.
         * 
         * @param name      - Name of right main panel button
         */
        public void resetRightButton(String name) {
            m_rightControlBtn.setText(name);
        }


        /**
         * Action listener for the right and left control buttons on the main JPanel.
         * The actions of the right and left control buttons provide the switching
         * between screens and saving/retrieving user information.  Also provides 
         * validation checking of user input.
         * 
         * @param evt           - Registered event from right/left control buttons
         */
        @Override
        public void actionPerformed(ActionEvent evt) {
            UI selection = UI.getType(evt.getActionCommand());
            cardLayout = (CardLayout) (m_cardPanel.getLayout());
            boolean isEmpty = false;

            switch(selection) {

                // Opens Registration form
                case USER_REG:
                    this.resetFlags();
                    m_regSelect = true;
                    m_regCard.clearTextFields();
                    cardLayout.show(m_cardPanel, UI.USER_REG.getName());
                    m_rightControlBtn.transferFocus();
                    this.resetLeftButton(UI.SUBMIT.getName());
                    this.resetRightButton(UI.CLOSE.getName());
                    break;

                // Opens login form
                case LOGIN:
                    this.resetFlags();
                    m_logInSelect = true;
                    m_loginCard.clearTextFields();
                    this.resetLeftButton(UI.SUBMIT.getName());
                    this.resetRightButton(UI.CLOSE.getName());
                    cardLayout.show(m_cardPanel, UI.LOGIN.getName());
                    m_rightControlBtn.transferFocus();
                    break;

                // Creates a User and UserInfo, saves/verifies User Info, and logs
                // the user inot the system.
                case SUBMIT:
                    m_leftControlBtn.requestFocusInWindow();
                    this.resetValidation();
                    m_message = new StringBuilder("Warning: ");
                    if(m_regSelect) {
                        if((m_firstname = m_regCard.getfirstName()).isEmpty() || m_regCard.getfirstName().startsWith(SPACE)) {
                            m_message.append("Firstname field is blank\n");      
                            m_firstname = "";
                            m_isInvalidInput[Fields.FIRST_NM.getValue()] = true; 
                            isEmpty = true;
                        }

                        if((m_lastname = m_regCard.getLastName()).isEmpty() || m_regCard.getLastName().startsWith(SPACE)) {
                            m_message.append("Lastname field is blank\n");
                            m_lastname = "";
                            m_isInvalidInput[Fields.LAST_NM.getValue()] = true;
                            isEmpty = true;
                        }

                        if((m_username = m_regCard.getUsername()).isEmpty() || m_regCard.getUsername().startsWith(SPACE)) {
                            m_message.append("Username field is blank\n");
                            m_username = "";
                            m_isInvalidInput[Fields.USER.getValue()] = true;
                            isEmpty = true;
                        } 

                        if((m_password = m_regCard.getPassword()).isEmpty() || m_regCard.getPassword().startsWith(SPACE)) {
                            m_message.append("Password field is blank\n");
                            m_password = "";
                            m_isInvalidInput[Fields.PASSWD.getValue()] = true;
                            isEmpty = true;
                        }

                        if((m_verifyPasswd = m_regCard.getVerifiedPasswd()).isEmpty() || m_regCard.getVerifiedPasswd().startsWith(SPACE)) {
                            m_message.append("Re-enter password field is blank\n");
                            m_verifyPasswd = "";
                            m_isInvalidInput[Fields.VER_PASS.getValue()] = true;
                            isEmpty = true;
                        }

                        // Verify both entered passwords match
                        if(!isEmpty && !m_verifyPasswd.equalsIgnoreCase(m_password)) {
                           m_message.append("The entered passwords do not match\n");
                           m_message.append("Please re-enter the passwords");
                           m_regCard.clearPasswordFields();
                           m_isInvalidInput[Fields.PASSWD.getValue()] = true;
                           isEmpty = true;
                        }

                        // Registration selcted; register user
                        if(!isEmpty) {   
                            this.registerUser(m_username, m_password);
                        }
                        else {
                            this.showDialog(m_message.toString());
                            m_regCard.setFocusInField(m_isInvalidInput);
                        }
                    }
                    else if(m_logInSelect) {
                        if((m_username = m_loginCard.getUsername()).isEmpty() || m_loginCard.getUsername().startsWith(SPACE)) {
                            m_message.append("Username field is blank\n");
                            m_username = "";
                            m_isInvalidInput[Fields.USER.getValue()] = true;
                            isEmpty = true;
                        }

                        if((m_password = m_loginCard.getPassword()).isEmpty() || m_loginCard.getPassword().startsWith(SPACE)) {
                            m_message.append("Password field is blank\n");
                            m_password = "";
                            m_isInvalidInput[Fields.PASSWD.getValue()] = true;
                            isEmpty = true;
                        }

                        // User is registered; login user
                        if(!isEmpty) {
                            if(m_userAuth.isRegistered(m_username)) {
                                this.logInUser(m_username, m_password);
                            }
                            else {
                                m_message = new StringBuilder("Warning: Not a recognized user, check username or register");
                                m_username = "";
                                m_password = "";
                                m_loginCard.clearTextFields();
                                this.showDialog(m_message.toString());
                            }
                        }
                        else {
                            this.showDialog(m_message.toString());
                            m_loginCard.setFocusInField(m_isInvalidInput);
                        }
                    }
                    else if(m_historySelect) {
                        m_startDate = m_historyCard.getStartDate();
                        m_endDate = m_historyCard.getEndDate();
                        this.showStockHistory();
                    }
                    break;

                // Gets stock symbol for each StockQuote listed in stock quote table
                // and adds to temp symbols List<String>.  Calls getStockQuotes and
                // re-displays the stock quote list in table.
                case REFRESH:
                    List<String> refreshList = new ArrayList<>();
                    refreshList = m_tickerCard.getSymbolsInTable();
                    m_stockQuoteList.clear();
                    m_stockQuoteList = m_stockService.getStockQuotes(refreshList);

                    if(m_stockQuoteList.size() > ZERO) {
                        m_tickerCard.displayStockQuoteList(m_stockQuoteList);
                        //System.out.println("Stock quote list refreshed");
                    }
                    else {
                        m_message = new StringBuilder("Warning: No stocks listed in table to refresh");
                        this.showDialog(m_message.toString());
                    }

                    m_tickerCard.setQuoteFieldFocus();
                    break;

                // Calls business logic to track the selected stock quote
                case TRACK:
                    this.resetFlags();
                    //m_tickerCard.getSelectedStock();
                    m_stockService.trackStock(m_username, m_tickerCard.getSelectedStock().getSymbol(), true);
                    this.resetLeftButton(UI.UNTRACK.getName());
                    break;

                // Calls business logic to untrack selected stock quote
                case UNTRACK:
                    this.resetFlags();
                    m_stockService.trackStock(m_username, m_tickerCard.getSelectedStock().getSymbol(), false);
                    this.resetLeftButton(UI.TRACK.getName());
                    break;

                case HISTORY:
                    this.resetFlags();
                    m_historySelect = true;
                    this.setHistoryButton(false);
                    cardLayout.show(m_cardPanel, UI.HISTORY.getName());
                    this.resetLeftButton("Submit");
                    m_leftControlBtn.grabFocus();
                    break;

                // Log-out user on CLOSE or LOGOUT selected.
                // Show Home screen if CANCEL or CLOSE selected.  
                case  CLOSE:
                    m_closeSelect = true;

                    // Close selected from Registration or LogIn screens
                    if(!m_userAuth.isRegistered(m_username) || !m_isLoggedIn) {
                        this.resetLeftButton(UI.USER_REG.getName());
                        this.resetRightButton(UI.LOGIN.getName());
                        cardLayout.show(m_cardPanel, UI.HOME.getName());
                        m_leftControlBtn.requestFocusInWindow();
                    }
                    else {  // Close selected from Quote Detail screen
                        this.resetLeftButton(UI.REFRESH.getName());
                        this.resetRightButton(UI.LOGOUT.getName()); 
                        this.enableSymbolJList(true);
                        this.setHistoryButton(false);
                        m_historyCard.clearHistory();
                        cardLayout.show(m_cardPanel, UI.TICKER.getName());
                        m_tickerCard.setQuoteFieldFocus();
                    }
                    this.resetFlags();
                    break;

                // Logs out this user from db and business logic
                case LOGOUT:
                    if(m_userAuth.logOut(m_username)) {
                        m_isLoggedIn = false;       
                        this.enableSymbolJList(false);
                        m_tickerCard.clearStockList();
                        m_quoteCard.clearQuote(m_isLoggedIn);
                        m_symbolList.clear();
                        m_stockQuoteList.clear();
                    }
                    else {
                        m_message = new StringBuilder("Warning: Unable to log user out, please try again");
                        this.showDialog(m_message.toString());
                    }

                    this.resetLeftButton(UI.USER_REG.getName());
                    this.resetRightButton(UI.LOGIN.getName());
                    cardLayout.show(m_cardPanel, UI.HOME.getName());
                    m_leftControlBtn.requestFocusInWindow();
                    this.resetFlags();
                    break;

                default:
                    System.out.println("Failed to select a key");
            }
        }


        /**
         * Logs user into the db.  First checks if the user is already logged in, if
         * not then logs in the user.  Will display a warning message if login fails.
         * Once logged in, the method launches the main screen.
         * 
         * @param username      - username entered into username field of Login screen
         * @param password      - password entered into password field of Login screen
         */
        private void logInUser(String username, String password) {
            m_message = new StringBuilder("Warning: ");

            if(!m_userAuth.isLoggedIn(username)) {
                if(m_userAuth.logIn(username, password)) {
                    System.out.println("User successfully logged in\n");
                    m_isLoggedIn = true;
                    //m_stockQuoteList.clear();
                }
                else {
                    m_message.append("User login failed, please check password\n");
                    m_username = "";
                    m_password = "";
                    this.showDialog(m_message.toString());
                    m_regCard.clearPasswordFields();
                    m_isInvalidInput[Fields.PASSWD.getValue()] = true;

                    if(m_logInSelect) {
                        m_loginCard.setFocusInField(m_isInvalidInput);
                    }
                    else if(m_regSelect) {
                        m_regCard.setFocusInField(m_isInvalidInput);
                    }                   
                }
            }
            else {
                
                m_isLoggedIn = true;   
            }

            // New user and is logged in; show stock quote list screen & symbol list
            if(m_isLoggedIn && m_regSelect || m_isLoggedIn && m_logInSelect) { 
                this.resetLeftButton(UI.REFRESH.getName());
                this.resetRightButton(UI.LOGOUT.getName());
                this.showTrackedStocks();
                this.enableSymbolJList(true);

                cardLayout.show(m_cardPanel, UI.TICKER.getName());
                m_tickerCard.setQuoteFieldFocus();
            }       
        }


        /**
         * Checks for a valid registered user from db.  If not present, it will 
         * register the user.  If the user is already register, will log the user
         * in and load the main screen.
         * 
         * @param username      - username entered into username field of registration screen
         * @param password      - password entered into password field of registration screen
         */
        private void registerUser(String username, String password) {
            m_message = new StringBuilder("Warning: ");

            if(!m_userAuth.isRegistered(username)) {
                m_userInfo = new UserInfo(m_firstname, m_lastname);
                if(m_userAuth.register(username, password, m_userInfo)) {
                    this.logInUser(username, password);
                }
                else {
                    m_message.append("Unable to check verify credentials, please try again\n");
                    this.showDialog(m_message.toString());
                }   
            }
            else {
                if(this.checkValidUser(m_username)) {
                    if(!m_userAuth.isLoggedIn(username)) {
                        System.out.println("User is already a registered user, attempting to login...");
                        this.logInUser(username, password);
                    }
                    else {
                        m_userAuth.logOut(username);      
                    }
                }
                else {
                    m_message.append("Username is already taken, please choose another\n");
                    this.showDialog(m_message.toString());
                    m_isInvalidInput[Fields.USER.getValue()] = true;
                    m_regCard.setFocusInField(m_isInvalidInput);
                }  
            }
        }


        /**
         * Verifies that this username for this User is not already registered for
         * another User.  Returns true if the user already exists
         * 
         * 
         * @param username      - username entered into username field of registration screen       
         * @return              - ture if the user exists in db
         */
        private boolean checkValidUser(String username) {
            UserInfo verifyUsr;
            boolean isExists = false;

            verifyUsr = m_userAuth.getUserInfo(username);
            if(m_firstname.equals(verifyUsr.getFirstName()) && m_lastname.equals(verifyUsr.getLastName())) {
                isExists = true;
            }

            return isExists;
        }


        /**
         * Resets all flags to default state.
         * These flags control the operation of the two main control buttons.
         * 
         */
        private void resetFlags() {
            m_logInSelect = false;
            m_regSelect = false;
            m_closeSelect = false;
            m_historySelect = false;
        }


        /**
         * Resets the validation boolean to false.  Array is used to verify user
         * input for the RegistrationCard and LoginCard text fields.
         */
        private void resetValidation() {
            for(int i = 0; i < m_isInvalidInput.length; i++) {
                m_isInvalidInput[i] = false;
            }
        }


        /**
         * Gets/returns the left control button.
         * This is main control button in UI.  This button changes state depending
         * on the active screen.  Control by Action Listener in this class.
         * 
         * @return      - The current state of left control button
         */
        public JButton getLeftControlBtn() {
           return m_leftControlBtn;
        }


        /**
         * Verifies whether the argument String symbol is tracked for this user.
         * Returns true if tracked. 
         * @param symbol        - Symbol from StockQuote to check tracking status
         * @return              - Returns true if StockQuote is tracked
         */
        public boolean getTrackingStatus(String symbol) {
            return m_stockService.isStockTracked(m_username, symbol);
        }


        /**
         * Sets the focus of either the left or right control buttons, depending on
         * the state of the argument.
         * @param leftBtn       - Grabs focus for left(true) or right(false) control buttons
         */
        public void setControlBtnFocus(boolean leftBtn) {
            if(leftBtn) {
                m_leftControlBtn.grabFocus();
            }
            else {
                m_rightControlBtn.grabFocus();
            }
        }


        /**
         * Makes the History button visible or non visible, depending on the argument
         * state, true or false.
         * @param state
         */
        public void setHistoryButton(boolean state) {
                m_historyBtn.setVisible(state);
        }


        /**
         * Gets symbols from JList and add display them to the quote text field.
         */
        public void setSymbolList() {
            if(m_symbolList.size() > ZERO) {
                m_tickerCard.setSymbolsTextField(m_symbolList);
            }
        }


        /**
         * Gets stock quotes based on the entered stock symbols and displays the
         * stock quotes in the stock quote table.  Validates the symbols argument
         * prior to calling the business logic, to insure the symbols are valid.
         *
         * @param symbols       - stock symbols from JList
         */
        public void showStockQuoteList(List<String> symbols) {
            List<String> tableSymbols = new ArrayList<>();
            m_message = new StringBuilder("Warning: ");

            if(symbols.size() > ZERO) { 
                tableSymbols = m_tickerCard.getSymbolsInTable();            // Get tracked stocks from db
                this.validateSymbols(symbols);                              // Validate input from quote text field

                for(String str : tableSymbols) {                            // Add tracked stocks to current quote list
                    symbols.add(str);
                }

                m_stockQuoteList.clear();                                   // Clear list and re-generate a new stock quote list
                m_stockQuoteList = m_stockService.getStockQuotes(symbols);  // Get list of stock quotes from stock ticker service

                if(m_stockQuoteList.size() > ZERO) {
                    m_tickerCard.displayStockQuoteList(m_stockQuoteList);   // Display stock quotes in table
                }
                else {
                    m_message.append("Unable to retreive Stock Quotes from service, please try again");
                    this.showDialog(m_message.toString());
                }
            }
            else {
                m_message.append("Quote text field is empty or the symbols added are invalid\nPlese try again");
                this.showDialog(m_message.toString());
            }

            m_symbolList.clear();                                           // Clear symbol list, no longer required
        }


        /**
         * Get stocks tracked by user from the db and displays them in the stock quote table.
         */
        private void showTrackedStocks() {
            List<String> trackedStockes = new ArrayList<>();
            trackedStockes = m_stockService.getTrackedStocks(m_username);
            if(trackedStockes.size() > ZERO) {          
                m_stockQuoteList = m_stockService.getStockQuotes(trackedStockes);
                if(m_stockQuoteList.size() > ZERO) {
                    m_tickerCard.displayStockQuoteList(m_stockQuoteList);
                }
            }  
        }


        /**
         * Displays a lists of stock details of a given stock, over of range of entered
         * dates.  Verifies the date range is a valid range prior to calling db for
         * stock details.  If invalid, a warning dialog is popped up.
         */
        public void showStockHistory() {
            m_message = new StringBuilder("Warning: ");
            Date today = new Date();
            boolean fault = false;

            try {
                if(m_startDate.compareTo(today) >= 0) {
                    m_message.append("The start date is equal to or greater than today's date\nPlease select a day before today and re-submit querry");
                    fault = true;
                }
                else if(m_endDate.compareTo(today) > 0 ) {
                    m_message.append("The end date is equal to or greater than today's date\nPlease select a day before today and re-submit querry");
                    fault = true;
                }
                else {
                    m_stockHistoryList = m_stockService.getStockHistory(m_tickerCard.getSelectedStock().getSymbol(), m_startDate, m_endDate);

                    if(m_stockHistoryList.size() != 0) {
                        m_historyCard.displayHistory(m_stockHistoryList);
                    }
                    else {
                        m_message.append("No data to report for this date range\nPlease select another date range and re-submit");
                        fault = true;
                    }
                }
            }
            catch(IllegalArgumentException ex) {
                m_message = new StringBuilder("Warning: Start date is after the end date\nPlease check date range and re-submit querry");
                fault = true;
            }
            finally {
                if(fault) {
                    this.showDialog(m_message.toString());
                    m_historyCard.clearHistory();
                }
            }
        }


        /**
         * Validates the user inputted stock symbols match the symbols within the 
         * symbols JList.  Removes user entered symbols if not found within the 
         * list.  Returns a clean list of stock symbols to send to business logic.
         * 
         * @param symbols       - list of symbols entered by user in quote text field
         * @return              - returns a clean list of symbols
         */
        public List<String> validateSymbols(List<String> symbols) {
            ListModel listModel = m_symbolJList.getModel();
            boolean isValid = false;

            for(int i = 0; i < symbols.size(); ) {
                for(int k = 0; k < listModel.getSize(); k++) {
                    if(symbols.get(i).equals(listModel.getElementAt(k))) {
                        isValid = true;
                        break;
                    }   
                }

                if(!isValid) {
                    symbols.remove(i);
                }
                else {
                    i++;    // Increment index when a match is found.
                }
                isValid = false;
            }
            return symbols;
        }


        /**
         * Enable & setVisible the symbol list JList component in main screen.
         * This component is disabled by default and only visible in main screen.
         * 
         * @param enable        - boolean, enables/disables m_symbolJList
         */
        public void enableSymbolJList(boolean enable) {
            m_symbolJList.setEnabled(enable);
            m_symbolJList.setVisible(enable);
            m_scrollPane.setEnabled(enable);
            m_scrollPane.setVisible(enable);
            m_symbolField.setEditable(enable);
            m_symbolField.setVisible(enable);
        }


        /**
         * Pops up a general warning dialog using the argument message as the message
         * content.
         * 
         * @param message           - message to display in body of dialog
         */
        public void showDialog( String message ) {
            JOptionPane.showMessageDialog( m_frame, message, "Stock Ticker Portfolio", JOptionPane.WARNING_MESSAGE);
        }
    }
}
