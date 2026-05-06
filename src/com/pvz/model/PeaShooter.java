package com.pvz.model;

import java.awt.Graphics;
import java.util.List;

import com.pvz.util.ResourceManager;

public class PeaShooter extends ShooterPlant{
	
	public PeaShooter(int x_pos, int y_pos, List<Projectile> activeProjectiles) {
		super(x_pos, y_pos, activeProjectiles);
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(ResourceManager.peaShooterSprite, x_pos + 15, y_pos, 100, 100, null);
	}
	
	@Override
	public PlantType getType() {
		
		return PlantType.PEASHOOTER;
	}
	
	@Override
	public void shoot() {
		activeProjectiles.add(new Pea(x_pos, y_pos));
	}
	
	public static class Pea extends Projectile{
		
		public Pea(int x_pos, int y_pos) {
			super(x_pos, y_pos);
			
		}

		@Override
		public void draw(Graphics g) {
			g.drawImage(ResourceManager.pea, x_pos + 100, y_pos + 6, 35, 35, null);
		}

		@Override
		protected ProjectileType getProjectileType() {
			
			return ProjectileType.PEA;
		}
		
	}

	

	

	

}
