package com.pvz.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import com.pvz.util.*;
import com.pvz.view.*;

public abstract class Zombie extends GameObject implements Attacker<VulnerablePlant>, Damageable, Updatable {
	public int currentCol;
    public int currentRow;
	protected int speed;
	protected ZombieType type;
	protected int health;
	protected int damage;
	protected boolean isEating;
	protected boolean isDead = false;
    protected int deathTimer = 150;
	protected int slowTick = 0;
	private final int SLOW_DURATION = 60 * 3;
	protected boolean isSlowed = false;

	public void slowDown() {
	    this.isSlowed = true;
	    this.slowTick = SLOW_DURATION; 
	}
	

    public void setDead(boolean dead) {
    	this.isDead = dead; 
    }
    public boolean isDead() {
    	return isDead; 
    }
    
    public void updateDeath() {
        if (isDead) deathTimer--;
    }
    
    public int getDeathTimer() { return deathTimer; }
	
	protected Zombie(int x_pos, int y_pos) {
		super(x_pos, y_pos);
		type = getType();
		health = getType().getHealth();
		speed = getType().getSpeed();
		damage = getType().getDamage();
	}
	public enum ZombieType{
		BASIC_ZOMBIE(10, 200, 20, "Basic Zombie", ResourceManager.basicZombieWalk, ResourceManager.basicZombieDead),
		FAST_ZOMBIE(15, 200, 20,  "Fast Zombie", ResourceManager.fastZombieWalk, ResourceManager.fastZombieDead),
		RUN_ZOMBIE(25, 200, 20, "Run Zombie", ResourceManager.runZombieWalk, ResourceManager.runZombieDead),
		TANK_ZOMBIE(25, 800, 40, "Tank Zombie", ResourceManager.tankZombieWalk, ResourceManager.tankZombieDead);
		
		private final int speed;
		private final String name;
		private final Image[] sprite;
		private final int health;
		private final int damage; 
		private final Image deadSprite, singularSprite;
		
		private ZombieType(int speed, int health, int damage, String name, Image[] sprites, Image deadSprite) {
			this.speed = speed;
			this.name = name;
			this.sprite = sprites;
			this.health = health;
			this.damage = damage;
			this.deadSprite = deadSprite;
			this.singularSprite = null;
		}
		private ZombieType(int speed, int health, int damage, String name, Image singularSprite, Image deadSprite) {
			// Constructor in case the zombie doesnt have an animation
			this.speed = speed;
			this.name = name;
			this.sprite = null;
			this.singularSprite = singularSprite;
			this.health = health;
			this.damage = damage;
			this.deadSprite = deadSprite;
		}
		public int getSpeed() { return speed; }
	    public String getName() { return name; }
	    public Image[] getSprite() { return sprite; }
	    public int getHealth() { return health; }
	    public int getDamage() { return damage; }
	    public Image getDeadSprite() { return deadSprite; }
	}
	
	public abstract ZombieType getType();
	
	protected int animationTick = 0;
	protected int currentFrame = 0;
	
	public void setEating(boolean isEating) {
		this.isEating = isEating;
	}

	@Override
	public void attack(VulnerablePlant target) {

		if (animationTick == 18 ) {
			target.getDamaged(damage);
		}
		
	}
	@Override
	public void getDamaged(int damage) {
		health -= damage;
	}
	public int getHealth() {
		return health;
	}
	public void applyHealthBoost(int healthBoost) {
		health += healthBoost;
	}
	
	@Override
	public void update() {
		
		animationTick++;
		if (isSlowed) {
	        slowTick--;
	        if (slowTick <= 0) isSlowed = false;
	    }
		int currentSpeed = isSlowed ? Math.max(1, speed / 2) : speed;

	    if (animationTick >= 20) {
	        currentFrame = (currentFrame + 1) % type.getSprite().length;
	        animationTick = 0;
	        
	        
	    }
	    if (!isEating) {
            this.x_pos -= currentSpeed;
        }
	    //Starts from 0 and goes till COLS - 1
		currentCol = (x_pos - GamePanel.START_X + GamePanel.TILE_WIDTH / 2) / GamePanel.TILE_WIDTH;
		currentRow = (y_pos - GamePanel.START_Y + 60) / GamePanel.TILE_HEIGHT;
	    
	}
	
}
