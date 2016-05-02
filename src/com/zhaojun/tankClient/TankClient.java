package com.zhaojun.tankClient;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class TankClient extends Frame{
	private int x = 0;
	private int y = 0;
	private Image image = this.createImage(800,600);
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
		new Thread(new PaintThread()).start();
	}
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.red);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);
		y+=5;
	}
	
	@Override
	public void update(Graphics g) {
		if(image == null){
			image = this.createImage(800,600);
		}
		Graphics gOffScreen = image.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(getBackground());
		gOffScreen.fillRect(0, 0, 800, 600);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(image, 0, 0, null);
	}

	private class PaintThread implements Runnable{
		@Override
		public void run() {
			while(true){
				repaint();				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	public static void main(String[] args){
		TankClient tankClient = new TankClient();		
		tankClient.lauchFrame();
	}
}
