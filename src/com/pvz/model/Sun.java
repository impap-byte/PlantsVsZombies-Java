package com.pvz.model;

import java.awt.Graphics;

import com.pvz.util.ResourceManager;

public class Sun extends GameObject{
	
	private static final int SIZE = 80;
	public final int worth = 25;
	
	public Sun(int x, int y) {
		super(x ,y);
	}
	
	public void draw(Graphics g) {
		g.drawImage(ResourceManager.sun, x_pos + 15, y_pos, 100, 100, null);
	}
	
	public boolean isClicked(int mouse_x, int mouse_y) {
		if (mouse_x >= x_pos + 15 && mouse_x <= x_pos + 15 + SIZE && mouse_y >= y_pos + 15 && mouse_y <= y_pos + 15 + SIZE) {
			return true;
		}
		return false;
	}
}
