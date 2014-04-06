/**
 * GUI for Stock Ticker Portfolio Manager
 * 90.308-061 Agile Software Dev. w/Java Project
 * ViewStockTicker.java
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import com.stockticker.StockQuote;
import com.stockticker.SymbolMap;
import com.stockticker.UserInfo;
import com.stockticker.StockHistory;
import com.stockticker.logic.AuthorizationService;
import com.stockticker.logic.BusinessLogicException;
import com.stockticker.logic.BusinessLogicService;
import com.stockticker.logic.StockTickerService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.IOException;


/**
 * ViewStockTicker screen for Stock Ticker Portfolio Manager
 * UI of the Stock Ticker Portfolio Manager api
 * @author prwallace
 */
public class ViewStockTicker extends WindowAdapter implements IComponentsUI {
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

    private JButton m_leftControlBtn;
    private JButton m_rightControlBtn;
    private JButton m_historyBtn;
    private JButton m_logoutBtn;
    private JButton m_profileBtn;
    private JLabel m_nameLbl;
    private JTextField m_symbolField;

    private final Toolkit toolKit = Toolkit.getDefaultToolkit();
    private final Dimension screenSize = toolKit.getScreenSize();
    private GridBagConstraints m_constraints;
    private Image m_titleIcon;

    private HomeCard m_homeCard;
    private QuoteCard m_quoteCard;
    private TickerCard m_tickerCard;
    private RegistrationCard m_regCard;
    private LoginCard m_loginCard;
    private HistoryCard m_historyCard;  

    private boolean m_logInSelect = false;
    private boolean m_regSelect = false;
    private boolean m_closeSelect = false;
    private boolean m_isLoggedIn = false;
    private boolean m_isMultSelect = false;
    private boolean m_historySelect = false;
    private boolean m_profileSelect = false;
    private boolean m_deleteSelect = false;
    private boolean[] m_isInvalidInput = new boolean[5];

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

    private BusinessLogicService bls;
    private final AuthorizationService m_userAuth;
    private final StockTickerService m_stockService;
    private UserInfo m_userInfo;

    private final OperateStockTicker m_operate;


    /**
     * Constructs the ViewStockTicker object
     */
    public ViewStockTicker() {
        bls = BusinessLogicService.INSTANCE;
        bls.start();
        m_userAuth = bls.getUserAuth();
        m_stockService = bls.getStockTicker();

        m_symbolList = new ArrayList<>();
        m_stockQuoteList = new ArrayList<>();
        m_multSymbols = new ArrayList<>();
        m_stockHistoryList = new ArrayList<>();
        m_frame = new JFrame();
        m_operate = new OperateStockTicker();
    }


