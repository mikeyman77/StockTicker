/**
 * GUI for Stock Ticker Portfolio Manager
 * J308 Project
 * Paul Wallace
 */

package com.stockticker.ui;

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

import com.stockticker.StockQuote;
import com.stockticker.SymbolMap;
//import com.stockticker.User;
import com.stockticker.UserInfo;
import com.stockticker.logic.AuthorizationService;
import com.stockticker.logic.StockTicker;
import com.stockticker.logic.StockTickerService;
import com.stockticker.logic.UserAuthorization;

import java.util.ArrayList;
import java.util.List;


/**
 * GUI for Stock Ticker Portfolio Manager J308 Project
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
    private JPanel m_toolPanel;
    private JPanel m_topPanel;
    private JPanel m_sidePanel;
    private JPanel m_statusPanel;

    private JList<Object> m_symbolJList;
    private JScrollPane m_scrollPane;

    public JButton m_leftControlBtn;
    public JButton m_rightControlBtn;
    private JTextField m_symbolField;

    private final Toolkit toolKit = Toolkit.getDefaultToolkit();
    private final Dimension screenSize = toolKit.getScreenSize();
    private GridBagConstraints m_constraints;
    private Image m_titleIcon;

    private HomeCard m_homeCard;
    private DetailCard m_detailCard;
    private QuoteCard m_quoteCard;
    private TickerCard m_tickerCard;
    private RegistrationCard m_regCard;
    private LoginCard m_loginCard;
    private final String m_icon = "./images/stock_ticker.png";

    private boolean m_logInSelect = false;
    private boolean m_regSelect = false;
    public boolean m_closeSelect = false;
    public boolean m_isLoggedIn = false;

    private String m_username = "";
    private String m_password = "";
    private String m_firstname = "";
    private String m_lastname = "";

    private StringBuilder m_buildStr;

    private List<String> m_symbolList;
    private List<StockQuote> m_stockQuoteList;

    private final AuthorizationService m_userAuth = UserAuthorization.INSTANCE;
    private final StockTickerService m_stockService =  StockTicker.INSTANCE;
    private UserInfo m_userInfo;
    //private User m_user;
    //private User m_user;

    private final OperateStockTicker m_operate;


    /**
     * Construct a ViewStockTicker object
     * 
     */
    public ViewStockTicker() {
        this.m_symbolList = new ArrayList<>();
        this.m_stockQuoteList = new ArrayList<>();
        //this.m_trackedSymbols = new ArrayList<>();
        m_frame = new JFrame();
        m_operate = new OperateStockTicker();
    }


    /**
     * Builds the main frame and provides a Window Listener close events from
     * the Title Bar.
     */
    public void build() {
        m_titleIcon = new ImageIcon(toolKit.getImage(m_icon)).getImage();
        m_frame.setIconImage(m_titleIcon);
        m_frame.setSize(920, 600);
        m_frame.setLocation(screenSize.width / 4, screenSize.height / 4);
        JFrame.setDefaultLookAndFeelDecorated(true);
        m_constraints = new GridBagConstraints();

        m_frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.out.println("disposing");
                m_frame.dispose();
                System.out.println("Closing");
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

        m_toolPanel = new JPanel();
        m_toolPanel.setPreferredSize(new Dimension(120, 520));

        m_topPanel = new JPanel();
        m_topPanel.setPreferredSize(new Dimension(700, 60));

        m_sidePanel = new JPanel();
        m_sidePanel.setPreferredSize(new Dimension(120, 520));

        m_cardPanel = new JPanel(new CardLayout());
        m_cardPanel.setLayout(cardLayout = new CardLayout());
        m_cardPanel.setPreferredSize(new Dimension(550, 520));
        m_cardPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));


        m_symbolJList = new JList<>(SymbolMap.getSymbols().keySet().toArray());
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

        // Select symbol in list after a double mouse click and track this symbol.  
        // Verify user is signed in
        m_symbolJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String symbol = null;
                if(e.getClickCount() == 2) {
                    symbol = m_symbolJList.getSelectedValue().toString();

                    if(m_username.isEmpty() || m_username.startsWith(SPACE)) {
                        System.out.println("User has not logged in");
                    }
                    else if(!m_userAuth.isLoggedIn(m_username)) {
                        System.err.println("User is not logged in");
                    }
                    else if(symbol == null) {
                        System.err.println("Unable to read symbol");
                    }
                    else {
                        m_symbolList.add(symbol);
                        m_operate.setSymbolList();
                    }
                }
                else {
                    if(e.getClickCount() == 1) {
                        String selection = m_symbolJList.getSelectedValue().toString();
                        m_symbolField.setText(selection);
                    }
                }
            }
        });

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


        // Action listener for JList; gets fired when enter pressed in list or text field
        Action enterAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String symbol = m_symbolJList.getSelectedValue().toString();
                if(symbol != null) {
                    m_symbolField.setText(symbol);
                }
                else {
                    System.out.println("No selection made in list");
                }

                int row_pos = m_symbolJList.getSelectedIndex();
                m_symbolJList.ensureIndexIsVisible(row_pos + ROW_OFFSET);

                if(m_username.isEmpty() || m_username.startsWith(SPACE)) {
                    System.out.println("User has not logged in");
                }
                else if(symbol.isEmpty()) {
                    System.err.println("Unable to select symbol");
                }
                else if(!m_symbolField.getText().equals(symbol)) {
                    System.err.println("Unable to read symbol from text field");
                }
                else if(!m_userAuth.isLoggedIn(m_username)) {
                    System.err.println("User is not logged in");
                }
                else {
                    m_symbolList.add(symbol);
                    m_operate.setSymbolList();
                }
            }
        };

        m_symbolField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), ENTER_PRESSED);
        m_symbolField.getActionMap().put(ENTER_PRESSED, enterAction);
        m_symbolJList.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), ENTER_PRESSED);
        m_symbolJList.getActionMap().put(ENTER_PRESSED, enterAction);


        // Create/add child panels to the main frame JPanels and layout their
        // individual controls using GridBagLayout.
        JPanel toolPanel = new JPanel();
        toolPanel.setPreferredSize(new Dimension(160, 180));
        JPanel midPanel = new JPanel(new GridBagLayout());
        midPanel.setPreferredSize(new Dimension(160, 200));
        JPanel listPanel = new JPanel(new GridBagLayout());
        listPanel.setPreferredSize(new Dimension(160, 50));

        // Layout the JList JScrollPane
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.ipadx = 10;
        m_constraints.insets = new Insets(50, 0, 0, 0);
        midPanel.add(m_scrollPane, m_constraints);

        // Layout the JList's set button
        m_constraints.gridx = 1;
        m_constraints.weighty = 0.1;
        m_constraints.anchor = GridBagConstraints.NORTH;
        m_constraints.insets = new Insets(0, 0, 0, 0);
        listPanel.add(m_symbolField, m_constraints);
        m_constraints.insets = new Insets(0, 0, 0, 0);
        m_statusPanel = new JPanel();
        m_statusPanel.setPreferredSize(new Dimension(650, 65));


        // Create the JPanel containing the UI control buttons
        JPanel btnPanel = new JPanel(new GridBagLayout());
        btnPanel.setPreferredSize(new Dimension(200, 40));
        m_leftControlBtn = new JButton(UI.USER_REG.getName());
        m_rightControlBtn = new JButton(UI.LOGIN.getName());

        // Listen for enter when left or right control buttons have focus
        Action m_leftButtonAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                m_leftControlBtn.doClick();
            }
        };

        m_leftControlBtn.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), ENTER_PRESSED);
        m_leftControlBtn.getActionMap().put(ENTER_PRESSED, m_leftButtonAction);

        Action rightButtonAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                m_rightControlBtn.doClick();
            }
        };

        m_rightControlBtn.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), ENTER_PRESSED);
        m_rightControlBtn.getActionMap().put(ENTER_PRESSED, rightButtonAction);
        

        // Layout the left control button
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.anchor = GridBagConstraints.PAGE_START;
        m_constraints.insets = new Insets(0, 0, 10, 10);
        btnPanel.add(m_leftControlBtn, m_constraints);

        // Layout the right control button
        m_constraints.gridx = 1;
        m_constraints.gridy = 0;
        m_constraints.insets = new Insets(0, 0, 0, 0);
        btnPanel.add(m_rightControlBtn, m_constraints);

        // Add action listeners to the control buttons
        m_leftControlBtn.addActionListener(m_operate);
        m_rightControlBtn.addActionListener(m_operate);

        // Add all child JPanels their parent JPanel
        m_toolPanel.add(toolPanel, BorderLayout.NORTH);
        m_toolPanel.add(midPanel, BorderLayout.CENTER);
        m_toolPanel.add(listPanel, BorderLayout.SOUTH);
        m_statusPanel.add(btnPanel, BorderLayout.NORTH);
        m_bottomPanel.add(m_statusPanel, BorderLayout.CENTER);

        // Add all main JPanels to the main frame
        m_frame.getContentPane().add(m_bottomPanel, BorderLayout.SOUTH);
        m_frame.getContentPane().add(m_topPanel, BorderLayout.NORTH);
        m_frame.getContentPane().add(m_cardPanel, BorderLayout.CENTER);
        m_frame.getContentPane().add(m_toolPanel, BorderLayout.WEST);
        m_frame.getContentPane().add(m_sidePanel, BorderLayout.EAST);

        // Layout the individual Card's and make GUI visiable
        this.setCardLayout();
        m_frame.setResizable(false);
        m_frame.setVisible(true);
    }


    /**
     * Layout the individual Cards (screens) of the UI. Instantiates the
     * children JPanels that will make up the entire CardLayout.
     * 
     */
    private void setCardLayout() {
        try {
            m_homeCard = new HomeCard();
        } catch(IOException ex) {
            System.err.println("Exception attempting to load or retrieve splash image");
            System.err.println(ex.getMessage());
        }

        m_detailCard = new DetailCard();
        m_quoteCard = new QuoteCard();
        m_tickerCard = new TickerCard(m_cardPanel, m_quoteCard, m_operate);
        m_regCard = new RegistrationCard(m_operate);
        m_loginCard = new LoginCard(m_operate);

        m_cardPanel.add(UI.HOME.getName(), m_homeCard.getCard());
        m_cardPanel.add(UI.DETAIL.getName(), m_detailCard.getCard());
        m_cardPanel.add(UI.QUOTE.getName(), m_quoteCard.getCard());
        m_cardPanel.add(UI.TICKER.getName(), m_tickerCard.getCard());
        m_cardPanel.add(UI.USER_REG.getName(), m_regCard.getCard());
        m_cardPanel.add(UI.LOGIN.getName(), m_loginCard.getCard());
        m_frame.add(m_cardPanel, BorderLayout.CENTER);
    }



    /**
     *
     */
    public class OperateStockTicker implements ActionListener {

        /**
         * Changes the name of the left control button on the main panel.
         *  
         * @param name      - Name of left main panel button
         */
        public void resetLeftButton(String name) {
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
         * Override method that receives ActionEvents for the JPanel fields, such as
         * a button press. Determines which event occurred and takes the appropriate
         * action.
         * 
         * @param evt       - Registered event
         */
        @Override
        public void actionPerformed(ActionEvent evt) {
            UI selection = UI.getType(evt.getActionCommand());
            cardLayout = (CardLayout) (m_cardPanel.getLayout());
            boolean isEmpty = false;

            switch(selection) {

                case USER_REG:
                    this.resetFlags();
                    m_regSelect = true;
                    m_regCard.clearTextFields();
                    cardLayout.show(m_cardPanel, UI.USER_REG.getName());
                    m_rightControlBtn.transferFocus();
                    this.resetLeftButton(UI.SUBMIT.getName());
                    this.resetRightButton(UI.CLOSE.getName());
                    break;

                case LOGIN:
                    this.resetFlags();
                    m_logInSelect = true;
                    m_loginCard.clearTextFields();
                    this.resetLeftButton(UI.SUBMIT.getName());
                    this.resetRightButton(UI.CLOSE.getName());
                    cardLayout.show(m_cardPanel, UI.LOGIN.getName());
                    m_rightControlBtn.transferFocus();
                    break;

                case SUBMIT:
                    m_leftControlBtn.requestFocusInWindow();
                    if(m_regSelect) {
                        if((m_firstname = m_regCard.getfirstName()).isEmpty() || m_regCard.getfirstName().startsWith(SPACE)) {
                            System.out.println("Firstname field is blank");
                            m_firstname = "";
                            isEmpty = true;
                        }

                        if((m_lastname = m_regCard.getLastName()).isEmpty() || m_regCard.getLastName().startsWith(SPACE)) {
                            System.out.println("Lastname field is blank");
                            m_lastname = "";
                            isEmpty = true;
                        }

                        if((m_username = m_regCard.getUsername()).isEmpty() || m_regCard.getUsername().startsWith(SPACE)) {
                            System.out.println("Username field is blank");
                            m_username = "";
                            isEmpty = true;
                        } 

                        if((m_password = m_regCard.getPassword()).isEmpty() || m_regCard.getPassword().startsWith(SPACE)) {
                            System.out.println("Password field is blank");
                            m_password = "";
                            isEmpty = true;
                        }

                        // Registration selcted; register user
                        if(!isEmpty) {   
                            this.registerUser(m_username, m_password);
                        }
                    }
                    else if(m_logInSelect) {
                        if((m_username = m_loginCard.getUsername()).isEmpty() || m_loginCard.getUsername().startsWith(SPACE)) {
                            System.out.println("Username field is blank");
                            m_username = "";
                            isEmpty = true;
                        }

                        if((m_password = m_loginCard.getPassword()).isEmpty() || m_loginCard.getPassword().startsWith(SPACE)) {
                            System.out.println("Password field is blank");
                            m_password = "";
                            isEmpty = true;
                        }

                        // User is registered; login user
                        if(!isEmpty) {      
                            if(m_userAuth.isRegistered(m_username)) {
                                this.logInUser(m_username, m_password);
                            }
                            else {
                                System.out.println("Not a recognized user, check username or register");
                                m_username = "";
                                m_password = "";
                                m_loginCard.clearTextFields();
                            }
                        } 
                    }
                    break;

                // Gets stock symbol for each StockQuote listed in stock quote table
                // and adds to temp symbols List<String>.  Calls getStockQuotes and
                // re-displays the stock quote list in table.
                case REFRESH:
                    List<String> refreshList = new ArrayList<>();
                    m_tickerCard.getSymbolsInTable(refreshList);
                    m_stockQuoteList.clear();
                    m_stockQuoteList = m_stockService.getStockQuotes(refreshList);

                    if(m_stockQuoteList.size() > ZERO) {//&& m_stockQuoteList != null) {
                        m_tickerCard.displayStockQuoteList(m_stockQuoteList, m_isLoggedIn);
                        System.out.println("Stock quote list refreshed");
                    }
                    else {
                        m_buildStr = new StringBuilder("Warning: No stocks listed in table");
                        this.showDialog(m_buildStr.toString());
                    }

                    m_tickerCard.setQuoteFieldFocus();
                    break;

                case TRACK:
                    this.resetFlags();
                    m_tickerCard.getSelectedStock();
                    m_stockService.trackStock(m_username, m_tickerCard.getSelectedStock().getSymbol(), true);
                    this.resetLeftButton(UI.UNTRACK.getName());
                    break;
                    
                case UNTRACK:
                    this.resetFlags();
                    m_stockService.trackStock(m_username, m_tickerCard.getSelectedStock().getSymbol(), false);
                    this.resetLeftButton(UI.TRACK.getName());
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
                        cardLayout.show(m_cardPanel, UI.TICKER.getName());
                        m_tickerCard.setQuoteFieldFocus();
                    }
                    this.resetFlags();
                    break;
                    
                case LOGOUT:
                    if(m_userAuth.logOut(m_username)) {
                        m_isLoggedIn = false;
                        System.out.println("User is logged out");
                        this.enableSymbolJList(false);
                        m_tickerCard.clearStockList(m_isLoggedIn);
                        m_quoteCard.clearQuote(m_isLoggedIn);
                        m_symbolList.clear();
                        m_stockQuoteList.clear();
                    }
                    else {
                        System.out.println("Unable to log user out");
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
         *
         * @param username
         * @param password
         */
        private void logInUser(String username, String password) {
            if(!m_userAuth.isLoggedIn(username)) {
                if(m_userAuth.logIn(username, password)) {
                    System.out.println("User successfully logged in");
                    m_isLoggedIn = true;   
                }
                else {
                    System.out.println("User login failed, please check password");
                    m_username = "";
                    m_password = "";
                }
            }
            else {
                System.out.println("User is already logged in");
                m_isLoggedIn = true;   
            }

            // New user and is logged in;  show stock quote list screen & symbol list
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
         *
         * @param username
         * @param password
         */
        private void registerUser(String username, String password) {
            if(!m_userAuth.isRegistered(username)) {
                m_userInfo = new UserInfo(m_firstname, m_lastname);
                if(m_userAuth.register(username, password, m_userInfo)) {
                    System.out.println("New user successfully registered");
                    this.logInUser(username, password);
                }
                else {
                    System.err.println("Problem occured, unable to register user");               
                }   
            }
            else {
                if(this.checkValidUser(m_username)) {
                    if(!m_userAuth.isLoggedIn(username)) {
                        System.out.println("User is already a registered user, attempting to login...");
                        this.logInUser(username, password);
                    }
                    else {
                        System.out.println("User is already registered and logged in");

                    }
                }
                else {
                    System.out.println("Username is already taken, please choose another");
                }  
            }
        }


        /**
         * Verify username is not already in registered to another User
         * 
         * @param username
         * @return
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
         * Gets the user entered symbols and adds them to a symbols List<String>.
         */
        public void setSymbolList() {
            if(m_symbolList.size() > ZERO) {
                m_tickerCard.setSymbolsTextField(m_symbolList, m_isLoggedIn);
                System.out.println("Symbol selected");
                //m_quoteSelect = false;
            }
            else {
                System.out.println("Unable to select symbol");
            }
        }


        /**
         * Gets the stock quotes based on the entered stock symbols and displays the
         * stock quotes in the stock quote table.
         */
        public void showStockQuoteList() {
            if(m_symbolList.size() > ZERO) {
                m_tickerCard.getSymbolsInTable(m_symbolList);
                m_stockQuoteList = m_stockService.getStockQuotes(m_symbolList);

                if(m_stockQuoteList.size() > ZERO) {
                    m_tickerCard.displayStockQuoteList(m_stockQuoteList, m_isLoggedIn);
                    //m_quoteSelect = true;
                    System.out.println("Stock quotes added to stock quote list");                }
                else {
                    System.out.println("Unable to get Stock Quotes; no quotes added to list");
                }
            }
            else {
                m_buildStr = new StringBuilder("Warning: No symbols selected from symbols list");
                this.showDialog(m_buildStr.toString());
            }
            m_symbolList.clear();
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
                    m_tickerCard.displayStockQuoteList(m_stockQuoteList, m_isLoggedIn);
                }
                else {
                    System.out.println("Unable to get the users tracked stock quotes");
                }
            }
            else {
                System.out.println("User has no tracked stocks");
            }
        }	


        /**
         * Enable & setVisible the symbol list JList component in main screen.
         * This component is disabled by default.
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
         *
         * @param message
         */
        public void showDialog( String message ) {
            JOptionPane.showMessageDialog( m_frame, message, "Stock Ticker Portfolio", JOptionPane.WARNING_MESSAGE);
        }
    }
}
