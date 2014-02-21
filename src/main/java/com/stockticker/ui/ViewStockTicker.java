/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stockticker.ui;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author Hunter97
 */
//public class ViewStockTicker extends WindowAdapter implements ActionListener {
public class ViewStockTicker extends JFrame implements ActionListener {
    private final Toolkit toolKit = Toolkit.getDefaultToolkit();
    //private final Dimension screen = toolKit.getScreenSize();
    private JPanel btnPanel;
    private JPanel toolPanel;
    private JPanel tablePanel;
    private JPanel topPanel;
    private JPanel userRegPanel;
    private JPanel cardPanel;

    private JLabel btnPanelLbl;
    private JLabel toolPanelLbl;
    private JLabel headerLabel;
    private JLabel cardLabel;
    
    private GridBagLayout m_gridBag;
    private GridBagConstraints m_constraints;
    
    public ViewStockTicker() { }
    
    public void actionPerformed( ActionEvent e ) {
        
    }
    
    public void build() {
        setTitle( "Stock Ticker" );
        Image titleIcon = new ImageIcon( toolKit.getImage( "L:\\StockTicker\\images\\stock_ticker.png" )).getImage();
        //Image titleIcon = new ImageIcon( toolKit.getImage( "images\\stock_ticker.png" )).getImage();
        setIconImage(titleIcon);
        setSize(700, 600);
        setLocationRelativeTo( this );
        JFrame.setDefaultLookAndFeelDecorated(true);
        m_gridBag = new GridBagLayout();
        m_constraints = new GridBagConstraints();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent){
                System.out.println("closing");
	        System.exit(0);
            }        
        });    
    }


    public void setPanels() {
        btnPanel = new JPanel(m_gridBag);
        btnPanel.setPreferredSize( new Dimension(100, 80));
        btnPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder() ) );
        btnPanelLbl = new JLabel("Screen Selection");

        toolPanel = new JPanel(m_gridBag);
        toolPanel.setPreferredSize( new Dimension(80, 520));
        toolPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder() ) );
        toolPanelLbl = new JLabel("Tool Panel");
        
        cardPanel = new JPanel(m_gridBag);
        cardPanel.setPreferredSize(new Dimension(610,520));
        cardPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder() ) );

        this.getContentPane().add(btnPanel, BorderLayout.SOUTH);
        btnPanel.add(btnPanelLbl);

        this.getContentPane().add(toolPanel, BorderLayout.WEST);
        toolPanel.add(toolPanelLbl);

        this.getContentPane().add(cardPanel, BorderLayout.EAST);
        setCardLayout();
    }


    private void setCardLayout() {
        userRegPanel = new JPanel();
        userRegPanel.setPreferredSize(new Dimension(420,420));
        userRegPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder() ) );
        headerLabel = new JLabel("Head to Cards",JLabel.CENTER );
        cardLabel = new JLabel("Cards",JLabel.CENTER);  

        CardLayout cards = new CardLayout();
        cards.setHgap(10);
        cards.setVgap(10);
        userRegPanel.setLayout(cards);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(new JButton("OK"));
        buttonPanel.add(new JButton("Cancel"));    
        JPanel textBoxPanel = new JPanel(new FlowLayout());

        textBoxPanel.add(new JLabel("Name:"));
        textBoxPanel.add(new JTextField(20));

        userRegPanel.add("Button", buttonPanel);
        userRegPanel.add("Text", textBoxPanel);
      
        final DefaultComboBoxModel panelName = new DefaultComboBoxModel();

        panelName.addElement("Ticker");
        panelName.addElement("Reg");
     	  
        final JComboBox listCombo = new JComboBox(panelName);    
        listCombo.setSelectedIndex(0);
        JScrollPane listComboScrollPane = new JScrollPane(listCombo);    

        listComboScrollPane.setSize(10,10);
        m_gridBag.setConstraints(listComboScrollPane, this.setupConstraints( 0, 2, 0, 0, .5, .5, GridBagConstraints.PAGE_START, GridBagConstraints.NONE ,
                new Insets( 10, 10, 10, 10 ), m_constraints));
        cardPanel.add( listComboScrollPane);
            
        m_gridBag.setConstraints(userRegPanel, this.setupConstraints( 0, 0, 30, GridBagConstraints.REMAINDER, .5, .5, GridBagConstraints.SOUTH, GridBagConstraints.NONE ,
                new Insets( 10, 1, 1, 10 ), m_constraints));
        cardPanel.add(userRegPanel);
    }

    /**
     * Sets/returns the GridBagConstraints of a component within a JPanel.
     * 
     * @param xPos          - Components column location in pane
     * @param yPos          - Components row location in pane
     * @param colTotal      - Number of columns in pane
     * @param rowTotal      - Number of rows in pane
     * @param weight_X      - Re-distribute component in X direction
     * @param weight_Y      - Re-distribute component in Y direction
     * @param panel_loc     - Anchor component at location in grid
     * @param fill_cells    - Causes component to fill display area in horizontal or vertical direction
     * @param pad_cells     - Pad remainder of grid location with white space
     * @param gbc_prop      - GridBagConstraints object
     * @return              - GridBagConstraints object with constraints set
     */
    private GridBagConstraints setupConstraints( int xPos, int yPos, int colTotal, int rowTotal, double weight_X, double weight_Y, int panel_loc,
            int fill_cells, Insets pad_cells, GridBagConstraints gbc_prop) {

        gbc_prop.gridx = xPos;
        gbc_prop.gridy = yPos;
        gbc_prop.gridwidth = colTotal;
        gbc_prop.gridheight = rowTotal;
        gbc_prop.weightx = weight_X;
        gbc_prop.weighty = weight_Y;
        gbc_prop.anchor = panel_loc;
        gbc_prop.fill = fill_cells;
        gbc_prop.insets = pad_cells;

        return gbc_prop;
    }
}