    /**
     * Builds the main frame and provides a Window Listener to close events from
     * the Title Bar.
     */
    public void build() {
        m_titleIcon = new ImageIcon(toolKit.getImage(m_icon)).getImage();
        m_frame.setIconImage(m_titleIcon);
        m_frame.setSize(940, 600);
        m_frame.setLocation(screenSize.width / 4, screenSize.height / 4);
        m_frame.setTitle("Stock Ticker Portfolio Manager");
        JFrame.setDefaultLookAndFeelDecorated(true);

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
        m_bottomPanel.setPreferredSize(new Dimension(940, 80));

        m_leftPanel = new JPanel();
        m_leftPanel.setPreferredSize(new Dimension(120, 520));

        m_topPanel = new JPanel(new GridBagLayout());
        m_topPanel.setPreferredSize(new Dimension(940, 64));

        m_rightPanel = new JPanel();
        m_rightPanel.setPreferredSize(new Dimension(120, 600));

        // Main JPanel for the CardLayout.  Positioned in center of UI.
        m_cardPanel = new JPanel(new CardLayout());
        m_cardPanel.setLayout(cardLayout = new CardLayout());
        m_cardPanel.setPreferredSize(new Dimension(700, 520));
        m_cardPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));

        // Create the stock symbols JList, its scroll pane, and a symbols text field
        m_symbolJList = new JList<>(SymbolMap.getSymbols().keySet().toArray());
        m_symbolJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        m_symbolJList.setVisibleRowCount(8);
        m_symbolJList.setVisible(false);

        m_scrollPane = new JScrollPane(m_symbolJList);
        m_scrollPane.setSize(150, 20);
        m_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        m_scrollPane.setVisible(false);

        m_symbolField = new JTextField(6);
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


        // Mouse listener for single or double clicks events in JList.  Allows for
        // single selection (double click) or multiple selections (click + cntrl)
        // plus enter.  Selected symbols are set within the Quote text field.
        m_symbolJList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String symbol = null;

                if(e.getClickCount() == 2) {
                    symbol = m_symbolJList.getSelectedValue().toString();
                    m_isMultSelect = false;

                    if(symbol != null) {
                        m_symbolList.add(symbol);
                        m_operate.setSymbolList(); 
                        m_symbolJList.clearSelection();
                    }
                }
                else { // User is making multiple selections in list
                    m_multSymbols.clear();

                    for(Object obj : m_symbolJList.getSelectedValuesList()) {
                        m_multSymbols.add(obj.toString());

                    }

                    m_symbolField.setText(m_symbolJList.getSelectedValue().toString());
                    m_isMultSelect = true;
                }
            }
        });


        // Key listener for JList and symbols text field.  Parses JList for a close
        // match to the user input.  Close matches are then selected in list.
        m_symbolField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                String userInput = null;

                // Move selection up in list view
                int row_loc = m_symbolJList.getSelectedIndex();
                m_symbolJList.ensureIndexIsVisible(row_loc + ROW_OFFSET);

                userInput = m_symbolField.getText();
                if(userInput != null) {
                    String key = m_symbolField.getText().toUpperCase();
                    ListModel listModel = m_symbolJList.getModel();

                    // Parse list for matches to key entry; display in list and text field
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


        // Abstract Action listener that sets the selected stock symbol in JList
        // to the Quote text field when enter is pressed and then released.
        Action enterAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String symbol = null;

                // Entry was typed into JList or symbols text field
                if(!m_isMultSelect) {
                    symbol = m_symbolJList.getSelectedValue().toString();

                    if(symbol != null) {
                        m_symbolField.setText(symbol);
                    }          

                    int row_pos = m_symbolJList.getSelectedIndex();
                    m_symbolJList.ensureIndexIsVisible(row_pos + ROW_OFFSET);

                    m_symbolList.add(symbol);
                    m_operate.setSymbolList();

                }
                else { // Multiple symbols selected with mouse
                    for(String str : m_multSymbols){
                        m_symbolList.add(str);
                    }

                    m_multSymbols.clear();
                    m_operate.setSymbolList();
                }

                m_symbolJList.clearSelection();
                m_symbolField.setText("");
            }
        };

        // Action and Input maps for the symbols list.  Keypresses are bound to the enter button on release
        m_symbolField.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), ENTER_PRESSED);
        m_symbolField.getActionMap().put(ENTER_PRESSED, enterAction);
        m_symbolJList.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), ENTER_PRESSED);
        m_symbolJList.getActionMap().put(ENTER_PRESSED, enterAction);


        // Create the JPanels for the symbols JList and symbols text field.
        JPanel fillerPanel = new JPanel();
        fillerPanel.setPreferredSize(new Dimension(160, 180));

        JPanel listPanel = new JPanel(new GridBagLayout());
        listPanel.setPreferredSize(new Dimension(160, 200));

        JPanel textFieldPanel = new JPanel(new GridBagLayout());
        textFieldPanel.setPreferredSize(new Dimension(160, 50));

        // Layout the JScrollPane for the Symbols JList
        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.ipadx = 10;
        m_constraints.insets = new Insets(50, 0, 0, 0);
        listPanel.add(m_scrollPane, m_constraints);

        // Layout the Symbols text field, under Symbols JList
        m_constraints = new GridBagConstraints();
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
        m_operate.enableHistoryButton(false);
        m_leftControlBtn = new JButton(UI.USER_REG.getName());
        m_rightControlBtn = new JButton(UI.LOGIN.getName());


        // Listen for enter when left control button has focus
        Action m_leftButtonAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                m_leftControlBtn.doClick();
            }
        };

        // Action and Input maps for the left control button
        m_leftControlBtn.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), ENTER_PRESSED);
        m_leftControlBtn.getActionMap().put(ENTER_PRESSED, m_leftButtonAction);

        // Listen for enter when the right control button has focus
        Action rightButtonAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                m_rightControlBtn.doClick();
            }
        };

        // Action and Input maps for the right control button
        m_rightControlBtn.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), ENTER_PRESSED);
        m_rightControlBtn.getActionMap().put(ENTER_PRESSED, rightButtonAction);;

        // Listen for enter when the history control button has focus
        Action historyButtonAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                m_historyBtn.doClick();
            }
        };

        // Action and Input maps for the history control button
        m_historyBtn.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), ENTER_PRESSED);
        m_historyBtn.getActionMap().put(ENTER_PRESSED, historyButtonAction);


        // Add a panel to layout the UI control buttons
        m_componentPanel = new JPanel();
        m_componentPanel.setPreferredSize(new Dimension(400, 65));

        // Layout the history button
        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.anchor = GridBagConstraints.PAGE_START;
        m_constraints.insets = new Insets(0, 0, 2, 10);
        controlBtnPanel.add(m_historyBtn, m_constraints);

        // Layout the left control button
        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 1;
        m_constraints.gridy = 0;
        m_constraints.insets = new Insets(0, 0, 2, 10);
        controlBtnPanel.add(m_leftControlBtn, m_constraints);

        // Layout the right control button
        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 2;
        m_constraints.gridy = 0;
        m_constraints.insets = new Insets(0, 0, 2, 2);
        controlBtnPanel.add(m_rightControlBtn, m_constraints);

        // Layout the buttons control panel onto its parent panel
        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 1;
        m_constraints.gridy = 0;
        m_constraints.insets = new Insets(0, 0, 0, 0);
        m_componentPanel.add(controlBtnPanel, m_constraints);


        // Create the logout and profile buttons and username label
        int width = 100;
        int height = 20;
        Dimension dim = new Dimension(width, height);
        m_nameLbl = new JLabel();
        m_nameLbl.setMaximumSize(dim);
        m_nameLbl.setMinimumSize(dim);
        m_nameLbl.setPreferredSize(dim);
        m_nameLbl.setFocusable(false);
        m_nameLbl.setHorizontalAlignment(SwingConstants.CENTER);
        m_nameLbl.setBorder(BorderFactory.createEtchedBorder());
        m_nameLbl.setVisible(false);

        m_profileBtn = new JButton(UI.PROFILE.getName());
        m_profileBtn.setVisible(false);

        m_logoutBtn = new JButton(UI.LOGOUT.getName());
        m_logoutBtn.setVisible(false);


        // Create JPanel for the logout and profile buttons and username label
        JPanel profilePanel = new JPanel(new GridBagLayout());
        profilePanel.setPreferredSize(new Dimension(120, 60));

        JPanel logoutPanel = new JPanel(new GridBagLayout());
        logoutPanel.setPreferredSize(new Dimension(120, 60));


        // Layout the username label and profile button; add their panel to parent panel
        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.anchor = GridBagConstraints.NORTH;
        m_constraints.insets = new Insets(11, 0, 10, 2);//817
        profilePanel.add(m_nameLbl, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 1;
        m_constraints.anchor = GridBagConstraints.NORTH;
        m_constraints.insets = new Insets(0, 6, 12, 4);
        profilePanel.add(m_profileBtn, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.weightx = 0.1;
        m_constraints.anchor = GridBagConstraints.NORTHEAST;
        m_topPanel.add(profilePanel, m_constraints);


        // Layout the logout button and add the panel to its parent panel
        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.anchor = GridBagConstraints.NORTH;
        m_constraints.insets = new Insets(0, 6, 30, 4);//856
        logoutPanel.add(m_logoutBtn, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.weightx = 0.1;
        m_constraints.anchor = GridBagConstraints.NORTH;
        m_constraints.insets = new Insets(0, 0, 530, 0);//856
        m_rightPanel.add(logoutPanel, m_constraints);


        // Add action listeners for the control buttons
        m_leftControlBtn.addActionListener(m_operate);
        m_rightControlBtn.addActionListener(m_operate);
        m_historyBtn.addActionListener(m_operate);
        m_profileBtn.addActionListener(m_operate);
        m_logoutBtn.addActionListener(m_operate);

        // Add all child JPanels to their parent JPanel
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
        // Throws an I/O Exception if unable to retrieve icon in Home screen
        try {
            m_homeCard = new HomeCard();
        } catch(IOException ex) {
            m_message = new StringBuilder("Error: Exception in home screen\n");
            m_message.append(ex.getMessage());
            m_operate.showError(m_message.toString());
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
         * @param name  - name of left main panel button
         */
        public void resetLeftButton(String name) {
            m_leftControlBtn.setVisible(true);
            m_leftControlBtn.setText(name);
        }


        /**
         * Changes the name of the right control button on the main panel and sets
         * whether this control is visible or not.
         * 
         * @param name  - name of right main panel button
         * @param visible   - right control button is visible(true) or not visible(false)
         */
        public void resetRightButton(String name, boolean visible) {
            m_rightControlBtn.setText(name);
            m_rightControlBtn.setVisible(visible);
        }


        /**
         * Action listener for the control buttons on the main JPanel.  The actions
         * from the control buttons provide the switching between screens, login-
         * logout, registration, and updating user information.  Also provides
         * validation checking of user input.
         * 
         * @param evt   - registered event from the control buttons
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
                    m_leftControlBtn.grabFocus();
                    m_regCard.enableProfileForm("", "", "", true);
                    cardLayout.show(m_cardPanel, UI.USER_REG.getName());
                    m_rightControlBtn.transferFocus();
                    this.resetLeftButton(UI.SUBMIT.getName());
                    this.resetRightButton(UI.CLOSE.getName(), true);
                    break;

                // Opens login form
                case LOGIN:
                    this.resetFlags();
                    m_logInSelect = true;
                    m_loginCard.clearTextFields();
                    this.resetLeftButton(UI.SUBMIT.getName());
                    this.resetRightButton(UI.CLOSE.getName(), true);
                    cardLayout.show(m_cardPanel, UI.LOGIN.getName());
                    m_rightControlBtn.transferFocus();
                    break;

                // Saves/verifies User Info, registers, updates, and login-logout
                // the user into the api.
                case SUBMIT:
                    m_leftControlBtn.requestFocusInWindow();
                    this.resetValidation();
                    m_message = new StringBuilder("Warning: ");

                    // User is in registration screen
                    if(m_regSelect) {
                        if((m_firstname = m_regCard.getfirstName(true)).isEmpty() || m_regCard.getfirstName(true).startsWith(SPACE)) {
                            m_message.append("Firstname field is blank\n");      
                            m_firstname = "";
                            m_isInvalidInput[Field.FIRST_NM.getValue()] = true; 
                            isEmpty = true;
                        }

                        if((m_lastname = m_regCard.getLastName(true)).isEmpty() || m_regCard.getLastName(true).startsWith(SPACE)) {
                            m_message.append("Lastname field is blank\n");
                            m_lastname = "";
                            m_isInvalidInput[Field.LAST_NM.getValue()] = true;
                            isEmpty = true;
                        }

                        if((m_username = m_regCard.getUsername()).isEmpty() || m_regCard.getUsername().startsWith(SPACE)) {
                            m_message.append("Username field is blank\n");
                            m_username = "";
                            m_isInvalidInput[Field.USER.getValue()] = true;
                            isEmpty = true;
                        } 

                        if((m_password = m_regCard.getPassword()).isEmpty() || m_regCard.getPassword().startsWith(SPACE)) {
                            m_message.append("Password field is blank\n");
                            m_password = "";
                            m_isInvalidInput[Field.PASSWD.getValue()] = true;
                            isEmpty = true;
                        }

                        if((m_verifyPasswd = m_regCard.getVerifiedPasswd()).isEmpty() || m_regCard.getVerifiedPasswd().startsWith(SPACE)) {
                            m_message.append("Re-enter password field is blank\n");
                            m_verifyPasswd = "";
                            m_isInvalidInput[Field.VER_PASS.getValue()] = true;
                            isEmpty = true;
                        }

                        // Verify both entered passwords match
                        if(!isEmpty && !m_verifyPasswd.equals(m_password)) {//equalsIgnoreCase
                           m_message.append("The entered passwords do not match\n");
                           m_message.append("Please re-enter the passwords");
                           m_regCard.clearPasswordFields();
                           m_isInvalidInput[Field.PASSWD.getValue()] = true;
                           isEmpty = true;
                        }

                        // Register user
                        if(!isEmpty) {   
                            this.registerUser(m_username, m_password);
                        }
                        else {
                            this.showDialog(m_message.toString(), JOptionPane.WARNING_MESSAGE);
                            m_regCard.setFocusInField(m_isInvalidInput);
                        }
                    } // User is in Login screen
                    else if(m_logInSelect) {
                        if((m_username = m_loginCard.getUsername()).isEmpty() || m_loginCard.getUsername().startsWith(SPACE)) {
                            m_message.append("Username field is blank\n");
                            m_username = "";
                            m_isInvalidInput[Field.USER.getValue()] = true;
                            isEmpty = true;
                        }

                        if((m_password = m_loginCard.getPassword()).isEmpty() || m_loginCard.getPassword().startsWith(SPACE)) {
                            m_message.append("Password field is blank\n");
                            m_password = "";
                            m_isInvalidInput[Field.PASSWD.getValue()] = true;
                            isEmpty = true;
                        }

                        // Login user & verify they are a registered user
                        if(!isEmpty) {
                            if(m_userAuth.isRegistered(m_username)) {
                                this.logInUser(m_username, m_password);
                            }
                            else {
                                m_message = new StringBuilder("Warning: Not a recognized user, check username or register");
                                m_username = "";
                                m_password = "";
                                m_loginCard.clearTextFields();
                                this.showDialog(m_message.toString(), JOptionPane.WARNING_MESSAGE);
                            }
                        }
                        else {
                            this.showDialog(m_message.toString(), JOptionPane.WARNING_MESSAGE);
                            m_loginCard.setFocusInField(m_isInvalidInput);
                        }
                    } // User is in History screen
                    else if(m_historySelect) {
                        m_startDate = m_historyCard.getStartDate();
                        m_endDate = m_historyCard.getEndDate();
                        this.showStockHistory();
                    } // User is in the Profile
                    else if(m_profileSelect) {
                        String firstname = "";
                        String lastname = "";

                        if((firstname = m_regCard.getfirstName(false)).isEmpty() || m_regCard.getfirstName(false).startsWith(SPACE)) {
                            m_message.append("Firstname field is blank\n");      
                            firstname = "";
                            m_isInvalidInput[Field.FIRST_NM.getValue()] = true; 
                            isEmpty = true;
                        }

                        if((lastname = m_regCard.getLastName(false)).isEmpty() || m_regCard.getLastName(false).startsWith(SPACE)) {
                            m_message.append("Lastname field is blank\n");
                            lastname = "";
                            m_isInvalidInput[Field.LAST_NM.getValue()] = true;
                            isEmpty = true;
                        }

                        if(!isEmpty) {   
                            if(!m_userInfo.getFirstName().equals(firstname) || !m_userInfo.getLastName().equals(lastname)) {
                                m_userInfo.setFirstName(firstname);
                                m_userInfo.setLastName(lastname);
                                if(m_userAuth.updateUserInfo(m_username, m_userInfo)) {
                                    m_firstname = m_userAuth.getUserInfo(m_username).getFirstName();
                                    m_lastname = m_userAuth.getUserInfo(m_username).getLastName();
                                    this.showDialog("User Information has been updated\nPlease press Close to continue", JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                            else {
                                this.showDialog("Please press Close to exit", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                        else {
                            this.showDialog(m_message.toString(), JOptionPane.WARNING_MESSAGE);
                            m_regCard.setFocusInField(m_isInvalidInput);
                        }
                    }
                    break;

                // Refresh the stock quote table.  Get the stock quotes from the 
                // table and add them to a temp symbols List<String>.  Call
                // getStockQuotes and re-display the stock quote list in table.
                case REFRESH:
                    List<String> refreshList = new ArrayList<>();
                    refreshList = m_tickerCard.getSymbolsInTable();
                    m_stockQuoteList.clear();

                    try {
                        m_stockQuoteList = m_stockService.getStockQuotes(refreshList);
                    }
                    catch(BusinessLogicException ex) {
                        this.showError(ex.getMessage());
                    }

                    if(m_stockQuoteList.size() > ZERO) {
                        m_tickerCard.displayStockQuoteList(m_stockQuoteList);
                    }
                    else {
                        m_message = new StringBuilder("Warning: No stocks listed in table to refresh");
                        this.showDialog(m_message.toString(), JOptionPane.WARNING_MESSAGE);
                    }

                    m_tickerCard.setQuoteFieldFocus();
                    break;

                // Calls business logic to track the selected stock quote
                case TRACK:
                    this.resetFlags();
                    m_stockService.trackStock(m_username, m_tickerCard.getSelectedStock().getSymbol(), true);
                    this.resetLeftButton(UI.UNTRACK.getName());
                    break;

                // Calls business logic to untrack selected stock quote
                case UNTRACK:
                    this.resetFlags();
                    m_stockService.trackStock(m_username, m_tickerCard.getSelectedStock().getSymbol(), false);
                    this.resetLeftButton(UI.TRACK.getName());
                    break;

                // Switch to the History screen
                case HISTORY:
                    this.resetFlags();
                    m_historySelect = true;
                    this.enableHistoryButton(false);

                    // Set history tables title border from selected stock
                    StringBuilder sb = new StringBuilder();
                    sb.append(m_tickerCard.getSelectedStock().getName()).append(" (").append(m_tickerCard.getSelectedStock().getSymbol()).append(")");
                    m_historyCard.setTableBorder(sb.toString());

                    cardLayout.show(m_cardPanel, UI.HISTORY.getName());
                    this.resetLeftButton("Submit");
                    m_leftControlBtn.grabFocus();
                    break;

                // User is updating their user informaiton
                case PROFILE:
                    this.resetFlags();
                    m_profileSelect = true;
                    m_regCard.clearTextFields();
                    this.enableHistoryButton(false);
                    this.enableButtons(false);
                    this.enableSymbolJList(false);
                    m_regCard.enableProfileForm(m_username, m_firstname, m_lastname, false);
                    this.resetLeftButton(UI.SUBMIT.getName());
                    this.resetRightButton(UI.CLOSE.getName(), true);
                    cardLayout.show(m_cardPanel, UI.USER_REG.getName());
                    break;

                // User is changing their password
                case UPDATE:
                    this.changePassword();
                    m_leftControlBtn.grabFocus();
                    break;

                case DELETE:
                    this.resetFlags();
                    m_deleteSelect = true;
                    m_message = new StringBuilder();

                    if(m_userAuth.unRegister(m_username)) {
                        m_message.append(m_username).append(" is no longer a registered user");
                        this.showDialog(m_message.toString(), JOptionPane.INFORMATION_MESSAGE);
                        this.resetLeftButton(UI.LOGOUT.getName());
                        m_leftControlBtn.doClick();
                    }
                    else {
                        m_message.append("Warning: Unable to unregister ").append(m_username).append(" at this time\nPlease try again");
                        this.showDialog(m_message.toString(), JOptionPane.WARNING_MESSAGE);
                    }

                // Close current screen, open screen 1 layout below this one.  
                case  CLOSE:
                    m_closeSelect = true;

                    // Close selected from Registration or LogIn screen
                    if(!m_userAuth.isRegistered(m_username) || !m_isLoggedIn) {
                        this.resetLeftButton(UI.USER_REG.getName());
                        this.resetRightButton(UI.LOGIN.getName(), true);
                        this.enableButtons(false);
                        cardLayout.show(m_cardPanel, UI.HOME.getName());
                        m_leftControlBtn.requestFocusInWindow();
                    }       // Close Profile screen
                    else if(m_profileSelect) {
                        m_regCard.clearTextFields();
                        m_userAuth.logOut(m_username);
                        m_logInSelect = true;
                        m_regCard.enableProfileForm("", "", "", true);
                        this.logInUser(m_username, m_password);;
                    }
                    else {  // Close Quote screen
                        this.resetLeftButton(UI.REFRESH.getName());
                        this.resetRightButton(UI.LOGOUT.getName(), false); 
                        this.enableSymbolJList(true);
                        this.enableHistoryButton(false);
                        m_historyCard.clearHistory();
                         m_tickerCard.setQuoteFieldFocus();
                        cardLayout.show(m_cardPanel, UI.TICKER.getName());
                    }
                    this.resetFlags();
                    break;

                // Logs out this user from db and business logic
                case LOGOUT:
                        if(m_deleteSelect || m_userAuth.logOut(m_username)) {
                            m_isLoggedIn = false;       
                            this.enableSymbolJList(false);
                            m_tickerCard.clearStockList();
                            m_quoteCard.clearQuote();
                            m_historyCard.clearHistory();
                            m_symbolList.clear();
                            m_stockQuoteList.clear();
                            m_nameLbl.setText("");
                            m_symbolField.setText("");
                        }
                        else {
                            m_message = new StringBuilder("Warning: Unable to log user out, please try again");
                            this.showDialog(m_message.toString(), JOptionPane.WARNING_MESSAGE);
                        }

                    this.resetLeftButton(UI.USER_REG.getName());
                    this.resetRightButton(UI.LOGIN.getName(), true);
                    this.enableHistoryButton(false);
                    this.enableButtons(false);
                    m_regCard.enableProfileForm("", "", "", false);
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
         * @param username  - name entered into username field
         * @param password  - password entered into password field
         */
        private void logInUser(String username, String password) {
            m_message = new StringBuilder("Warning: ");

                if(!m_userAuth.isLoggedIn(username)) {
                    if(m_userAuth.logIn(username, password)) {
                        m_nameLbl.setText(m_username);
                        m_isLoggedIn = true;
                        m_userInfo = m_userAuth.getUserInfo(m_username);
                        m_firstname = m_userInfo.getFirstName();
                        m_lastname = m_userInfo.getLastName();
                    }
                    else {
                        m_message.append("User login failed, please check password\n");
                        m_username = "";
                        m_password = "";
                        this.showDialog(m_message.toString(), JOptionPane.WARNING_MESSAGE);
                        m_regCard.clearPasswordFields();
                        m_isInvalidInput[Field.PASSWD.getValue()] = true;

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

            // User is logged in, show Ticker screen and its components
            if(m_isLoggedIn && m_regSelect || m_isLoggedIn && m_logInSelect) { 
                this.resetLeftButton(UI.REFRESH.getName());
                this.resetRightButton(UI.LOGOUT.getName(), false);
                this.enableButtons(true);
                this.showTrackedStocks();
                this.enableSymbolJList(true);
                cardLayout.show(m_cardPanel, UI.TICKER.getName());
                m_tickerCard.setQuoteFieldFocus();
            }       
        }


        /**
         * Registers the user and user information with the business logic and db.
         * Will show a warning message if user is already registered or the user-
         * name exists with a different registered user.
         * 
         * @param username  - username entered into username field of registration screen
         * @param password  - password entered into password field of registration screen
         */
        private void registerUser(String username, String password) {
            m_message = new StringBuilder("Warning: ");

            if(!m_userAuth.isRegistered(username)) {
                m_userInfo = new UserInfo(m_firstname, m_lastname);
                if(m_userAuth.register(username, password, m_userInfo)) {
                    this.logInUser(username, password);
                }
                else {
                    m_message.append("Unable to verify credentials, please try again\n");
                    m_isInvalidInput[Field.PASSWD.getValue()] = true;
                    m_regCard.clearPasswordFields();
                    m_regCard.setFocusInField(m_isInvalidInput);
                    this.showDialog(m_message.toString(), JOptionPane.WARNING_MESSAGE);
                }   
            }
            else {
                m_isInvalidInput[Field.USER.getValue()] = true;
                m_regCard.clearTextFields();
                m_regCard.setFocusInField(m_isInvalidInput);
                m_message.append("A registered user already exists with the same user informaiton\nPlease login or re-register using a different username");
                this.showDialog(m_message.toString(), JOptionPane.WARNING_MESSAGE);
            }
        }


        /**
         * Resets all flags to default state.
         * These flags control the operation of the main control buttons.
         */
        private void resetFlags() {
            m_logInSelect = false;
            m_regSelect = false;
            m_closeSelect = false;
            m_historySelect = false;
            m_profileSelect = false;
            m_deleteSelect = false;
        }


        /**
         * Resets the validation boolean array to false.  Array indicates the entered
         * input is valid (false) or invalid(true) for each user input field..
         */
        private void resetValidation() {
            for(int i = 0; i < m_isInvalidInput.length; i++) {
                m_isInvalidInput[i] = false;
            }
        }


        /**
         * Gets/returns the left control button.
         * This is main control button in UI.  This button name state depending
         * on the active screen.
         * 
         * @return  - The left control button
         */
        public JButton getLeftControlBtn() {
           return m_leftControlBtn;
        }


        /**
         * Verifies with Business Logic whether the StockQuote associated with this
         * symbol is being tracked.
         * 
         * @param symbol    - symbol from StockQuote to check tracking status
         * @return  - true if StockQuote is tracked
         */
        public boolean getTrackingStatus(String symbol) {
            boolean isTracked = false;

            try {
                isTracked = m_stockService.isStockTracked(m_username, symbol);//
            }
            catch(BusinessLogicException ex) {
                this.showError(ex.getMessage());
            }

            return isTracked;
        }


        /**
         * Sets the focus of either the left or right control buttons, depending on
         * the state of the argument(true = left control button, false = right
         * control button).
         * 
         * @param leftBtn   - Grabs focus for left(true) or right(false) control buttons
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
         * Gets symbols from JList and add them to the quote text field.
         */
        public void setSymbolList() {
            if(m_symbolList.size() > ZERO) {
                m_tickerCard.setSymbolsTextField(m_symbolList);
            }
        }


        /**
         * Gets/Displays the StockQuote's in the stock quote table based on the
         * selected symbols in the Quote text field.  Retrieves the tracked stocks
         * from the table(if any), validates the symbols list, and calls the business
         * logic, then displays the returned stock quotes in the table.  
         *
         * @param symbols   - stock symbols from JList
         */
        public void showStockQuoteList(List<String> symbols) {
            List<String> tableSymbols = new ArrayList<>();
            m_message = new StringBuilder("Warning: ");

            if(symbols.size() > ZERO) { 
                tableSymbols = m_tickerCard.getSymbolsInTable();
                this.validateSymbols(symbols);

                for(String str : tableSymbols) {
                    symbols.add(str);
                }

                m_stockQuoteList.clear();

                try {
                    m_stockQuoteList = m_stockService.getStockQuotes(symbols);
                }
                catch(BusinessLogicException ex) {
                    this.showError(ex.getMessage());
                }

                if(m_stockQuoteList.size() > ZERO) {
                    m_tickerCard.displayStockQuoteList(m_stockQuoteList);
                }
                else {
                    m_message.append("Unable to retreive Stock Quotes from service, please try again");
                    this.showDialog(m_message.toString(), JOptionPane.WARNING_MESSAGE);
                }
            }
            else {
                m_message.append("Quote text field is empty or the symbols added are invalid\nPlese try again");
                this.showDialog(m_message.toString(), JOptionPane.WARNING_MESSAGE);
            }

            m_symbolList.clear();
        }


        /**
         * Get stocks tracked by user from the db and displays them in the stock quote table.
         */
        private void showTrackedStocks() {
            List<String> trackedStockes = new ArrayList<>();

            try {
                trackedStockes = m_stockService.getTrackedStocks(m_username);
            }
            catch(BusinessLogicException ex) {
                this.showError(ex.getMessage());
            }

            if(trackedStockes.size() > ZERO) {

                try {
                    m_stockQuoteList = m_stockService.getStockQuotes(trackedStockes);
                }
                catch(BusinessLogicException ex) {
                    this.showError(ex.getMessage());
                }

                if(m_stockQuoteList.size() > ZERO) {
                    m_tickerCard.displayStockQuoteList(m_stockQuoteList);
                }
            }  
        }


        /**
         * Displays a StockHistory for a given stock over of range of entered
         * dates.  Verifies the date range is a valid range prior to calling Business
         * Logic.  If an invalid range, a warning dialog is popped up.
         */
        public void showStockHistory() {
            m_message = new StringBuilder("Warning: ");
            Date today = new Date();
            boolean fault = false;

            try {
                if(m_startDate.compareTo(today) >= ZERO) {
                    m_message.append("The start date is equal to or greater than today's date\nPlease select a day before today and re-submit querry");
                    fault = true;
                }
                else if(m_endDate.compareTo(today) > ZERO ) {
                    m_message.append("The end date is equal to or greater than today's date\nPlease select a day before today and re-submit querry");
                    fault = true;
                }
                else {
                    m_stockHistoryList = m_stockService.getStockHistory(m_tickerCard.getSelectedStock().getSymbol(), m_startDate, m_endDate);

                    if(m_stockHistoryList.size() != ZERO) {
                        m_historyCard.displayHistory(m_stockHistoryList);
                    }
                    else {
                        m_message.append("No data to report for this date range\nPlease select another date range and re-submit");
                        fault = true;
                    }
                }
            }
            catch(IllegalArgumentException e) {
                m_message.append(e.getMessage());
                //this.showDialog(m_username, ZERO);
                fault = true;
            }
            catch(BusinessLogicException ex) {
                m_message.append(ex.getMessage());
                //this.showError(ex.getMessage());
                fault = true;
            }
            finally {
                if(fault) {
                    this.showError(m_message.toString());
                    m_historyCard.clearHistory();
                }
            }
        }


        /**
         * Validates the user entered stock symbols match symbols within the 
         * symbols JList.  Removes user entered symbols if a match is not found.
         * Returns a clean list of stock symbols to send to business logic.
         * 
         * @param symbols   - list of symbols entered by user in quote text field
         * @return          - a clean list of symbols
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
         * Sets the visible property of the symbols JList.  This component is
         * not visible unless user is on main screen.
         * 
         * @param visible    - set visible m_symbolJList
         */
        public void enableSymbolJList(boolean visible) {
            m_symbolJList.setVisible(visible);
            m_scrollPane.setVisible(visible);
            m_symbolField.setVisible(visible);
             m_symbolJList.ensureIndexIsVisible(0);
        }


        /**
         * Sets the visible property for the profile and logout buttons.
         * 
         * @param visible   - set visible login button, profile button, and username label
         */
        public void enableButtons(boolean visible) {
            m_logoutBtn.setVisible(visible);
            m_profileBtn.setVisible(visible);
            m_nameLbl.setVisible(visible);
        }


        /**
         * Calls business logic to un-track the Stock associated with the argument
         * Stock symbol.
         * 
         * @param symbol    - symbol of Stock to cancel tracking on.
         * @return  - true tracking stopped, false unable to stop tracking
         */
        public boolean untrackStock(String symbol) {
            return m_stockService.trackStock(m_username, symbol, false);
        }

        
        /**
         *  Makes the History button visible or non visible, depending on the argument
         *  state, true or false.
         * 
         * @param visible   - set the visible property; true = visible, false = not visible
         */
        public void enableHistoryButton(boolean visible) {
                m_historyBtn.setVisible(visible);
        }

        
        /**
         * Pops up a dialog using the string argument as the message content and
         * the JOptionPane argument as the dialog type.
         * 
         * @param message   - message to display in dialog
         * @param type      - type of dialog message
         */
        public void showDialog(String message, int type ) {
            JOptionPane.showMessageDialog( m_frame, message, "Stock Ticker Portfolio", type);
        }


        /**
         * Pops up an error dialog using the String argument as the message content.
         * 
         * @param message   - message to display in dialog
         */
        public void showError( String message ) {
            JOptionPane.showMessageDialog( m_frame, message, "Stock Ticker Portfolio", JOptionPane.ERROR_MESSAGE);
        }


        /**
         * Password dialog message to allow user to change their registered password.
         * Provides to fields, the password field and a verification field.
         * 
         * @return  - true if the password was updated
         */
        private boolean changePassword() {
            m_message = new StringBuilder();
            String password = null;
            String verified;
            boolean isUpdated = false;

            JLabel passwordLbl = new JLabel("Password:");
            JPasswordField passwordField = new JPasswordField(20);
            passwordField.setEchoChar('*');

            JLabel verifyLbl = new JLabel("Verify:");
            JPasswordField verifyField = new JPasswordField(20);
            verifyField.setEchoChar('*');

            // Required to grab focus in password field
            passwordField.addAncestorListener(new AncestorListener() {
                @Override
                public void ancestorAdded(AncestorEvent evt) {
                    evt.getComponent().grabFocus();
                }

                @Override
                public void ancestorRemoved(AncestorEvent evt) {}

                @Override
                public void ancestorMoved(AncestorEvent evt) {}
            });


            Object[] message = {passwordLbl, passwordField, verifyLbl, verifyField};
            int option = JOptionPane.showConfirmDialog( m_frame, message, "Update User Password", JOptionPane.OK_CANCEL_OPTION);

            if ( option == JOptionPane.OK_OPTION ) {
                password = String.valueOf(passwordField.getPassword());
                verified = String.valueOf(verifyField.getPassword());

                if(password.isEmpty() && verified.isEmpty()) {
                    m_message.append("One or both of password fields are blank\n");
                    isUpdated = true;
                }
                else if(!password.equals(verified)) {
                   m_message.append("The entered passwords do not match\n");
                   m_message.append("Please re-enter the passwords");
                }
                else if(m_password.equals(password)) {
                    m_message.append("The entered is the same as previous password\n");
                    m_message.append("Please re-enter the passwords");
                }
                else if(m_userAuth.changePassword(m_username, m_password, password)) {
                    m_password = password;
                    m_message.append("Password has been updated");
                }
                else {
                    m_message.append("A problem occured updating password, please try again");
                }

                this.showDialog(m_message.toString(), JOptionPane.INFORMATION_MESSAGE);
            }

            return isUpdated;
        }
    }
}
