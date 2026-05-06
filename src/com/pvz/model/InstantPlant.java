package com.pvz.model;

public abstract class InstantPlant extends Plant implements Updatable{
	protected int waitTime;
	protected boolean isDead;
	protected boolean showExplosion;
	private int tickCounter;
	protected InstantPlant(int x_pos, int y_pos) {
		super(x_pos, y_pos);
		waitTime = type.getWaitTime();
	}
	
	@Override
	public void update() {
		if (!isDead) {
			if (showExplosion) {
				tickCounter += 2;
				if (tickCounter >= waitTime) {
					isDead = true;
				}
			}
			else {
				tickCounter++;
				if (tickCounter >= waitTime) {
					showExplosion = true;
					tickCounter = 0;
				}
			}
		}
	}
	
	public boolean isDead() {
		return isDead;
	}
}
