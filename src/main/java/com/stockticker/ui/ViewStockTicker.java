/*
 * 
 * 
 * 
 */

package com.stockticker.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.stockticker.SymbolMap;

/*
 * Different LookAndFeel's that are available
 * Motif:   "com.sun.java.swing.plaf.motif.MotifLookAndFeel"
 * Metal:   "javax.swing.plaf.metal.MetalLookAndFeel"
 *  or use UIManager.getCrossPlatformLookAndFeelClassName()
 * System: UIManager.getSystemLookAndFeelClassName()
 * GTK:     "com.sun.java.swing.plaf.gtk.GTKLookAndFeel"
 * 
 * 
 */

/**
 * 
 * @author prwallace
 */
public class ViewStockTicker extends WindowAdapter implements ActionListener,
        IStockTicker_UIComponents {

    final static int PWORD_LENGTH = 16;
    static CardLayout cardLayout;
    static JPanel m_cardPanel;

    private final JFrame m_frame;
    private JPanel m_bottomPanel;
    private JPanel m_toolPanel;
    private JPanel m_topPanel;
    private JPanel m_sidePanel;

    private JPanel m_btnPanel;
    private JPanel m_statusPanel;

    private final Toolkit toolKit = Toolkit.getDefaultToolkit();
    private final Dimension screenSize = toolKit.getScreenSize();
    private GridBagConstraints m_constraints;
    private Image m_titleIcon;

    private String m_password;
    private String m_usrName;
    private final String m_icon = "images\\stock_ticker.png";
    //private final String m_icon = "images\\tickerIcon_2.png";
    //private final String m_icon = "images\\tickerIcon.png";
    

    /**
     *
     */
    public ViewStockTicker() {
        m_frame = new JFrame("Stock Ticker Portfolio Manager");
        
    }


    /**
     * Builds the main frame and provides a Window Listener to listen for close
     * events.
     */
    public void build() {
        
        /*try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            //UIManager.getCrossPlatformLookAndFeelClassName();
        }
        catch(ClassNotFoundException |UnsupportedLookAndFeelException | InstantiationException |IllegalAccessException e) {
            System.err.println("Problem with locating current Look and Feel");
            System.err.println(e.getMessage());
            e.printStackTrace();
        }*/

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
     * Sets up the main panels on the UI and adds their associated fields. Calls
     * setCardLayout to setup the panels and fields for the individual cards.
     */
    public void setPanels() {
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
        m_cardPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), 
                BorderFactory.createLoweredBevelBorder()));

        JList stockList = new JList(SymbolMap.getSymbols().keySet().toArray());
        JScrollPane scrollPane = new JScrollPane(stockList);
        scrollPane.setSize(150, 20);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        final JButton setButton = new JButton();;
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
                //String name = symbolName.getText();

                // Reset the text field.
                symbolName.requestFocusInWindow();
                symbolName.setText("");
            }
        });

        JPanel toolPanel = new JPanel();
        toolPanel.setPreferredSize(new Dimension(160, 180));
        JPanel midPanel = new JPanel(new GridBagLayout());
        midPanel.setPreferredSize(new Dimension(160, 200));
        JPanel btmPanel = new JPanel(new GridBagLayout());
        btmPanel.setPreferredSize(new Dimension(160, 50));

        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.ipadx = 10;
        m_constraints.insets = new Insets(50, 0, 0, 0);
        midPanel.add(scrollPane, m_constraints);
        m_constraints.insets = new Insets(0, 0, 30, 5);
        btmPanel.add(setButton, m_constraints);
        m_constraints.gridx = 1;
        btmPanel.add(symbolName, m_constraints);
        m_constraints.insets = new Insets(0, 0, 0, 0);
        m_statusPanel = new JPanel();
        m_statusPanel.setPreferredSize(new Dimension(650, 65));

        JPanel btnPanel = new JPanel(new GridBagLayout());
        btnPanel.setPreferredSize(new Dimension(200, 40));
        JButton regBtn = new JButton(UIButton.USER_REG.getName());
        JButton logBtn = new JButton(UIButton.LOGIN.getName());
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.anchor = GridBagConstraints.PAGE_START;
        m_constraints.insets = new Insets(0, 0, 10, 10);
        btnPanel.add(regBtn, m_constraints);
        m_constraints.gridx = 1;
        m_constraints.gridy = 0;
        m_constraints.insets = new Insets(0, 0, 0, 0);
        btnPanel.add(logBtn, m_constraints);
        m_constraints.insets = new Insets(0, 0, 0, 0);

        regBtn.addActionListener(this);
        logBtn.addActionListener(this);

        m_toolPanel.add(toolPanel, BorderLayout.NORTH);
        m_toolPanel.add(midPanel, BorderLayout.CENTER);
        m_toolPanel.add(btmPanel, BorderLayout.SOUTH);
        m_statusPanel.add(btnPanel, BorderLayout.NORTH);
        m_bottomPanel.add(m_statusPanel, BorderLayout.CENTER);
        m_frame.getContentPane().add(m_bottomPanel, BorderLayout.SOUTH);
        m_frame.getContentPane().add(m_topPanel, BorderLayout.NORTH);
        m_frame.getContentPane().add(m_cardPanel, BorderLayout.CENTER);
        m_frame.getContentPane().add(m_toolPanel, BorderLayout.WEST);
        m_frame.getContentPane().add(m_sidePanel, BorderLayout.EAST);

        setCardLayout();
        m_frame.setResizable(false);
        m_frame.setVisible(true);
    }


    /**
     *
     */
    private void setCardLayout() {
        HomeCard homeCard = null;

        try {
            homeCard = new HomeCard();
        }
        catch(IOException ex) {
            System.err.println("Exception attempting to load or retrieve splash image");
            System.err.println(ex.getMessage());
        }

        DetailCard detailCard = new DetailCard();
        QuoteCard quoteCard = new QuoteCard();
        TickerCard tickerCard = new TickerCard();
        RegistrationCard regCard = new RegistrationCard();
        LoginCard loginCard = new LoginCard();

        m_cardPanel.add(homeCard.getCard());
        m_cardPanel.add(detailCard.getCard());
        m_cardPanel.add(quoteCard.getCard());
        m_cardPanel.add(tickerCard.getCard());
        m_cardPanel.add(regCard.getCard());
        m_cardPanel.add(loginCard.getCard());
        m_frame.add(m_cardPanel, BorderLayout.CENTER);
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
        UIButton selection = UIButton.getType(evt.getActionCommand());
        cardLayout = (CardLayout) (m_cardPanel.getLayout());

        switch(selection) {

            case USER_REG:
                cardLayout.next(m_cardPanel);
                break;

            case LOGIN:
                cardLayout.next(m_cardPanel);
                break;

            default:
                System.out.println("failed to select a key");
        }
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


    public void displayUIProperties() {
        System.out.println("frame: " + m_frame);
        System.out.println("bottom panel: " + m_bottomPanel);
        System.out.println("side panel(main) " + m_toolPanel);
        System.out.println("side panel(button) " + m_btnPanel);
        System.out.println("card panel(main) " + m_cardPanel);
        System.out.println("bottom2 " + m_statusPanel);
        System.out.println("username: " + m_usrName);
        System.out.println("password: " + m_password);
        System.out.println("constraints " + m_constraints);
        System.out.println("btnPanel " + m_btnPanel);

    }
}
