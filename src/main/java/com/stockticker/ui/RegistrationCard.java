/**
 * GUI for Stock Ticker Portfolio Manager
 * J308 Project
 * Paul Wallace
 */

package com.stockticker.ui;

import com.stockticker.ui.ViewStockTicker.OperateStockTicker;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
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
 * RegistratioinCard class
 * Provides fields to allow a user to register with the Stock Ticker Portfolio
 * Manager api
 * @author prwallace
 */
public class RegistrationCard extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final String ENTER_PRESSED = "ENTER_RELEASED";

    private final GridBagConstraints m_constraints;
    private JPanel m_regCard;

    private JTextField m_usernameField;
    private JPasswordField m_passwordField;
    private JTextField m_firstnameField;
    private JTextField m_lastnameField;
    private final OperateStockTicker m_operate;


    
    /**
     * Constructs the RegistrationCard class
     * @param operate   - Instance variable of the OperateStockTicker class
     */
    public RegistrationCard(OperateStockTicker operate) {
        m_constraints = new GridBagConstraints();
        m_operate = operate;
        setCard();
    }


    /**
     * Adds the components and the components panel for this screen.  Lays out
     * the components onto the component panel and add the component panel to the 
     * Card panel.
     */
    public final void setCard() {
        m_regCard = new JPanel(new GridBagLayout());
        m_regCard.setPreferredSize(new Dimension(550, 520));

        JLabel userLbl = new JLabel("User Name:");
        JLabel passLbl = new JLabel("Password:");
        JLabel fistNameLbl = new JLabel("First name:");
        JLabel lastNameLbl = new JLabel("Last name:");

        m_usernameField = new JTextField(40);
        m_usernameField.setText("");

        m_passwordField = new JPasswordField(20);
        m_passwordField.setEchoChar('*');
        m_passwordField.setText("");

        m_firstnameField = new JTextField(40);
        m_firstnameField.setText("");

        m_lastnameField = new JTextField(40);
        m_lastnameField.setText("");

        Action enterAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               m_operate.getLeftControlBtn().doClick();
            }
        };

        m_lastnameField.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), ENTER_PRESSED);
        m_lastnameField.getActionMap().put(ENTER_PRESSED, enterAction);
        

        JPanel compPanel = new JPanel(new GridBagLayout());
        compPanel.setPreferredSize(new Dimension(400, 250));

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

        m_constraints.gridx = 0;
        m_constraints.gridy = 2;
        m_constraints.insets = new Insets(10, 30, 10, 0);
        compPanel.add(fistNameLbl, m_constraints);

        m_constraints.gridx = GridBagConstraints.REMAINDER;
        m_constraints.gridy = 2;
        m_constraints.gridwidth = 2;
        m_constraints.weightx = 1.0;
        m_constraints.fill = GridBagConstraints.HORIZONTAL;
        m_constraints.insets = new Insets(0, 120, 0, 30);
        compPanel.add(m_firstnameField, m_constraints);

        m_constraints.gridx = 0;
        m_constraints.gridy = 3;
        m_constraints.insets = new Insets(10, 30, 10, 0);
        compPanel.add(lastNameLbl, m_constraints);

        m_constraints.gridx = GridBagConstraints.REMAINDER;
        m_constraints.gridy = 3;
        m_constraints.gridwidth = 2;
        m_constraints.weightx = 1.0;
        m_constraints.fill = GridBagConstraints.HORIZONTAL;
        m_constraints.insets = new Insets(0, 120, 0, 30);
        compPanel.add(m_lastnameField, m_constraints);

        m_regCard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Registration"));
        m_regCard.add(compPanel, new GridBagConstraints());
    }


    /**
     * Gets/returns the card object of this JPanel.  
     *
     * @return          - The card object for this panel
     */
    public JPanel getCard() {
        return m_regCard;
    }


    /**
     * Gets/returns the first name entered into the first name text field.
     *
     * @return          - Returns the first name from the firstname text field
     */
    public String getfirstName() {
        return m_firstnameField.getText();
    }


    /**
     * Gets/returns the last name entered into the last name text field.
     * 
     * @return          - Returns the last name from the lastname text field
     */
    public String getLastName() {
        return m_lastnameField.getText();
    }


    /**
     * Gets/Returns the username entered into the username text field.
     * 
     * @return          - Returns the username from username text field
     */
    public String getUsername() {
        return m_usernameField.getText();
    }


    /**
     * Gets/returns the password entered into the password text field.
     * 
     * @return          - Returns the entered password from password field
     */
    public String getPassword() {
        return String.valueOf(m_passwordField.getPassword());
    }


    /**
     * Grabs focus for the Username or Password text fields, based on the String
     * argument.
     * 
     * @param field         - Indicates which field should have the focus
     */
    public void setFocusInField(String field) {
        switch (field) {
            case "Username":
                m_usernameField.grabFocus();
                break;
            case "Password":
                m_passwordField.grabFocus();
            case "Firstname":
                m_firstnameField.grabFocus();
            case "Lastname":
                m_lastnameField.grabFocus();
                break;
            default:
                System.out.println("Invalid String field used to switch focus");
        }
    }


    /**
     * Clears all text fields
     */
    public void clearTextFields() {
        m_usernameField.setText("");
        m_usernameField.grabFocus();
        Arrays.fill(m_passwordField.getPassword(), '0');
        m_firstnameField.setText("");
        m_lastnameField.setText("");
        m_passwordField.setText("");
    }
}
