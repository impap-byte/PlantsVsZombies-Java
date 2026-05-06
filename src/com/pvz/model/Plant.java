package com.pvz.model;

import java.awt.Image;

import com.pvz.util.ResourceManager;

public abstract class Plant extends GameObject{
	protected int sunCost;
	protected PlantType type;
	protected Plant(int x_pos, int y_pos) {
		super(x_pos, y_pos);
		this.type = getType();
		sunCost = type.getCost();
	}	
	
	public enum PlantType{
		PEASHOOTER(100, 300, "Peashooter", ResourceManager.peaShooterSprite, 90),
		SNOW_PEA(175, 300,  "Snow Pea", ResourceManager.snowPeaSprite, 90),
		CHERRY_BOMB(150, -500, "Cherry Bomb", 120, ResourceManager.cherryBombSprite),
		SUNFLOWER(50, 100, "Sunflower", ResourceManager.sunflowerSprite),
		WALLNUT(75, 4000, "Wallnut", ResourceManager.wallnutSprite);
		
		private final int cost;
		private final String name;
		private final Image sprite;
		private final int health;
		private final int cooldown;
		private final int waitTime;
		
		//Constructor for ordinary plants
		private PlantType(int cost, int health, String name, Image sprite) {
			cooldown = -1;
			waitTime = -1;
			this.cost = cost;
			this.name = name;
			this.sprite = sprite;
			this.health = health;
		}
		
		// Constructor for shooter plants
		private PlantType(int cost, int health, String name, Image sprite, int cooldown) {
			waitTime = -1;
			this.cost = cost;
			this.name = name;
			this.sprite = sprite;
			this.health = health;
			this.cooldown = cooldown;
		}
		
		// Constructor for instant plants
		private PlantType(int cost, int health, String name, int waitTime, Image sprite) {
			this.cost = cost;
			this.name = name;
			this.sprite = sprite;
			this.health = health;
			this.cooldown = -1;
			this.waitTime = waitTime;
		}
		public int getCost() { return cost; }
	    public String getName() { return name; }
	    public Image getSprite() { return sprite; }
	    public int getHealth() { return health; }
	    public int getCooldown() { return cooldown; }
	    public int getWaitTime() { return waitTime; }
	}
	
	public abstract PlantType getType();
}
