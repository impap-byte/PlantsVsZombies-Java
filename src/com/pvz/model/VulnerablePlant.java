package com.pvz.model;

public abstract class VulnerablePlant extends Plant implements Damageable{
	protected int health;
	protected VulnerablePlant(int x_pos, int y_pos) {
		super(x_pos, y_pos);
		health = type.getHealth();
	}
	
	@Override
	public void getDamaged(int damage) {
		//System.out.println("Im being hit!!" + health);
		
		health -= damage;
	}
	
	public int getHealth() {
		return health;
	}
	
}
