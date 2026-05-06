package com.pvz.model;

import java.io.Serializable;
import java.util.List;

import com.pvz.controller.WaveManager;
import com.pvz.view.*;

public class SaveData implements Serializable{
	public int sunPoints;
	public int zombieCount;
	public List<Zombie> activeZombies;
	public List<Projectile> activeProjectiles;
	public List<Sun> activeSuns;
	public GamePanel.Tile[][] tiles;
	public int currentWave;
	public boolean isWaveActive;
	public WaveManager waveManager;
	public SaveData(int sunPoints, int zombieCount, List<Zombie> activeZombies, 
            List<Projectile> activeProjectiles, List<Sun> activeSuns, GamePanel.Tile[][] tiles, 
            boolean isWaveActive, int currentWave, WaveManager waveManager) {
			this.activeSuns = activeSuns;
			this.sunPoints = sunPoints;
			this.zombieCount = zombieCount;
			this.activeZombies = activeZombies;
			this.activeProjectiles = activeProjectiles;
			this.tiles = tiles;
			this.currentWave = currentWave;
			this.isWaveActive = isWaveActive;
			this.waveManager = waveManager;
}
}
