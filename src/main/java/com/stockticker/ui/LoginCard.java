/**
 * GUI for Stock Ticker Portfolio Manager
 * J308 Project
 * Paul Wallace
 */

package com.stockticker.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

/**
 * J308
 * 
 * @author prwallace
 */
public class LoginCard extends JPanel {
    private static final long serialVersionUID = 1L;
    private JPanel m_loginCard;
    private final GridBagConstraints m_constraints;

    private JTextField m_usernameField;
    private JTextField m_passwordField;

    /**
     *
     */
    public LoginCard() {
        m_constraints = new GridBagConstraints();
        setCard();
    }

   /**
    *
    */
    public final void setCard() {
        m_loginCard = new JPanel(new GridBagLayout());
        m_loginCard.setPreferredSize(new Dimension(550, 520));

        JLabel userLbl = new JLabel("User Name:");
        JLabel passLbl = new JLabel("Password:");

        m_usernameField = new JTextField(40);
        m_passwordField = new JTextField(40);
        m_usernameField.setText("");
        m_passwordField.setText("");

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

        m_loginCard.setBorder(BorderFactory.createTitledBorder(BorderFactory
                .createEtchedBorder(EtchedBorder.LOWERED), "Login"));
        m_loginCard.add(compPanel, new GridBagConstraints());
    }


    /**
     *
     * @return
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
     *
     * @return
     */
    public String getPassword() {
        return m_passwordField.getText();
    }


    /**
     *
     */
    public void clearTextFields() {
        m_usernameField.setText("");
        m_passwordField.setText("");
    }
}
