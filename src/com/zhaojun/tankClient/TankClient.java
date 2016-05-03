package com.zhaojun.tankClient;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankClient extends Frame{
	
	private static final long serialVersionUID = 8691694202623904542L;
	private List<Missile> missileList = new ArrayList<Missile>();
	private List<Explode> explodeList = new ArrayList<Explode>();
	public static final int GAME_LENGTH = 800; //定义常量，灵活
	public static final int GAME_HIGTH = 600;
	private Tank myTank = new Tank(50,50,true,this);
	private Tank enemyTank = new Tank(100,100,false,this);
	private Image image = this.createImage(GAME_LENGTH,GAME_HIGTH);
	public void lauchFrame(){
		this.setLocation(100, 10);
		this.setSize(GAME_LENGTH, GAME_HIGTH);
		this.setVisible(true);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter(){ //增加一个监听器，事件发生时会有什么行为呢？
			@Override
			public void windowClosing(WindowEvent e){
				setVisible(false);
		        System.exit(-1);
		    }		
		});
		this.addKeyListener(new KeyMinitor());
		new Thread(new PaintThread()).start();
	}
	public void paint(Graphics g) {
		g.drawString("missileList.count:"+missileList.size(), 10, 50);
		myTank.paint(g);	
		enemyTank.paint(g);
		for (Missile missile : missileList) {
			missile.hitTank(enemyTank);
			missile.paint(g);
		}
		for (Explode explode : explodeList) {
			explode.paint(g);
		}
	}
	
	@Override
	public void update(Graphics g) {
		if(image == null){
			image = this.createImage(GAME_LENGTH,GAME_HIGTH);
		}
		Graphics gOffScreen = image.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(getBackground());
		gOffScreen.fillRect(0, 0, GAME_LENGTH, GAME_HIGTH);
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
	private class KeyMinitor extends KeyAdapter{
		@Override
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}
		@Override
		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}
		
	}
	public static void main(String[] args){
		TankClient tankClient = new TankClient();		
		tankClient.lauchFrame();
	}
	public List<Missile> getMissileList() {
		return missileList;
	}
	public void setMissileList(List<Missile> missileList) {
		this.missileList = missileList;
	}
	public List<Explode> getExplodeList() {
		return explodeList;
	}
	public void setExplodeList(List<Explode> explodeList) {
		this.explodeList = explodeList;
	}
	
}
