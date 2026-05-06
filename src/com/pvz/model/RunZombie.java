package com.pvz.model;

import java.awt.Graphics;

import com.pvz.model.Zombie.ZombieType;
import com.pvz.util.ResourceManager;
import com.pvz.view.*;

public class RunZombie extends Zombie{

	public RunZombie(int x_pos, int y_pos) {
		super(x_pos, y_pos);
	}


	@Override
	public void draw(Graphics g) {
		if (isDead) {
			g.drawImage(type.getDeadSprite(), x_pos - 150 , y_pos - 300, 400, 400, null);
		}
		else {
			g.drawImage(type.getSprite()[currentFrame], x_pos + 15, y_pos - 45, 130, 140, null);
		}
		
		//g.fillOval(x_pos + 45, y_pos, 10, 10);
	}

	@Override
	public ZombieType getType() {
		return ZombieType.RUN_ZOMBIE;
	}
	@Override
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
	    currentCol = (x_pos + 45 - GamePanel.START_X + GamePanel.TILE_WIDTH / 2) / GamePanel.TILE_WIDTH;
		currentRow = (y_pos - GamePanel.START_Y + 60) / GamePanel.TILE_HEIGHT;
	}
	

}
