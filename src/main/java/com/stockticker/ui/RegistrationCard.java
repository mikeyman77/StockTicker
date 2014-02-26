package com.stockticker.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RegistrationCard {
    GridBagConstraints m_constraints;
    JPanel m_regCard;

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

        JTextField userName = new JTextField(40);

        JTextField password = new JTextField(40);
        JTextField fistName = new JTextField(40);
        JTextField lastName = new JTextField(40);

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
        compPanel.add(userName, m_constraints);

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
        compPanel.add(password, m_constraints);

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
        compPanel.add(fistName, m_constraints);

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
        compPanel.add(lastName, m_constraints);

        m_regCard.add(compPanel, new GridBagConstraints());
    }

    public JPanel getCard() {
        return m_regCard;
    }
}
