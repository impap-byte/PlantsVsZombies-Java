package com.pvz.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.pvz.controller.GameStateManager;
import com.pvz.controller.GameStateManager.GameState;
import com.pvz.model.Plant;
import com.pvz.model.Plant.PlantType;
import com.pvz.util.ResourceManager;

public class CardHolderPanel extends JPanel {
	protected List<PlantCard> cards = new ArrayList<>();
	protected PlantType selectedCardType;
	protected PlantCard selectedCard;
	private GameStateManager gameStateManager;
	GamePanel gp;
    public CardHolderPanel(GameStateManager gameStateManager, GamePanel gp) {

    	this.gp = gp;
    	this.gameStateManager = gameStateManager;
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.setOpaque(false);
        this.setVisible(true);
        
        addCards(gameStateManager);
    }
    public void handleSelection(PlantCard selectedCard) {
    	for (PlantCard card : cards) {
    		card.changeSelectedStatus(false);
    	}
    	this.selectedCard = selectedCard;
    	selectedCard.changeSelectedStatus(true);
    	selectedCardType = selectedCard.type;
    	repaint();
    }
    public void addCards(GameStateManager gameStateManager) {
    	
    	PlantCard sunflowerCard = new PlantCard(PlantType.SUNFLOWER);
        sunflowerCard.addActionListener(e -> {
        	if (gameStateManager.getGameState() != GameState.RUN){
        		return;
        	}
        	gp.shovelVisible = false;
            if (sunflowerCard.canAfford) {
            	handleSelection(sunflowerCard);
            	System.out.println("Sunflower SELECTED");
            }
        });
        add(sunflowerCard);
        
        PlantCard wallnutCard = new PlantCard(PlantType.WALLNUT);
        wallnutCard.addActionListener(e -> {
        	if (gameStateManager.getGameState() != GameState.RUN){
        		return;
        	}
        	gp.shovelVisible = false;
            if (wallnutCard.canAfford) {
            	handleSelection(wallnutCard);
            	System.out.println("Wallnut SEELCTED");
            }
        });
        add(wallnutCard);
        
    	PlantCard peaShooterCard = new PlantCard(PlantType.PEASHOOTER);
    	peaShooterCard.addActionListener(e -> {
    		if (gameStateManager.getGameState() != GameState.RUN){
        		return;
        	}
    		gp.shovelVisible = false;
            if (peaShooterCard.canAfford) {
            	handleSelection(peaShooterCard);
            	System.out.println("PeaSHOOTER SEELCTED");
            }
        });
        add(peaShooterCard);
        
        PlantCard snowPeaCard = new PlantCard(PlantType.SNOW_PEA);
        snowPeaCard.addActionListener(e -> {
        	if (gameStateManager.getGameState() != GameState.RUN){
        		return;
        	}
            if (snowPeaCard.canAfford) {
            	gp.shovelVisible = false;
            	handleSelection(snowPeaCard);
            	System.out.println("SnowPea SELECTED");
            }
        });
        add(snowPeaCard);
        
        PlantCard cherryBombCard = new PlantCard(PlantType.CHERRY_BOMB);
        cherryBombCard.addActionListener(e -> {
        	if (gameStateManager.getGameState() != GameState.RUN){
        		return;
        	}
            if (cherryBombCard.canAfford) {
            	gp.shovelVisible = false;
            	handleSelection(cherryBombCard);
            	System.out.println("Cherry Bomb SELECTED");
            }
        });
        add(cherryBombCard);
        
        cards.add(cherryBombCard);
        cards.add(snowPeaCard);
        cards.add(peaShooterCard);
        cards.add(sunflowerCard);
        cards.add(wallnutCard);
        
        
    }
    protected PlantType getPlantCardType() {
    	return selectedCardType;
    }
    protected void updateAffordability() {
    	for (PlantCard card : cards) {
    		if (card.type.getCost() > gameStateManager.getSunPoints()) {
    			card.canAfford = false;
    		}
    		else {
    			card.canAfford = true;
    		}
    	}
    }
    @Override
    public void paintComponent(Graphics g) {
    	g.drawImage(ResourceManager.cardHolderPanel, 0, 0, getWidth(), getHeight(), null);
    }
}
