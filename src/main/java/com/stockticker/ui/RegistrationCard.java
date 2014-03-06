/**
 * GUI for Stock Ticker Portfolio Manager
 * J308 Project
 * Paul Wallace
 */

package com.stockticker.ui;

import com.stockticker.UserInfo;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

/**
* J308
* @author prwallace
*/
public class RegistrationCard extends JPanel {
    private static final long serialVersionUID = 1L;

    private GridBagConstraints m_constraints;
    private JPanel m_regCard;

    private JTextField m_usernameField;
    private JTextField m_passwordField;
    private JTextField m_firstmameField;
    private JTextField m_lastnameField;
    
    private List<String> m_regInfo = new ArrayList<String>();

    /**
    *
    *
    */
    public RegistrationCard() {
        m_constraints = new GridBagConstraints();
        setCard();
    }


    /**
    *
    *
    */
    public final void setCard() {
        m_regCard = new JPanel(new GridBagLayout());
        m_regCard.setPreferredSize(new Dimension(550, 520));

        JLabel userLbl = new JLabel("User Name:");
        JLabel passLbl = new JLabel("Password:");
        JLabel fistNameLbl = new JLabel("First name:");
        JLabel lastNameLbl = new JLabel("Last name:");

        m_usernameField = new JTextField(40);
        m_passwordField = new JTextField(40);
        m_passwordField.setText("");
        m_firstmameField = new JTextField(40);
        m_lastnameField = new JTextField(40);

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
        compPanel.add(m_firstmameField, m_constraints);

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

        // Add this main panel to the main Card panel
        m_regCard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Registration"));
        m_regCard.add(compPanel, new GridBagConstraints());

    }


   /**
    *
    *
    */
    public JPanel getCard() {
        return m_regCard;
    }


   /**
    *
    *
    */
    public String getfirstName() {
        return m_firstmameField.getText();
    }


   /**
    *
    *
    */
    public String getLastName() {
        return m_lastnameField.getText();
    }


    /**
    *
    *
    */
    public String getUsername() {
        return m_usernameField.getText();
    }


    /**
    *
    *
    */
    public String getPassword() {
        return m_passwordField.getText();
    }


    public void clearTextFields() {
        m_usernameField.setText("");
        m_passwordField.setText("");
        m_firstmameField.setText("");
        m_lastnameField.setText("");
    }
}
