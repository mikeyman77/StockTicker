/**
 * GUI for Stock Ticker Portfolio Manager
 * 90.308-061 Agile Software Dev. w/Java Project
 * Paul Wallace
 */
package com.stockticker.ui;

import com.stockticker.ui.IComponentsUI.Field;
import com.stockticker.ui.ViewStockTicker.OperateStockTicker;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EtchedBorder;
import javax.swing.AbstractAction;
import javax.swing.Action;

import java.util.Arrays;


/**
 * Registration screen for Stock Ticker Portfolio Manager
 * Provides fields to allow a user to register with the Stock Ticker Portfolio
 * Manager
 * 
 * @author prwallace
 */
public class RegistrationCard extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final String ENTER_PRESSED = "ENTER_RELEASED";

    private GridBagConstraints m_constraints;
    private JPanel m_regCard;

    private JTextField m_usernameField;
    private JPasswordField m_passwordField;
    private JPasswordField m_verifyField;
    private JTextField m_firstnameField;
    private JTextField m_lastnameField;
    private final OperateStockTicker m_operate;

    private JLabel m_userLbl;
    private JLabel m_passLbl;
    private JLabel m_verPassLbl;
    private JLabel m_fistNameLbl;
    private JLabel m_lastNameLbl;
    private JLabel m_updateLbl;

    private JButton m_passwordBtn;

    
    /**
     * Constructs the RegistrationCard class
     * 
     * @param operate   - Instance of OperateStockTicker
     */
    public RegistrationCard(OperateStockTicker operate) {
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

        m_userLbl = new JLabel("User Name:");
        m_passLbl = new JLabel("Password:");
        m_verPassLbl = new JLabel("Re-enter Password:");
        m_fistNameLbl = new JLabel("First name:");
        m_lastNameLbl = new JLabel("Last name:");

        m_usernameField = new JTextField(40);
        m_usernameField.setText("");

        m_passwordField = new JPasswordField(20);
        m_passwordField.setEchoChar('*');
        m_passwordField.setText("");

        m_verifyField = new JPasswordField(20);
        m_verifyField.setEchoChar('*');
        m_verifyField.setText("");

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

        m_lastnameField.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), ENTER_PRESSED);
        m_lastnameField.getActionMap().put(ENTER_PRESSED, enterAction);
        

        JPanel compPanel = new JPanel(new GridBagLayout());
        compPanel.setPreferredSize(new Dimension(400, 250));

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.insets = new Insets(10, 45, 10, 0);
        compPanel.add(m_userLbl, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.weightx = 1.0;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.HORIZONTAL;
        m_constraints.insets = new Insets(0, 120, 0, 30);
        compPanel.add(m_usernameField, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 1;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.insets = new Insets(10, 50, 10, 50);
        compPanel.add(m_passLbl, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = GridBagConstraints.REMAINDER;
        m_constraints.gridy = 1;
        m_constraints.gridwidth = 2;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.HORIZONTAL;
        m_constraints.insets = new Insets(0, 120, 0, 30);
        compPanel.add(m_passwordField, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 2;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.insets = new Insets(10, 0, 10, 50);
        compPanel.add(m_verPassLbl, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = GridBagConstraints.REMAINDER;
        m_constraints.gridy = 2;
        m_constraints.gridwidth = 2;
        m_constraints.weightx = 1.0;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.HORIZONTAL;
        m_constraints.insets = new Insets(0, 120, 0, 30);
        compPanel.add(m_verifyField, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 3;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.insets = new Insets(10, 50, 10, 0);
        compPanel.add(m_fistNameLbl, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = GridBagConstraints.REMAINDER;
        m_constraints.gridy = 3;
        m_constraints.gridwidth = 2;
        m_constraints.weightx = 1.0;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.HORIZONTAL;
        m_constraints.insets = new Insets(0, 120, 0, 30);
        compPanel.add(m_firstnameField, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 4;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.insets = new Insets(10, 50, 10, 0);
        compPanel.add(m_lastNameLbl, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = GridBagConstraints.REMAINDER;
        m_constraints.gridy = 4;
        m_constraints.gridwidth = 2;
        m_constraints.weightx = 1.0;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.HORIZONTAL;
        m_constraints.insets = new Insets(0, 120, 0, 30);
        compPanel.add(m_lastnameField, m_constraints);


        // Create and layout the panels and components for the Update Profile screen
        m_updateLbl = new JLabel("Change Password:");
        m_updateLbl.setVisible(false);

        m_passwordBtn = new JButton("Update");
        m_passwordBtn.setVisible(false);

        m_passwordBtn.addActionListener(m_operate);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 1;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.insets = new Insets(10, 5, 10, 0);//50
        compPanel.add(m_updateLbl, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = GridBagConstraints.REMAINDER;
        m_constraints.gridy = 1;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.insets = new Insets(15, 0, 15, 81);//120
        compPanel.add(m_passwordBtn, m_constraints);

        m_regCard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Registration"));
        m_regCard.add(compPanel, new GridBagConstraints());
    }


    /**
     * Gets/returns the Card panel
     * 
     * @return  - the Card panel of this screen
     */
    public JPanel getCard() {
        return m_regCard;
    }


    /**
     * Gets/returns the test in first name text field.
     *
     * @return  - the text in the first name text field
     */
    public String getfirstName() {
        return m_firstnameField.getText();
    }


    /**
     * Gets/returns the text in the last name text field.
     * 
     * @return  - the text in the last name text field
     */
    public String getLastName() {
        return m_lastnameField.getText();
    }


    /**
     * Gets/Returns the text in the username text field.
     * 
     * @return  - the text in the username text field
     */
    public String getUsername() {
        return m_usernameField.getText();
    }


    /**
     * Gets/returns the text in the password text field.
     * 
     * @return  - the text in the password field
     */
    public String getPassword() {
        return String.valueOf(m_passwordField.getPassword());
    }


    /**
     * Gets/returns the text the verify password text field.
     * 
     * @return  - the text in the verify password field
     */
    public String getVerifiedPasswd() {
        return String.valueOf(m_verifyField.getPassword());
    }


    /**
     * Sets the focus into one of the four text fields when that text field isEmpty.
     * If multiple fields are empty, the focus will go to the first empty field,
     * starting from top to bottom.
     * 
     * @param isEmpty   - boolean array that indicates an empty text field when true
     */
    public void setFocusInField(boolean[] isEmpty) {
        if(isEmpty[Field.USER.getValue()]) {
            m_usernameField.grabFocus();
        }
        else if(isEmpty[Field.PASSWD.getValue()]) {
            m_passwordField.grabFocus();
        }
        else if(isEmpty[Field.VER_PASS.getValue()]) {
            m_verifyField.grabFocus();
        }
        else if(isEmpty[Field.FIRST_NM.getValue()]) {
            m_firstnameField.grabFocus();
        }
        else if(isEmpty[Field.LAST_NM.getValue()]) {
             m_lastnameField.grabFocus();
        }
    }


    /**
     * Sets the visible property for the text fields in this form.  Used to clear
     * a text field and set its focus when switching between forms or invalid
     * information was entered into the field.
     * 
     * @param username  - sets the visible property for the username field
     * @param firstname
     * @param lastname
     * @param visible 
     */
    public void enableProfileForm(String username, String firstname, String lastname, boolean visible) {
        m_usernameField.setText(username);
        m_firstnameField.setText(firstname);
        m_firstnameField.grabFocus();
        m_lastnameField.setText(lastname);

        m_usernameField.setEditable(visible);
        m_passwordField.setVisible(visible);
        m_verifyField.setVisible(visible);
        m_passLbl.setVisible(visible);
        m_verPassLbl.setVisible(visible);

        if(visible) {
            m_updateLbl.setVisible(false);
            m_passwordBtn.setVisible(false);
        }
        else {
            m_updateLbl.setVisible(true);
            m_passwordBtn.setVisible(true);
        }
    }


    /**
     * Clears both the password field and the verify password field.
     */
    public void clearPasswordFields() {
        Arrays.fill(m_passwordField.getPassword(), '0');
        m_passwordField.setText("");
        Arrays.fill(m_verifyField.getPassword(), '0');
        m_verifyField.setText("");
    }


    /**
     * Clears all text fields and grabs focus in the username field.
     */
    public void clearTextFields() {
        m_usernameField.setEditable(true);
        m_usernameField.setText("");
        m_usernameField.grabFocus();
        Arrays.fill(m_passwordField.getPassword(), '0');
        m_passwordField.setText("");
        Arrays.fill(m_verifyField.getPassword(), '0');
        m_verifyField.setText("");
        m_firstnameField.setText("");
        m_lastnameField.setText("");
    }
}
