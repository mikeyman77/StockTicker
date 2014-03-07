/**
 * GUI for Stock Ticker Portfolio Manager
 * J308 Project
 * Paul Wallace
 */

package com.stockticker.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
//import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

//import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.stockticker.SymbolMap;
import com.stockticker.User;
import com.stockticker.UserInfo;
import com.stockticker.logic.AuthorizationService;
import com.stockticker.logic.StockTicker;
import com.stockticker.logic.StockTickerService;
import com.stockticker.logic.UserAuthorization;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 * GUI for Stock Ticker Portfolio Manager J308 Project
 * 
 * @author prwallace
 */
public class ViewStockTicker extends WindowAdapter implements IStockTicker_UIComponents {

    //private static ViewStockTicker instance;
    private static CardLayout cardLayout;
    private static JPanel m_cardPanel;

    private final JFrame m_frame;
    private JPanel m_bottomPanel;
    private JPanel m_toolPanel;
    private JPanel m_topPanel;
    private JPanel m_sidePanel;
    private JPanel m_statusPanel;

    public JButton m_leftControlBtn;
    public JButton m_rightControlBtn;

    private final Toolkit toolKit = Toolkit.getDefaultToolkit();
    private final Dimension screenSize = toolKit.getScreenSize();
    private GridBagConstraints m_constraints;
    //private Image m_titleIcon;

    private HomeCard m_homeCard;
    private DetailCard m_detailCard;
    private QuoteCard m_quoteCard;
    private TickerCard m_tickerCard;
    private RegistrationCard m_regCard;
    private LoginCard m_loginCard;
    //private final String m_icon = "images\\stock_ticker.png";

    private boolean m_logInSelect = false;
    private boolean m_regSelect = false;
    private boolean m_tickerSelect = false;
    private boolean m_cancelSelect = false;
    //private boolean m_isRegistered = false;

    private String m_username = null;
    private String m_password = null;
    private String m_firstname = null;
    private String m_lastname = null;
    private String m_symbol = null;

    //private int m_logInTries = 0;
    //private int m_maxTries = 3;
    //private int m_index;

    private final AuthorizationService m_userAuth = UserAuthorization.INSTANCE;
    private final StockTickerService stockTicker =  StockTicker.INSTANCE;
    private UserInfo m_userInfo;
    //private User m_user;


    /**
     * Construct a ViewStockTicker object
     * 
     */
    public ViewStockTicker() {
        m_frame = new JFrame();
    }


