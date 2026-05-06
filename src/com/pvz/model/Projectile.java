package com.pvz.model;

import java.awt.Image;

import java.util.List;

import com.pvz.util.ResourceManager;

public abstract class Projectile extends GameObject implements Updatable, Attacker<Zombie>{
	protected int projectileSpeed;
	protected int damage;
	private int tickCounter = 0;
	protected ProjectileType projectileType;
	public Projectile(int x_pos, int y_pos) {
		super(x_pos, y_pos);
		this.projectileType = getProjectileType();
		projectileSpeed = projectileType.getSpeed();
		damage = projectileType.getDamage();
	}
	protected abstract ProjectileType getProjectileType();
	@Override
	public void update() {
		x_pos += projectileSpeed;
	}
	
	@Override
	public void attack(Zombie z) {
		if (!z.isDead) {
			if (this.projectileType == ProjectileType.FROZEN_PEA) {
		        z.slowDown();
		    }
			z.getDamaged(damage);
		}
	}
	
	public enum ProjectileType {
	    PEA(30, 4, ResourceManager.pea),
	    FROZEN_PEA(30, 4, ResourceManager.frozenPea);

	    private final int damage;
	    private final int projectileSpeed;
	    private final Image sprite;

	    ProjectileType(int damage, int projectileSpeed, Image sprite) {
	        this.damage = damage;
	        this.projectileSpeed = projectileSpeed;
	        this.sprite = sprite;
	    }

	    public int getDamage() { return damage; }
	    public int getSpeed() { return projectileSpeed; }
	    public Image getSprite() { return sprite; }
	}
}
