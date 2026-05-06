package com.pvz.view;

import javax.swing.*;

import com.pvz.model.Plant.PlantType;
import com.pvz.util.ResourceManager;

import java.awt.*;

public class PlantCard extends JButton {
	protected static final int CARD_WIDTH = 120;
	protected static final int CARD_HEIGHT = 140;
    private int cost;
    private Image plantImage;
    protected boolean canAfford = true;
    private boolean isSelected = false;
    protected PlantType type;
    
    public PlantCard(PlantType type) {
    	this.type = type;
        this.cost = type.getCost();
        this.plantImage = type.getSprite();

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        this.setContentAreaFilled(false);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JLabel costLabel = new JLabel(String.valueOf(cost), SwingConstants.CENTER);
        costLabel.setForeground(Color.BLACK);
        costLabel.setFont(new Font("Arial", Font.BOLD, 16));
        costLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        // I added bottom padding for esthetic reasons
        this.add(costLabel, BorderLayout.SOUTH);
    }
    public void changeSelectedStatus(boolean status) {
    	isSelected = status;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(ResourceManager.genericCardBack, 0, 0, getWidth(), getHeight(), null);

        if (plantImage != null) {
            g.drawImage(plantImage, 22, 18, getWidth() - 45, getHeight() - 55, null);
        }
        else {
        	System.out.println("plant not loaded");
        }

        if (!canAfford) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(0, 0, 0, 150));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
        else if (isSelected){
        	Graphics2D g2d = (Graphics2D) g;
        	g2d.setColor(new Color(0, 50, 0, 150));
        	g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
