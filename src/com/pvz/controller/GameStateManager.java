package com.pvz.controller;

import com.pvz.model.Sun;
import com.pvz.view.*;


public class GameStateManager {
	private boolean firstLaunch = true;
	public enum GameState{
		MENU,
		RUN,
		PAUSED,
		DEFEAT,
		VICTORY
	}
	private int sunPoints = 400;
	private GameState currentState;
	private MainFrame frame;
	
	public GameStateManager(MainFrame frame) {
		currentState = GameState.RUN;
		this.frame = frame;
		
	}
	
	public void changeGameState(GameState state, GamePanel gp) {
		if (firstLaunch && state == GameState.RUN) {
			gp.startGameThread();
			firstLaunch = false;
		}
		else if (state == GameState.PAUSED) {
			gp.pauseGame();
			gp.saveGame();
		}

		currentState = state;
		
		frame.handleGameStateChange();
	}
	public int getSunPoints() {
		return sunPoints;
	}
	public boolean purchasePlant(int cost) {
		
		if ((sunPoints - cost) >= 0) {
			sunPoints -= cost;
			return true;
		}
		else {
			return false;
		}
	}
	public GameState getGameState() {
		return currentState;
	}
	public void earnSunPoints(Sun sun) {
		sunPoints += sun.worth;
	}
	public void setSunPoints(int sunPoints) {
		this.sunPoints = sunPoints;
	}
	
}
