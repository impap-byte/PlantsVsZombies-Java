package com.pvz.model;

import java.awt.Graphics;

import com.pvz.model.Plant.PlantType;
import com.pvz.util.ResourceManager;

public class CherryBomb extends InstantPlant{

	public CherryBomb(int x_pos, int y_pos) {
		super(x_pos, y_pos);
	}

	@Override
	public void draw(Graphics g) {
		if (showExplosion) {
			g.drawImage(ResourceManager.explosionEffect, x_pos - 5, y_pos - 30, 150, 150, null);
		}
		else {
			g.drawImage(ResourceManager.cherryBombSprite, x_pos + 15, y_pos, 100, 100, null);
		}
		
	}
	@Override
	public PlantType getType() {
		return PlantType.CHERRY_BOMB;
	}

	

}
