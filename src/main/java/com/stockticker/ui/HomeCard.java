package com.stockticker.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class HomeCard extends JPanel {
    private static final long serialVersionUID = 1L;
    private JPanel m_homeCard;
    private BufferedImage m_splashImage;
    private GridBagConstraints m_constraints;
    
    public HomeCard() throws IOException {
        m_constraints = new GridBagConstraints();
        setCard();
    }

    public void setCard() throws IOException {
        m_homeCard = new JPanel(new GridBagLayout());
        m_homeCard.setPreferredSize(new Dimension(550, 520));
        m_homeCard.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Home"));

        m_splashImage = ImageIO.read(new File("images\\naskaqImage.jpg"));
        JLabel imageLbl = new JLabel(new ImageIcon(m_splashImage));
        JLabel name = new JLabel("Stock Ticker Portfolio Manager");
        name.setFont(new Font("Serif", Font.PLAIN, 40));
        
        m_constraints.gridx = 0;
        m_constraints.gridy = 0;
        m_constraints.fill = GridBagConstraints.NONE;
        m_constraints.insets = new Insets(0, 0, 50, 0);
        m_homeCard.add(imageLbl, m_constraints);

        m_constraints.gridx = 0;
        m_constraints.gridy = 1;
        m_constraints.insets = new Insets(0, 0, 100, 0);
        m_homeCard.add(name, m_constraints);
    }


    public JPanel getCard() {
        return m_homeCard;
    }
}
