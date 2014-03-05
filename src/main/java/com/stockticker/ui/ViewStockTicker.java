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

import javax.swing.BorderFactory;
//import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.stockticker.SymbolMap;
import com.stockticker.UserInfo;
import com.stockticker.logic.AuthorizationService;
import com.stockticker.logic.UserAuthorization;


/**
 * GUI for Stock Ticker Portfolio Manager J308 Project
 * 
 * @author prwallace
 */
public class ViewStockTicker extends WindowAdapter implements ActionListener,
        IStockTicker_UIComponents {

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

    private String m_username = null;
    private String m_password = null;
    private String m_firstname = null;
    private String m_lastname = null;

    private int m_logInTries = 0;
    private int m_maxTries = 3;

    private AuthorizationService m_userAuth = UserAuthorization.INSTANCE;
    private UserInfo m_userInfo;


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

        // Add controls for the Symbol list controls and their action listeners
        JList<Object> stockList = new JList<>(SymbolMap.getSymbols().keySet()
                .toArray());
        JScrollPane scrollPane = new JScrollPane(stockList);
        scrollPane.setSize(150, 20);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        final JButton setButton = new JButton();
        setButton.setName("Set");
        setButton.setPreferredSize(new Dimension(10, 10));
        setButton.setActionCommand("Set");
        setButton.addActionListener(this);
        stockList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    setButton.doClick();
                }
            }
        });

        final JTextField symbolName = new JTextField(5);
        symbolName.setEditable(true);
        symbolName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // String name = symbolName.getText();

                // Reset the text field.
                symbolName.requestFocusInWindow();
                symbolName.setText("");
            }
        });

        // Create/add child panels to the main frame JPanels and layout their
        // individual controls using GridBagLayout.
        JPanel toolPanel = new JPanel();
        toolPanel.setPreferredSize(new Dimension(160, 180));
        JPanel midPanel = new JPanel(new GridBagLayout());
        midPanel.setPreferredSize(new Dimension(160, 200));
        JPanel btmPanel = new JPanel(new GridBagLayout());
        btmPanel.setPreferredSize(new Dimension(160, 50));

        // Layout the JList JScrollPane
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.ipadx = 10;
        m_constraints.insets = new Insets(50, 0, 0, 0);
        midPanel.add(scrollPane, m_constraints);

        // Layout the JList's set button
        m_constraints.insets = new Insets(0, 0, 30, 5);
        btmPanel.add(setButton, m_constraints);
        m_constraints.gridx = 1;
        btmPanel.add(symbolName, m_constraints);
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
        // m_constraints.insets = new Insets(0, 0, 0, 0);

        // Add action listeners to the control buttons
        m_leftControlBtn.addActionListener(this);
        m_rightControlBtn.addActionListener(this);

        // Add all child JPanels their parent JPanel
        m_toolPanel.add(toolPanel, BorderLayout.NORTH);
        m_toolPanel.add(midPanel, BorderLayout.CENTER);
        m_toolPanel.add(btmPanel, BorderLayout.SOUTH);
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
     * Changes the name of the left control button on the main panel.
     * 
     * @param name
     *            - Name of left main panel button
     */
    public void resetLeftButton(String name) {
        m_leftControlBtn.setText(name);
    }


    /**
     * Changes the name of the right control button on the main panel.
     * 
     * @param name
     *            - Name of right main panel button
     */
    public void resetRightButton(String name) {
        m_rightControlBtn.setText(name);
    }


    /**
     * Override method that receives ActionEvents for the JPanel fields, such as
     * a button press. Determines which event occurred and takes the appropriate
     * action.
     * 
     * @param evt
     *            Registered event
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        UI selection = UI.getType(evt.getActionCommand());
        cardLayout = (CardLayout) (m_cardPanel.getLayout());
        /*String firstname = null;
        String lastname = null;
        String username = null;
        String password = null;*/


        switch(selection) {

            case USER_REG:
                cardLayout.show(m_cardPanel, UI.USER_REG.getName());
                this.resetLeftButton(UI.SUBMIT.getName());
                this.resetRightButton("Cancel");
                m_regSelect = true;
                m_logInSelect = false;
                break;


            case LOGIN:
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
                    if((m_firstname = m_regCard.getfirstName())!= null) {
                        if((m_lastname = m_regCard.getLastName()) != null ) {
                            m_userInfo = new UserInfo(m_firstname, m_lastname);
                        }
                        else {
                            System.out.println("Lastname field is blank");
                        }

                        if((m_username = m_regCard.getUsername()) != null) {
                            if((m_password = m_regCard.getPassword()) != null) {
                                this.registerUser(m_username, m_password);
                            }
                            else {
                                System.out.println("password field if blank");
                            }
                        }
                        else {
                            System.out.println("username field is blank");
                        }
                    }
                    else {
                        System.out.println("Firstname field is blank");
                    } 
                }
                else if(m_logInSelect) {
                    if((m_username = m_regCard.getUsername()) != null) {
                        if((m_password = m_regCard.getPassword()) != null) {
                            if(m_userAuth.isRegistered(m_username)) {
                                this.logInUser(m_username, m_password); 
                            }
                            else {
                                System.out.println("user is not registered; need to register before logon");
                            }
                        }
                        else {
                            System.out.println("password field is blank");
                        }
                    }
                    else {
                        System.out.println("user field is blank");
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
                        System.out.println("user is logged out");
                    }
                    else {
                        System.out.println("unable to log user out");
                    }
                }
                this.resetLeftButton("Registration");
                this.resetRightButton("Login");
                this.resetFlags();
                cardLayout.show(m_cardPanel, UI.HOME.getName());
                m_cancelSelect = false;
                break;

            default:
                System.out.println("failed to select a key");
        }
    }


    public void logInUser(String username, String password) {
        if(!m_userAuth.isLoggedIn(username)) {
            if(m_userAuth.logIn(username, password)) {
                System.out.println("user successfully logged in");
            }
            else {
                System.out.println("user login failed");
            }
        }
        else {
            System.out.println("user is already logged in");
        }

        m_logInSelect = false;
        this.resetLeftButton("Update");
        this.resetRightButton("Logout");
        m_rightControlBtn.setEnabled(true);
        m_tickerSelect = true;
        cardLayout.show(m_cardPanel, UI.TICKER.getName());
    }


    public void registerUser(String username, String password) {
        
        if(!m_userAuth.isRegistered(username)) {
            if(m_userAuth.register(username, password, m_userInfo)) {
                System.out.println("user successfully registered");
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
            if(m_userAuth.isLoggedIn(username)) {
                System.out.println("User is already a registered user, attempting to login...");
                this.logInUser(username, password);
            }
            else {
                System.out.println("User is registered and logged in");
            }
            m_regSelect = false;
        }
    }


    /**
     * Reset all flags to default state
     * 
     */
    private void resetFlags() {
        m_logInSelect = false;
        m_regSelect = false;
        m_logInTries = 0;
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
    @Override
    public void setStatus(Color color, String message, String tip) {

    }

    /**
     * Prints details about GUI frame and panels to the screen. Available for
     * debugging purposes.
     * 
     */
    /*
     * public void displayUIProperties() { System.out.println("frame: " +
     * m_frame); System.out.println("bottom panel: " + m_bottomPanel);
     * System.out.println("side panel(main) " + m_toolPanel);
     * System.out.println("side panel(button) " + m_btnPanel);
     * System.out.println("card panel(main) " + m_cardPanel);
     * System.out.println("bottom2 " + m_statusPanel);
     * System.out.println("btnPanel " + m_btnPanel);
     * 
     * }
     */
}
