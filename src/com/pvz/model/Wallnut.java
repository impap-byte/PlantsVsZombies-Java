package com.pvz.model;

import java.awt.Graphics;

import com.pvz.model.*;
import com.pvz.util.ResourceManager;

public class Wallnut extends VulnerablePlant{

	public Wallnut(int x_pos, int y_pos) {
		super(x_pos, y_pos);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(ResourceManager.wallnutSprite, x_pos + 15, y_pos, 100, 100, null);
	}
	@Override
	public PlantType getType() {
		return PlantType.WALLNUT;
	}
}
