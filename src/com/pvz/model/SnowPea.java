package com.pvz.model;

import java.awt.Graphics;
import java.util.List;

import com.pvz.model.*;
import com.pvz.util.ResourceManager;

public class SnowPea extends ShooterPlant{
	
	
	
	public SnowPea(int x_pos, int y_pos, List<Projectile> activeProjectiles) {
		super(x_pos, y_pos, activeProjectiles);
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(ResourceManager.snowPeaSprite, x_pos + 15, y_pos, 100, 100, null);
	}
	
	@Override
	public PlantType getType() {
		return PlantType.SNOW_PEA;
	}
	

	@Override
	void shoot() {
		activeProjectiles.add(new FrozenPea(x_pos, y_pos));
	}
	
	public static class FrozenPea extends Projectile{
		
		public FrozenPea(int x_pos, int y_pos) {
			super(x_pos, y_pos);
			
		}

		@Override
		public void draw(Graphics g) {
			g.drawImage(ResourceManager.frozenPea, x_pos + 100, y_pos + 6, 35, 35, null);
		}

		@Override
		public ProjectileType getProjectileType() {
			return ProjectileType.FROZEN_PEA;
		}
		
	}
}
