package com.pvz.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.pvz.util.ResourceManager;
import com.pvz.view.*;

public class BasicZombie extends Zombie implements Updatable{

	public BasicZombie(int x_pos, int y_pos) {
		super(x_pos, y_pos);
	}

	// I had to override draw in all zombie types because of size issues of the sprites i found on the internet
	@Override
	public void draw(Graphics g) {
		if (isDead) {
			g.drawImage(type.getDeadSprite(), x_pos + 15, y_pos - 45, 150, 150, null);
		}
		else {
			g.drawImage(type.getSprite()[currentFrame], x_pos + 15, y_pos - 45, 150, 150, null);
		}
		//g.fillOval(x_pos + 40, y_pos, 10, 10);
	}
	
	@Override
	public ZombieType getType() {
		return ZombieType.BASIC_ZOMBIE;
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
	    currentCol = (x_pos + 40 - GamePanel.START_X + GamePanel.TILE_WIDTH / 2) / GamePanel.TILE_WIDTH;
		currentRow = (y_pos - GamePanel.START_Y + 60) / GamePanel.TILE_HEIGHT;
	}

}
