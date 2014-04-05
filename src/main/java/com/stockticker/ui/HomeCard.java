/**
 * GUI for Stock Ticker Portfolio Manager
 * 90.308-061 Agile Software Dev. w/Java Project
 * Paul Wallace
 */

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

/**
 * Home screen for Stock Ticker Portfolio Manager
 * Provides the user an entry point into the Stock Ticker Portfolio Manager
 * @author prwallace
 */
public final class HomeCard extends JPanel {
    private static final long serialVersionUID = 1L;
    private JPanel m_homeCard;
    private BufferedImage m_splashImage;
    private GridBagConstraints m_constraints;


    /**
     * Constructor for HomeCard object
     * @throws IOException  - for IconImages used in  Home screen 
     */
    public HomeCard() throws IOException {
        setCard();
    }


    /**
     * Creates the Card JPanel for this UI screen and adds its components to this
     * panel.
     */
    public void setCard()  {
            m_homeCard = new JPanel(new GridBagLayout());
            m_homeCard.setPreferredSize(new Dimension(550, 520));
            m_homeCard.setBorder(BorderFactory.createTitledBorder(BorderFactory
                    .createEtchedBorder(EtchedBorder.LOWERED), "Home"));
            try {
                m_splashImage = ImageIO.read(new File("./images/nasdakTicker.jpg"));
                JLabel imageLbl = new JLabel(new ImageIcon(m_splashImage));

                m_constraints = new GridBagConstraints();
                m_constraints.gridx = 0;
                m_constraints.gridy = 0;
                m_constraints.fill = GridBagConstraints.NONE;
                m_constraints.insets = new Insets(0, 0, 50, 0);
                m_homeCard.add(imageLbl, m_constraints);
            } 
            catch (IOException ex) {
                System.err.println("Exceptioin attempting to launch logo " + ex.getMessage());
            }

            JLabel name = new JLabel("Stock Ticker Portfolio Manager");
            name.setFont(new Font("Serif", Font.PLAIN, 40));

            m_constraints = new GridBagConstraints();
            m_constraints.gridx = 0;
            m_constraints.gridy = 1;
            m_constraints.insets = new Insets(0, 0, 100, 0);
            m_homeCard.add(name, m_constraints);
    }


    /**
     * Gets/returns this screens Card object
     * @return an instance of the HomeCard JPanel object
     */
    public JPanel getCard() {
        return m_homeCard;
    }
}
