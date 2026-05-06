package com.pvz.view;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.pvz.controller.GameStateManager;
import com.pvz.controller.GameStateManager.GameState;
import com.pvz.view.*;
import java.awt.CardLayout;

public class MainFrame extends JFrame{
	public static final int WIDTH = 1900;
	public static final int HEIGHT = 1000;
	private GameStateManager gameStateManager = new GameStateManager(this);
	
	private GamePanel gamePanel = new GamePanel(gameStateManager);
	private MenuPanel menuPanel = new MenuPanel(gameStateManager, gamePanel);
	private CardLayout cardLayout = new CardLayout();
	private JPanel container = new JPanel(cardLayout);
	
	public MainFrame() {
		super("Plants Vs Zombies");
		
		container.add(menuPanel, "MENU_SCREEN");
		container.add(gamePanel, "GAME_SCREEN");
		setResizable(false);
		add(container);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	public void handleGameStateChange() {
		if (gameStateManager.getGameState() == GameState.RUN) {
			cardLayout.show(container, "GAME_SCREEN");
			repaint();
			gamePanel.requestFocusInWindow();
		}
		else if (gameStateManager.getGameState() == GameState.MENU) {
			cardLayout.show(container, "MENU_SCREEN");
			gamePanel.requestFocusInWindow();
		}
	}
	
}
