package com.zhaojun.tankClient;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class TankClient extends Frame{
	public void lauchFrame(){
		this.setLocation(100, 10);
		this.setSize(800, 600);
		this.setVisible(true);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter(){ //增加一个监听器，事件发生时会有什么行为呢？
			@Override
			public void windowClosing(WindowEvent e){
				setVisible(false);
		        System.exit(-1);}		
		});
	}
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.red);
		g.fillOval(50, 50, 30, 30);
		g.setColor(c);
	}
	public static void main(String[] args){
		TankClient tankClient = new TankClient();
		tankClient.lauchFrame();	
	}
}
