package com.pvz.model;

import java.util.List;

public abstract class ShooterPlant extends VulnerablePlant implements Updatable{
	protected int cooldown;
	private int tickCounter = 0;
	private boolean isActive;
	protected List<Projectile> activeProjectiles;
	public ShooterPlant(int x_pos, int y_pos, List<Projectile> activeProjectiles) {
		super(x_pos, y_pos);
		this.activeProjectiles = activeProjectiles;
		this.cooldown = type.getCooldown();
	}
	
	@Override
	public void update() {
		tickCounter++;
		if (tickCounter >= cooldown) {
			shoot();
			tickCounter = 0;
		}
	}
	public void setActiveProjectiles(List<Projectile> activeProjectiles) {
		this.activeProjectiles = activeProjectiles;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public boolean isActive() {
		return isActive;
	}
	abstract void shoot();
}
