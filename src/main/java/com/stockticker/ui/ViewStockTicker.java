/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stockticker.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author prwallace
 */
public class ViewStockTicker extends WindowAdapter implements ActionListener, ItemListener {
    final static String USER_REG = "Registration";
    final static String TICKER = "Ticker";
    final static String QUOTE = "Quote";
    final static int    PWORD_LENGTH = 16;

    private final JFrame m_frame;
    private JPanel btnPanel;
    private JPanel toolPanel;
    private JPanel cardPanel;

    private final Toolkit toolKit = Toolkit.getDefaultToolkit();
    private final Dimension screenSize = toolKit.getScreenSize();
    private JLabel btnPanelLbl;
    private GridBagConstraints m_constraints;

    private String m_password;
    private String m_usrName;
    private String m_icon = "images\\stock_ticker.png";

    /**
     *
     */
    public ViewStockTicker() {
        m_frame = new JFrame("Stock Ticker");
    }


    /**
     *
     * @param evt
     */
    @Override
    public void actionPerformed( ActionEvent evt ) {
        switch(evt.getActionCommand()) {
            case "Login" :
                getLogin();
                break;

            case "Close" :
                m_frame.dispose();
                break;

            default :
                System.out.println("failed to select a key");    
        }
    }


    /**
     *
     */
    public void build() {
        m_frame.setTitle( "Stock Ticker" );
        Image m_titleIcon = new ImageIcon( toolKit.getImage(m_icon)).getImage();
        m_frame.setIconImage(m_titleIcon);
        m_frame.setSize(700, 600);
        m_frame.setLocation(screenSize.width/4, screenSize.height/4);
        JFrame.setDefaultLookAndFeelDecorated(true);
        m_constraints = new GridBagConstraints();

        m_frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent){
                System.out.println("closing");
	        System.exit(0);
            }
        });
    }


    /**
     *
     */
    public void setPanels() {
        btnPanel = new JPanel(new GridBagLayout());
        btnPanel.setPreferredSize( new Dimension(100, 80));
        btnPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder() ) );
        btnPanelLbl = new JLabel("Screen Selection");

        toolPanel = new JPanel();
        toolPanel.setPreferredSize( new Dimension(100, 520));
        toolPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder() ) );

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(80, 420));

        JButton login = new JButton("Login");
        m_constraints.gridx = 0;
        m_constraints.gridy = 1;
        m_constraints.insets = new Insets(350,0,0,0);
        panel.add(login, m_constraints);

        JButton close = new JButton("Close");
        m_constraints.gridy++;
        m_constraints.insets = new Insets(7,0,0,0);
        panel.add(close, m_constraints);

        cardPanel = new JPanel(new CardLayout());
        cardPanel.setPreferredSize(new Dimension(600,520));
        cardPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder() ) );

        btnPanel.add(btnPanelLbl);
        toolPanel.add(panel, BorderLayout.SOUTH);
        m_frame.getContentPane().add(toolPanel, BorderLayout.WEST);
        m_frame.getContentPane().add(btnPanel, BorderLayout.SOUTH);
        m_frame.getContentPane().add(cardPanel, BorderLayout.EAST);

        close.addActionListener(this);
        login.addActionListener(this);

        setCardLayout();
        m_frame.setVisible(true);
    }


    private void setCardLayout() {
        JPanel comboBoxPanel = new JPanel();
        String comboBoxFields[] = {USER_REG, TICKER, QUOTE};
        JComboBox comboBox = new JComboBox(comboBoxFields);
        comboBox.setEditable(false);
        comboBox.addItemListener(this);
        comboBoxPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
        comboBoxPanel.add(comboBox);

        JPanel regCard = new JPanel(new FlowLayout());
        regCard.add(new JButton( "Ok"));
        regCard.add(new JButton("Cancel"));

        JPanel tickerCard = new JPanel(new FlowLayout());
        tickerCard.add(new JTextField("TempField", 20));
        
        JPanel quoteCard = new JPanel(new FlowLayout());
        quoteCard.add(new JButton("Cancel"));

        cardPanel.add(regCard, USER_REG);
        cardPanel.add(tickerCard, TICKER);
        cardPanel.add(quoteCard, QUOTE);

        m_frame.add(comboBoxPanel, BorderLayout.PAGE_START);
        m_frame.add(cardPanel, BorderLayout.CENTER);      
    }


    /**
     *
     * @param evt
     */
    @Override
    public void itemStateChanged(ItemEvent evt){
        CardLayout cards = (CardLayout)(cardPanel.getLayout());
        cards.show(cardPanel, (String)evt.getItem());
    }


    private boolean getLogin() {
        boolean hasUserInput = false;
        JTextField userField = new JTextField();
        JTextField pwordField = new JPasswordField(PWORD_LENGTH);

        //Start over with new username and password
        m_usrName = null;
        m_password = null;

        Object[] message = {
                "Username:", userField,
                "Password:", pwordField
        };

        userField.setCaretPosition(0);
        pwordField.setCaretPosition( 0 );

        int option = JOptionPane.showConfirmDialog(m_frame, message, "User Login", JOptionPane.OK_CANCEL_OPTION);

        if ( option == JOptionPane.OK_OPTION ) {
            if (pwordField.getText() == null) {
                System.out.println("No password entered");
                //m_statusTextField.setForeground( Color.RED );
                //m_statusTextField.setText( "Invalid table name or password entered" );
            }
            else if(userField.getText() == null) {
                System.out.println("Username was not entered");
            } else {
                m_password = pwordField.getText();
                m_usrName = userField.getText();
                hasUserInput = true;
            }
        }

        return hasUserInput;
    }
}
