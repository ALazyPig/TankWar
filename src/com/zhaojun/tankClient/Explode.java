package com.zhaojun.tankClient;

import java.awt.Color;
import java.awt.Graphics;

public class Explode {
	private int x,y;
	private boolean live = true;
	private TankClient tankClient;
	private int[] step = {4,16,25,36,49,16};
	private int i = 0;
	
	public Explode(int x, int y, TankClient tankClient) {
		this.x = x;
		this.y = y;
		this.tankClient = tankClient;
	}

	public void paint(Graphics g) {
		if(!live) 
			return;
		Color c = g.getColor();
		g.setColor(Color.black);
		g.fillOval(x, y, step[i], step[i++]);
		g.setColor(c);
		if(i == step.length){
			live = false;
			tankClient.getExplodeList().remove(this);
			i = 0;
		}
	}
}
