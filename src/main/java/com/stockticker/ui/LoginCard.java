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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private GridBagConstraints m_constraints;

    private String m_userName;
    private String m_password;

    /**
    *
    *
    */
    public LoginCard() {
        m_constraints = new GridBagConstraints();
        setCard();
    }

    /**
    *
    *
    */
    public final void setCard() {
        m_loginCard = new JPanel(new GridBagLayout());
        m_loginCard.setPreferredSize(new Dimension(550, 520));

        JLabel userLbl = new JLabel("User Name:");
        JLabel passLbl = new JLabel("Password:");

        final JTextField userField = new JTextField(40);
        userField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                m_userName = userField.getText();
                System.out.println(m_userName);
            }
        });

        final JTextField passwordField = new JTextField(40);
        passwordField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                m_password = passwordField.getText();
                System.out.println(m_password);
            }
        });

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
        compPanel.add(userField, m_constraints);

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
        compPanel.add(passwordField, m_constraints);

        m_loginCard.setBorder(BorderFactory.createTitledBorder(BorderFactory
                .createEtchedBorder(EtchedBorder.LOWERED), "Login"));
        m_loginCard.add(compPanel, new GridBagConstraints());
    }

    /**
    *
    *
    */
    public JPanel getCard() {
        return m_loginCard;
    }

    /**
    *
    *
    */
    public String getUsername() {
        return m_userName;
    }

    /**
    *
    *
    */
    public String getPassword() {
        return m_password;
    }
}
