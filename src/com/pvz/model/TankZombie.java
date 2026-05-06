package com.pvz.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.pvz.util.ResourceManager;
import com.pvz.view.*;

public class TankZombie extends Zombie implements Updatable{

	public TankZombie(int x_pos, int y_pos) {
		super(x_pos, y_pos);
	}

	@Override
	public void draw(Graphics g) {
		if (isDead) {
			g.drawImage(type.getDeadSprite(), x_pos + 15, y_pos - 45, 150, 150, null);
		}
		else {
			g.drawImage(type.getSprite()[currentFrame], x_pos, y_pos - 75, 180, 180, null);
		}
		
		//g.fillOval(x_pos + 50, y_pos, 10, 10);
	}
	
	@Override
	public ZombieType getType() {
		return ZombieType.TANK_ZOMBIE;
	}
	public void update() {
		if (isDead) {
			return;
		}
		animationTick++;
		if (isSlowed) {
	        slowTick--;
	        if (slowTick <= 0) isSlowed = false;
	    }
		int currentSpeed = isSlowed ? Math.max(1, speed / 2) : speed;

	    if (animationTick >= 20) {
	        currentFrame = (currentFrame + 1) % type.getSprite().length;
	        animationTick = 0;
	        
	        if (!isEating) {
	            this.x_pos -= currentSpeed;
	        }
	    }
	    currentCol = (x_pos + 50 - GamePanel.START_X + GamePanel.TILE_WIDTH / 2) / GamePanel.TILE_WIDTH;
		currentRow = (y_pos - GamePanel.START_Y + 60) / GamePanel.TILE_HEIGHT;
	}

}
