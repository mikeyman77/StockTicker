package com.stockticker.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class RegistrationCard extends JPanel {
    private static final long serialVersionUID = 1L;

    private GridBagConstraints m_constraints;
    private JPanel m_regCard;

    private JTextField m_userField;
    private JTextField m_passwordField;
    private JTextField m_fistNameField;
    private JTextField m_lastNameField;

    private String m_userName;
    private String m_password;
    private String m_firstName;
    private String m_lastName;


    public RegistrationCard() {
        m_constraints = new GridBagConstraints();
        setCard();
    }


    public final void setCard() {
        m_regCard = new JPanel(new GridBagLayout());
        m_regCard.setPreferredSize(new Dimension(550, 520));

        JLabel userLbl = new JLabel("User Name:");
        JLabel passLbl = new JLabel("Password:");
        JLabel fistNameLbl = new JLabel("First name:");
        JLabel lastNameLbl = new JLabel("Last name:");


        m_userField = new JTextField(40);
        m_userField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                m_userName = m_userField.getText();
                System.out.println(m_userName);
            }
        });

        m_passwordField = new JTextField(40);
        m_passwordField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                m_password = m_passwordField.getText();
                System.out.println(m_password);
            }
        });

        m_fistNameField = new JTextField(40);
        m_fistNameField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                m_firstName = m_fistNameField.getText();
                System.out.println(m_firstName);
            }
        });

        m_lastNameField = new JTextField(40);
        m_lastNameField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                m_lastName = m_lastNameField.getText();
                System.out.println(m_lastName);
            }
        });

        /*final JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.out.println(submit.getText());
            }
        });*/


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
        compPanel.add(m_userField, m_constraints);

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
        compPanel.add(m_fistNameField, m_constraints);

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
        compPanel.add(m_lastNameField, m_constraints);

        /*m_constraints.gridx = 1;
        m_constraints.gridy = 4;
        //m_constraints.weightx = 1.0;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.insets = new Insets(50, 0, 0, 120);
        compPanel.add(submit, m_constraints);*/

        
        m_regCard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Registration"));
        m_regCard.add(compPanel, new GridBagConstraints());

    }


    public JPanel getCard() {
        return m_regCard;
    }
}
