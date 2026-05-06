package com.pvz.model;

import java.awt.Graphics;

import java.util.List;

import com.pvz.model.Plant.PlantType;
import com.pvz.util.ResourceManager;

public class Sunflower extends VulnerablePlant implements Producer, Updatable{
	
	private int sunTimer;
	List<Sun> activeSuns;
	
	public Sunflower(int x, int y, List<Sun> activeSuns) {
		super(x, y);
		this.sunTimer = PRODUCTION_DELAY;
		this.activeSuns = activeSuns;
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(ResourceManager.sunflowerSprite, x_pos + 15, y_pos, 100, 100, null);
	}
	
	@Override
	public void produce() {
		activeSuns.add(new Sun(x_pos, y_pos));
	}

	@Override
	public void update() {
		sunTimer--;
		if (sunTimer <= 0) {
			produce();
			sunTimer = PRODUCTION_DELAY;
		}
	}
	@Override
	public PlantType getType() {
		return PlantType.SUNFLOWER;
	}
	
}
