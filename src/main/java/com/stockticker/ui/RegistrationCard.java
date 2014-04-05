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
    private JPanel m_profilePanel;
    private JPanel m_compPanel;

    private JTextField m_usernameField;
    private JTextField m_firstnameField;
    private JTextField m_lastnameField;
    private JPasswordField m_passwordField;
    private JPasswordField m_verifyField;

    private JTextField m_updateUserFld;
    private JTextField m_updateFirstFld;
    private JTextField m_updateLastFld;

    private final OperateStockTicker m_operate;

    
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
     * Adds the components and the components panel for Registration and Update
     * Profile forms.  Lays out the components onto their component panels and
     * adds to the panels.  The component panels are then added to the Card panel.
     */
    public final void setCard() {

        // Create and layout the panels and components for the Registration form
        m_regCard = new JPanel(new GridBagLayout());
        m_regCard.setPreferredSize(new Dimension(550, 520));

        JLabel userLbl = new JLabel("User Name:");
        JLabel passLbl = new JLabel("Password:");
        JLabel verPassLbl = new JLabel("Re-enter Password:");
        JLabel fistNameLbl = new JLabel("First name:");
        JLabel lastNameLbl = new JLabel("Last name:");

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

        Action enterLastname = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               m_operate.getLeftControlBtn().doClick();
            }
        };

        m_lastnameField.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), ENTER_PRESSED);
        m_lastnameField.getActionMap().put(ENTER_PRESSED, enterLastname);
        

        m_compPanel = new JPanel(new GridBagLayout());
        m_compPanel.setPreferredSize(new Dimension(400, 250));

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.insets = new Insets(10, 45, 10, 0);
        m_compPanel.add(userLbl, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.weightx = 1.0;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.HORIZONTAL;
        m_constraints.insets = new Insets(0, 120, 0, 30);
        m_compPanel.add(m_usernameField, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 1;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.insets = new Insets(10, 50, 10, 50);
        m_compPanel.add(passLbl, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = GridBagConstraints.REMAINDER;
        m_constraints.gridy = 1;
        m_constraints.gridwidth = 2;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.HORIZONTAL;
        m_constraints.insets = new Insets(0, 120, 0, 30);
        m_compPanel.add(m_passwordField, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 2;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.insets = new Insets(10, 0, 10, 50);
        m_compPanel.add(verPassLbl, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = GridBagConstraints.REMAINDER;
        m_constraints.gridy = 2;
        m_constraints.gridwidth = 2;
        m_constraints.weightx = 1.0;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.HORIZONTAL;
        m_constraints.insets = new Insets(0, 120, 0, 30);
        m_compPanel.add(m_verifyField, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 3;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.insets = new Insets(10, 50, 10, 0);
        m_compPanel.add(fistNameLbl, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = GridBagConstraints.REMAINDER;
        m_constraints.gridy = 3;
        m_constraints.gridwidth = 2;
        m_constraints.weightx = 1.0;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.HORIZONTAL;
        m_constraints.insets = new Insets(0, 120, 0, 30);
        m_compPanel.add(m_firstnameField, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 4;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.insets = new Insets(10, 50, 10, 0);
        m_compPanel.add(lastNameLbl, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = GridBagConstraints.REMAINDER;
        m_constraints.gridy = 4;
        m_constraints.gridwidth = 2;
        m_constraints.weightx = 1.0;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.HORIZONTAL;
        m_constraints.insets = new Insets(0, 120, 0, 30);
        m_compPanel.add(m_lastnameField, m_constraints);


        // Create and layout the panels and components for the Update Profile form
        JLabel updateUserLbl = new JLabel("User name:");
        JLabel updateFirstLbl = new JLabel("First name:");
        JLabel updateLastLbl = new JLabel("Last name:");
        JLabel passwordLbl = new JLabel("Change Password:");
        JLabel deleteLbl = new JLabel("Delete User:");

        m_updateUserFld = new JTextField(40);
        m_updateUserFld.setEditable(false);

        m_updateFirstFld = new JTextField(40);
        m_updateLastFld = new JTextField(40);

        JButton passwordBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");

        m_profilePanel = new JPanel(new GridBagLayout());
        m_profilePanel.setPreferredSize(new Dimension(400, 250));
        m_profilePanel.setVisible(false);

        passwordBtn.addActionListener(m_operate);
        deleteBtn.addActionListener(m_operate);

        Action enterLastFld = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
               m_operate.getLeftControlBtn().doClick();
            }
        };

        m_updateLastFld.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), ENTER_PRESSED);
        m_updateLastFld.getActionMap().put(ENTER_PRESSED, enterLastFld);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.insets = new Insets(10, 48, 10, 0);
        m_profilePanel.add(updateUserLbl, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = GridBagConstraints.REMAINDER;
        m_constraints.gridy = 0;
        m_constraints.weightx = 1.0;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.HORIZONTAL;
        m_constraints.insets = new Insets(0, 120, 0, 30);
        m_profilePanel.add(m_updateUserFld, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 1;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.insets = new Insets(10, 50, 10, 50);
        m_profilePanel.add(updateFirstLbl, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = GridBagConstraints.REMAINDER;
        m_constraints.gridy = 1;
        m_constraints.gridwidth = 2;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.HORIZONTAL;
        m_constraints.insets = new Insets(0, 120, 0, 30);
        m_profilePanel.add(m_updateFirstFld, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 2;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.insets = new Insets(10, 50, 10, 50);
        m_profilePanel.add(updateLastLbl, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = GridBagConstraints.REMAINDER;
        m_constraints.gridy = 2;
        m_constraints.gridwidth = 2;
        m_constraints.weightx = 1.0;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.HORIZONTAL;
        m_constraints.insets = new Insets(0, 120, 0, 30);
        m_profilePanel.add(m_updateLastFld, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 3;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.insets = new Insets(25, 5, 0, 0);
        m_profilePanel.add(passwordLbl, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = GridBagConstraints.REMAINDER;
        m_constraints.gridy = 3;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.insets = new Insets(30, 10, 5, 95);
        m_profilePanel.add(passwordBtn, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = 0;
        m_constraints.gridy = 4;
        m_constraints.anchor = GridBagConstraints.WEST;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.insets = new Insets(5, 44, 18, 50);
        m_profilePanel.add(deleteLbl, m_constraints);

        m_constraints = new GridBagConstraints();
        m_constraints.gridx = GridBagConstraints.REMAINDER;
        m_constraints.gridy = 4;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.insets = new Insets(5, 10, 15, 95);
        m_profilePanel.add(deleteBtn, m_constraints);


        // Add the registration and update profile forms to Card panel
        m_regCard.add(m_compPanel, new GridBagConstraints());
        m_regCard.add(m_profilePanel, new GridBagConstraints());
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
     * Gets/returns the text in first name text field of the specified form.  The
     * boolean argument when true, retrieves text from registration form, when 
     * false, retrieves from the update profile form.
     * 
     * @param regForm   - boolean indicates which field to retrieve text
     * @return  - the text in the last name text field
     */
    public String getfirstName(boolean regForm) {
        String temp;

        if(regForm) {
            temp = m_firstnameField.getText();
        }
        else {
            temp = m_updateFirstFld.getText();
        }

        return temp;
    }

    
    /**
     * Gets/returns the text in last name text field of the specified form.  The
     * boolean argument when true, retrieves text from registration form, when 
     * false, retrieves from the update profile form.
     * 
     * @param regForm   - boolean indicates which field to retrieve text
     * @return  - the text in the last name text field
     */
    public String getLastName(boolean regForm) {
        String temp;

        if(regForm) {
            temp = m_lastnameField.getText();
        }
        else {
            temp = m_updateLastFld.getText();
        }

        return temp;
    }

    
    /**
     * Gets/returns the text in the username text field
     * 
     * @return  - the text in the username field
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
     * This method handles the switching between the registration form and the
     * update profile form. Sets the visible property for the registration form
     * and the update profile form, depending on which form is being used.  Sets
     * the fields of the update profile form with the current user information 
     * and sets the boarder for each form.
     * 
     * @param username  - sets the text field with current user information
     * @param firstname - sets the test field with current user information
     * @param lastname  - sets the test field with current user information
     * @param visible   - sets the visibility of the register and update profile panels
     */
    public void enableProfileForm(String username, String firstname, String lastname, boolean visible) {
        m_updateUserFld.setText(username);
        m_updateFirstFld.setText(firstname);
        m_updateLastFld.setText(lastname);

        m_updateFirstFld.grabFocus();
        m_compPanel.setVisible(visible);

        if(visible) {
            m_profilePanel.setVisible(false);
            
            m_regCard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Registration"));
        }
        else {
            m_profilePanel.setVisible(true);
            m_regCard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Profile"));
            
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
