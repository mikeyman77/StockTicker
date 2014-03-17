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
    private static final int ROW_OFFSET = 7;
    private static final String SPACE = " ";

    private static CardLayout cardLayout;
    private static JPanel m_cardPanel;

    private final JFrame m_frame;
    private JPanel m_bottomPanel;
    private JPanel m_toolPanel;
    private JPanel m_topPanel;
    private JPanel m_sidePanel;
    private JPanel m_statusPanel;

    private JList<Object> m_stockList;
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
    //private boolean m_stockListSelect = false;
    private boolean m_cancelSelect = false;
    //private boolean m_isTracking = false;
    public boolean m_isLoggedIn = false;

    private String m_username = "";
    private String m_password = "";
    private String m_firstname = "";
    private String m_lastname = "";

    //private List<String> m_trackedStocks;
    private List<String> m_symbolList;
    private List<StockQuote> m_stockQuoteList;

    private final AuthorizationService m_userAuth = UserAuthorization.INSTANCE;
    private final StockTickerService m_stockTicker =  StockTicker.INSTANCE;
    private UserInfo m_userInfo;
    //private User m_user;

    private final OperateStockTicker m_operate;


    /**
     * Construct a ViewStockTicker object
     * 
     */
    public ViewStockTicker() {
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


        m_stockList = new JList<>(SymbolMap.getSymbols().keySet().toArray());
        m_stockList.setVisibleRowCount(8);
        m_stockList.setEnabled(false);
        m_stockList.setVisible(false);

        m_scrollPane = new JScrollPane(m_stockList);
        m_scrollPane.setSize(150, 20);
        m_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        m_scrollPane.setEnabled(false);
        m_scrollPane.setVisible(false);

        m_symbolField = new JTextField(6);
        m_symbolField.setEditable(false);
        m_symbolField.setVisible(false);
  
        // Select symbol in list after a double mouse click and track this symbol.  
        // Verify user is signed in
        m_stockList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String symbol = null;
                if(e.getClickCount() == 2) {
                    //m_isTracking = true;
                    symbol = m_stockList.getSelectedValue().toString();

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
                        m_operate.getStockQuoteList();
                    }
                }
                else {
                    if(e.getClickCount() == 1) {
                        String selection = m_stockList.getSelectedValue().toString();
                        m_symbolField.setText(selection);
                    }
                }
            }
        });

        m_symbolField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                String userInput = null;

                int row_pose = m_stockList.getSelectedIndex();
                m_stockList.ensureIndexIsVisible(row_pose + ROW_OFFSET);
                userInput = m_symbolField.getText();

                if(userInput != null) {
                    String key = m_symbolField.getText().toUpperCase();
                    ListModel listModel = m_stockList.getModel();

                    for(int i = 0; i < listModel.getSize(); i++) {
                        listModel.getElementAt(i);
                        if(listModel.getElementAt(i).toString().startsWith(key)) {             
                            m_stockList.setSelectedValue(listModel.getElementAt(i), true);  
                            break;
                        }
                    }
                }
            } 
        });


        Action enterAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //m_isTracking = true;
                String symbol = m_stockList.getSelectedValue().toString();
                m_symbolField.setText(symbol);

                int row_pos = m_stockList.getSelectedIndex();
                m_stockList.ensureIndexIsVisible(row_pos + ROW_OFFSET);

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
                /*else if(m_stockTicker.trackStock(m_username, symbol, true)) {
                    m_trackedStocks = m_stockTicker.getTrackedStocks(m_username);
                    System.out.println("Trcking symbol " + symbol);
                }*/
                else {
                    m_symbolList.add(symbol);
                    m_operate.getStockQuoteList();
                }
            }
        };

        m_symbolField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), ENTER_PRESSED);
        m_symbolField.getActionMap().put(ENTER_PRESSED, enterAction);
        m_stockList.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), ENTER_PRESSED);
        m_stockList.getActionMap().put(ENTER_PRESSED, enterAction);


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
        //Action buttonAction = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent evt) {
            UI selection = UI.getType(evt.getActionCommand());
            cardLayout = (CardLayout) (m_cardPanel.getLayout());
            boolean isEmpty = false;

            System.out.println("In operate action = " + selection);
            switch(selection) {

                case USER_REG:
                    m_regCard.clearTextFields();
                    cardLayout.show(m_cardPanel, UI.USER_REG.getName());
                    this.resetLeftButton(UI.SUBMIT.getName());
                    this.resetRightButton("Cancel");
                    m_regSelect = true;
                    break;


                case LOGIN:
                    m_loginCard.clearTextFields();
                    cardLayout.show(m_cardPanel, UI.LOGIN.getName());
                    if(!m_logInSelect) {
                        this.resetLeftButton("Submit");
                        this.resetRightButton("Cancel");
                        m_logInSelect = true;
                        m_regSelect = false;
                    }
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
                            //m_userInfo = new UserInfo(m_firstname, m_lastname);
                            this.registerUser(m_username, m_password);
                        }
                        /*else {
                            m_regCard.clearTextFields();
                        }*/
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
                        /*else {
                            m_loginCard.clearTextFields();
                        }*/
                    }
                    break;


                //case UPDATE:
                case REFRESH:
                    cardLayout.show(m_cardPanel, UI.TICKER.getName());
                    m_leftControlBtn.requestFocusInWindow();
                    m_quoteCard.clearQuote(m_isLoggedIn);
                    
                    //if(m_tickerSelect && !m_trackedStocks.isEmpty()) {
                    //if(!m_stockListSelect) {
                        /*if(m_symbolList != null) {//&& m_symbolList.size() > 0
                            m_stockQuoteList = m_stockTicker.getStockQuotes(m_symbolList);
                            if(m_stockQuoteList.size() > 1 && m_stockQuoteList != null) {//
                                m_tickerCard.displayStockQuoteList(m_stockQuoteList, m_isLoggedIn);
                                //m_stockListSelect = true;
                            }
                            else {
                                System.out.println("Unable to get Stock Quotes list");
                            }
                        }
                        else {
                            System.out.println("Please add a symbol to stock quote list");
                        }*/
                    /*}
                    else {
                        m_quoteCard.clearQuote(m_isLoggedIn);  //Returning from QuoteCard
                        //m_stockListSelect = false;
                    }
                        //cardLayout.show(m_cardPanel, UI.DETAIL.getName());
                        //m_tickerSelect = false;
                    } else {
                        cardLayout.show(m_cardPanel, UI.TICKER.getName());
                        m_tickerSelect = true;
                    }*/

                    break;

                case CANCEL:
                    m_cancelSelect = true;
                case LOGOUT:
                    if(!m_cancelSelect) {
                        if(m_userAuth.logOut(m_username)) {
                            System.out.println("User is logged out");
                            //m_stockListSelect = false;
                            m_isLoggedIn = false;
                            this.enableStockList(false);
                            m_symbolList = null;
                            m_tickerCard.clearStockList(m_isLoggedIn);
                            m_quoteCard.clearQuote(m_isLoggedIn); 
                        }
                        else {
                            System.out.println("Unable to log user out");
                        }
                    }
                    this.resetLeftButton("Registration");
                    this.resetRightButton("Login");
                    this.resetFlags();
                    cardLayout.show(m_cardPanel, UI.HOME.getName());
                    m_leftControlBtn.requestFocusInWindow();
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
                    this.resetFlags();
                    //m_tickerCard.displayTractedStocks(m_stockQuoteList);
                    m_symbolList = new ArrayList<>();
                    m_stockQuoteList = new ArrayList<>();
                    m_isLoggedIn = true;
                    this.enableStockList(true);
                }
                else {
                    System.out.println("User login failed, please check password");
                    m_username = "";
                    m_password = "";
                }
            }
            else {
                System.out.println("User is already logged in");
                this.resetFlags();
                m_isLoggedIn = true;
                this.enableStockList(true);
            }

            // User is logged in,  switch screen to ticker
            if(!m_logInSelect && !m_regSelect) {
                this.resetLeftButton("Refresh");
                this.resetRightButton("Logout");
                m_rightControlBtn.setEnabled(true);
                //m_tickerSelect = true;
                cardLayout.show(m_cardPanel, UI.TICKER.getName());
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
                    //m_user = new User(username, password);
                    System.out.println("New user successfully registered");
                    m_regSelect = false;
                    m_logInSelect = true;
                    this.logInUser(username, password);
                }
                else {
                    System.err.println("Problem occured, unable to register user");
                    this.resetFlags();
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
                        //this.resetLeftButton("Update");
                        this.resetLeftButton("Refresh");
                        this.resetRightButton("Logout");
                        this.resetFlags();
                        //m_tickerSelect = true;
                        cardLayout.show(m_cardPanel, UI.TICKER.getName());
                        m_leftControlBtn.requestFocusInWindow();
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
         * Reset all flags to default state
         * 
         */
        private void resetFlags() {
            m_logInSelect = false;
            m_regSelect = false;
            m_cancelSelect = false;
        }


        /**
         *
         * @return
         */
        public JButton getLeftControlBtn() {
           return m_leftControlBtn;
        }


        /**
         *
         */
        public void getStockQuoteList() {
            if(m_symbolList != null) {
                m_stockQuoteList = m_stockTicker.getStockQuotes(m_symbolList);
                    if(m_stockQuoteList.size() > 1 && m_stockQuoteList != null) {//
                        m_tickerCard.setSymbolsTextField(m_symbolList, m_isLoggedIn);
                        System.out.println("Symbol selected");
                    }
                    else {
                        System.out.println("Unable to get Stock Quotes list");
                    }
            }
            else {
                System.out.println("Unable to get Stock Quotes list");
            }
        }


        /**
         *
         */
        public void showStockQuoteList() {
            if(m_symbolList != null) {
                m_stockQuoteList = m_stockTicker.getStockQuotes(m_symbolList);
                if(m_stockQuoteList.size() > 1 && m_stockQuoteList != null) {//
                    m_tickerCard.displayStockQuoteList(m_stockQuoteList, m_isLoggedIn);
                }
                else {
                    System.out.println("Unable to get Stock Quotes list");
                }
            }
            else {
                System.out.println("Please add a symbol to stock quote list");
            }
        }


        /**
         *
         * @param enable
         */
        private void enableStockList(boolean enable) {
            m_stockList.setEnabled(enable);
            m_stockList.setVisible(enable);
            m_scrollPane.setEnabled(enable);
            m_scrollPane.setVisible(enable);
            m_symbolField.setEditable(enable);
            m_symbolField.setVisible(enable);
        }
    }
}
