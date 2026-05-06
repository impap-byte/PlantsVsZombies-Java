package com.pvz.model;

import java.awt.Graphics;
import java.io.Serializable;

public abstract class GameObject implements Serializable{
	protected int x_pos;
	protected int y_pos;
	protected GameObject(int x_pos, int y_pos) {
		this.x_pos = x_pos;
		this.y_pos = y_pos;
	}
	public int getX() {
		return x_pos;
	}
	public int getY() {
		return y_pos;
	}
	public abstract void draw(Graphics g);
}
