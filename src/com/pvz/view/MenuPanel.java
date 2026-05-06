package com.pvz.view;

import javax.swing.JPanel;

import com.pvz.util.ResourceManager;
import com.pvz.controller.GameStateManager;
import com.pvz.controller.GameStateManager.GameState;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Image;
public class MenuPanel extends JPanel{
	private GameStateManager gameStateManager;
	private JButton startButton;
	GamePanel gp;
	public MenuPanel(GameStateManager gameStateManager, GamePanel gp) {
		this.gp = gp;
		this.gameStateManager = gameStateManager;
		JLabel infoLabel = new JLabel();
		
		setLayout(null);
		
		Image originalImage = ResourceManager.startButtonTexture;
		int width = 400;
	    int height = 150;
	    Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	    ImageIcon icon = new ImageIcon(scaledImage);
	    
		startButton = new JButton(icon);
		startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);
        startButton.setOpaque(false);
        startButton.setBounds(730, 690, 400, 150);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Start Button Clicked!");
           
                gameStateManager.changeGameState(GameState.RUN, gp);
            }
        });
        
		add(startButton);
		this.setFocusable(true);
		this.requestFocusInWindow(true);
	
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(ResourceManager.titleBackground, 0, 0, getWidth(), getHeight(), null);
	
	}
	
}
