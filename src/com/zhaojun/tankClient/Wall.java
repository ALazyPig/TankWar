package com.zhaojun.tankClient;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall {
	private int x, y, w, h;
	private TankClient tankClient;
	public Wall(int x, int y, int w, int h, TankClient tankClient) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tankClient = tankClient;
	}
	public void paint(Graphics g) { 			
		Color c = g.getColor();
		g.setColor(Color.black);
		g.fillRect(x, y, w, h);
		g.setColor(c);
	}
	public Rectangle getRectangle(){
		return new Rectangle(x, y, w, h);
	}
}
