/**
 * GUI for Stock Ticker Portfolio Manager
 * J308 Project
 * Paul Wallace
 */

package com.stockticker.ui;

import com.stockticker.ui.ViewStockTicker.OperateStockTicker;
import java.util.Arrays;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EtchedBorder;


/**
 * LoginCard class
 * Provides fields to allow a user to log into the Stock Ticker Portfolio Manager
 * api
 * 
 * @author prwallace
 */
public class LoginCard extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final String ENTER_PRESSED = "ENTER_RELEASED";

    private JPanel m_loginCard;
    private final GridBagConstraints m_constraints;

    private JTextField m_usernameField;
    private JPasswordField m_passwordField;

    private final OperateStockTicker m_operate;


    
    /**
     * Constructor for the LoginCard class
     * 
     * @param operate   - Instance variable of the OperateStockTicker class
     */
    public LoginCard(OperateStockTicker operate) {
        m_constraints = new GridBagConstraints();
        m_operate = operate;
        setCard();
    }


   /**
    * Adds the components and the components panel for this screen.  Lays out
    * the components onto the component panel and adds the component panel to the
    * Card panel.
    */
    public final void setCard() {
        m_loginCard = new JPanel(new GridBagLayout());
        m_loginCard.setPreferredSize(new Dimension(550, 520));

        JLabel userLbl = new JLabel("User Name:");
        JLabel passLbl = new JLabel("Password:");

        m_usernameField = new JTextField(40);
        m_usernameField.setText("");

        m_passwordField = new JPasswordField(20);
        m_passwordField.setEchoChar('*');
        m_passwordField.setText("");

        Action enterAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               m_operate.getLeftControlBtn().doClick();
            }
        };

        m_passwordField.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), ENTER_PRESSED);
        m_passwordField.getActionMap().put(ENTER_PRESSED, enterAction);

        JPanel compPanel = new JPanel(new GridBagLayout());
        compPanel.setPreferredSize(new Dimension(400, 150));

        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.weightx = 0.00001;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.insets = new Insets(10, 30, 10, 0);
        compPanel.add(userLbl, m_constraints);

        m_constraints.gridy = 0;
        m_constraints.gridwidth = 2;
        m_constraints.weightx = 1.0;
        m_constraints.fill = GridBagConstraints.HORIZONTAL;
        m_constraints.insets = new Insets(0, 120, 0, 30);
        compPanel.add(m_usernameField, m_constraints);

        m_constraints.gridx = 0;
        m_constraints.gridy = 1;
        m_constraints.insets = new Insets(10, 30, 10, 0);
        compPanel.add(passLbl, m_constraints);

        m_constraints.gridx = GridBagConstraints.REMAINDER;
        m_constraints.gridy = 1;
        m_constraints.gridwidth = 2;
        m_constraints.weightx = 1.0;
        m_constraints.fill = GridBagConstraints.HORIZONTAL;
        m_constraints.insets = new Insets(0, 120, 0, 30);
        compPanel.add(m_passwordField, m_constraints);

        m_loginCard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Login"));
        m_loginCard.add(compPanel, new GridBagConstraints());
    }


    /**
     * Gets/returns the card object of this JPanel.
     * 
     * @return          - The card object for this panel
     */
    public JPanel getCard() {
        return m_loginCard;
    }


    /**
     *
     * @return
     */
    public String getUsername() {
        return m_usernameField.getText();
    }


    /**
     * Gets/returns the user name entered into the user name text field.
     * 
     * @return          - Returns the user name from the user name text field
     */
    public String getPassword() {
        return String.valueOf(m_passwordField.getPassword());
    }


    /**
     * Sets the focus into one of the two text fields when that text field isEmpty.
     * If both text fields are empty, the focus will go to the first empty  field,
     * starting from top to bottom.
     * 
     * @param isEmpty         - boolean array that indicates an empty text field when true
     */
    public void setFocusInField(boolean[] isEmpty) {
        if(isEmpty[0]) {
            m_usernameField.grabFocus();
        }
        else if(isEmpty[1]) {
            m_passwordField.grabFocus();
        }
    }


    /**
     * Clears all text fields
     */
    public void clearTextFields() {
        m_usernameField.setText("");
        m_usernameField.grabFocus();
        Arrays.fill(m_passwordField.getPassword(), '0');
        m_passwordField.setText("");
    }
}