    /**
     * Builds the main frame and provides a Window Listener close events from
     * the Title Bar.
     */
    public void build() {
        //m_titleIcon = new ImageIcon(toolKit.getImage(m_icon)).getImage();
        //m_frame.setIconImage(m_titleIcon);
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


        final JList<Object> stockList = new JList<>(SymbolMap.getSymbols().keySet().toArray());
        JScrollPane scrollPane = new JScrollPane(stockList);
        scrollPane.setSize(150, 20);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        final JTextField symbolField = new JTextField(5);
        symbolField.setEditable(true);

        final JButton setButton = new JButton();
        setButton.setName("Set");
        setButton.setPreferredSize(new Dimension(10, 10));
        setButton.setActionCommand("Set");
        
        // Select symbol in list after a double mouse click and track this symbol.  
        // Verify user is signed in
        stockList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2 && m_username != null) {
                    if(m_userAuth.isLoggedIn(m_username)) {
                        m_symbol = stockList.getSelectedValue().toString();
                        if(m_symbol != null && stockTicker.trackStock(m_username, m_symbol, true)) {
                            System.out.println("Now tracking symbol " + m_symbol);
                        }
                    }
                    else {
                        System.out.println("Unable to track symbol; user may not be logged in");
                    }
                }
                else {
                    if(e.getClickCount() == 2) {
                        System.out.println("Unable to track symbol; user may not be logged in");
                    }
                }
            }
        });

        // Get entered text from text field after user presses enter. Select this entry in the list.
        symbolField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m_symbol = symbolField.getText().toUpperCase();
                stockList.setSelectedValue(m_symbol, true);
                System.out.println(symbolField.getText());
            }
        });

        // Get selected symbol from list and diplay in text field.
        stockList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()) {
                    String selection = stockList.getSelectedValue().toString();;
                    symbolField.setText(selection);
                }
            }
        });

        // Select highligthed symbol from list when button is clicked and track this symbol.
        // Verify user is logged in.
        setButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if(m_symbol != null && m_username != null) {
                    if(m_userAuth.isLoggedIn(m_username) && stockTicker.trackStock(m_username, m_symbol, true)) {
                        System.out.println("Now tracking symbol " + m_symbol);
                    }
                    else {
                        System.out.println("Unable to track symbol; user may not be logged in");
                    }
                }
                else {
                    System.out.println("The symbol was not selected; user may not be logged in");
                }
            }
        });


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
        midPanel.add(scrollPane, m_constraints);

        // Layout the JList's set button
        m_constraints.insets = new Insets(0, 0, 30, 5);
        listPanel.add(setButton, m_constraints);
        m_constraints.gridx = 1;
        listPanel.add(symbolField, m_constraints);
        m_constraints.insets = new Insets(0, 0, 0, 0);
        m_statusPanel = new JPanel();
        m_statusPanel.setPreferredSize(new Dimension(650, 65));

        // Create the JPanel containing the UI control buttons
        JPanel btnPanel = new JPanel(new GridBagLayout());
        btnPanel.setPreferredSize(new Dimension(200, 40));

        m_leftControlBtn = new JButton(UI.USER_REG.getName());
        m_rightControlBtn = new JButton(UI.LOGIN.getName());

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
        OperateStockTicker operate = new OperateStockTicker();
        m_leftControlBtn.addActionListener(operate);
        m_rightControlBtn.addActionListener(operate);

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
        setCardLayout();
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
        m_tickerCard = new TickerCard(m_cardPanel);
        m_regCard = new RegistrationCard();
        m_loginCard = new LoginCard();

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
                    m_regCard.clearTextFields();
                    cardLayout.show(m_cardPanel, UI.USER_REG.getName());
                    this.resetLeftButton(UI.SUBMIT.getName());
                    this.resetRightButton("Cancel");
                    m_regSelect = true;
                    //m_logInSelect = false;
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
                    if(m_regSelect) {
                        if((m_firstname = m_regCard.getfirstName()).equals("")) {
                            System.out.println("Firstname field is blank");
                            isEmpty = true;
                        }

                        if((m_lastname = m_regCard.getLastName()).equals("")) {
                            System.out.println("Lastname field is blank");
                            isEmpty = true;
                        }

                        if((m_username = m_regCard.getUsername()).equals("")) {
                            System.out.println("Username field is blank");
                            isEmpty = true;
                        } 

                        if((m_password = m_regCard.getPassword()).equals("")) {
                            System.out.println("Password field is blank");
                            isEmpty = true;
                        }

                        // Registration selcted; register user
                        if(!isEmpty) {
                            m_userInfo = new UserInfo(m_firstname, m_lastname);
                            this.registerUser(m_username, m_password);
                        }
                    }
                    else if(m_logInSelect) {
                        if((m_username = m_loginCard.getUsername()).equals("")) {
                            System.out.println("Username field is blank");
                            isEmpty = true;
                        }

                        if((m_password = m_loginCard.getPassword()).equals("")) {
                            System.out.println("Password field is blank");
                            isEmpty = true;
                        }

                        // User is registered; login user
                        if(!isEmpty) {
                            if(m_userAuth.isRegistered(m_username)) {
                                this.logInUser(m_username, m_password);
                            }
                            else {
                                System.out.println("Not a recognized user, check username or register");
                            }
                        } 
                    }
                    break;


                case UPDATE:
                    if(m_tickerSelect) {
                        cardLayout.show(m_cardPanel, UI.DETAIL.getName());
                        m_tickerSelect = false;
                    } else {
                        cardLayout.show(m_cardPanel, UI.TICKER.getName());
                        m_tickerSelect = true;
                    }

                    break;

                case CANCEL:
                    m_cancelSelect = true;
                case LOGOUT:
                    if(!m_cancelSelect) {
                        if(m_userAuth.logOut(m_username)) {
                            System.out.println("User is logged out");
                        }
                        else {
                            System.out.println("Unable to log user out");
                        }
                    }
                    this.resetLeftButton("Registration");
                    this.resetRightButton("Login");
                    this.resetFlags();
                    cardLayout.show(m_cardPanel, UI.HOME.getName());
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
                }
                else {
                    System.out.println("User login failed, please check password");
                }
            }
            else {
                System.out.println("User is already logged in");
                this.resetFlags();
            }

            // User is logged in,  switch screen to ticker
            if(!m_logInSelect && !m_regSelect) { 
                this.resetLeftButton("Update");
                this.resetRightButton("Logout");
                m_rightControlBtn.setEnabled(true);
                m_tickerSelect = true;
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
                if(m_userAuth.register(username, password, m_userInfo)) {
                    System.out.println("User successfully registered");
                    m_regSelect = false;
                    m_logInSelect = true;
                    //m_isRegistered = true;
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
                        System.out.println("User is registered and logged in");
                        this.resetLeftButton("Update");
                        this.resetRightButton("Logout");
                        this.resetFlags();
                        m_tickerSelect = true;
                        //m_regSelect = false;
                        cardLayout.show(m_cardPanel, UI.TICKER.getName());
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
            //m_logInTries = 0;
            m_cancelSelect = false;
        }


        /**
         * Displays a message the argument message in the status field in UI. The
         * user can set the color of the message and a tool tip. These fields can be
         * set to null if not required.
         * 
         * @param color
         * @param message
         * @param tip
         */
        //@Override
        public void setStatus(Color color, String message, String tip) {

        }
    }
}
