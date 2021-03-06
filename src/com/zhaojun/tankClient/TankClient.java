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

import com.zhaojun.tankClient.Tank.Direction;

public class TankClient extends Frame{
	
	private static final long serialVersionUID = 8691694202623904542L;
	private List<Missile> missileList = new ArrayList<Missile>();
	private List<Explode> explodeList = new ArrayList<Explode>();
	private List<Tank> tankList = new ArrayList<Tank>();
	private Wall w1 = new Wall(200,200,300,30,this),w2 = new Wall(300,400,30,200,this);
	
	public static final int GAME_LENGTH = 800; //定义常量，灵活
	public static final int GAME_HIGTH = 600;
	private Tank myTank = new Tank(50,50,true,this,Direction.STOP);
	private Image image = this.createImage(GAME_LENGTH,GAME_HIGTH);
	public void lauchFrame(){
		for(int i=0; i<10; i++)
			tankList.add(new Tank(70*(i+1),50,false,this,Direction.DOWN));
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
		g.drawString("explodeList.count:"+explodeList.size(), 10, 70);
		g.drawString("tankList.count:"+tankList.size(), 10, 90);
		myTank.paint(g);
		w1.paint(g);
		w2.paint(g);
		for (Missile missile : missileList) {
			missile.hitTanks(tankList);
			missile.hitTank(myTank);
			missile.hitWall(w1);
			missile.hitWall(w2);
			missile.paint(g);
		}
		for (Explode explode : explodeList) {
			explode.paint(g);
		}
		for (Tank tank : tankList) {
			tank.hitWall(w1);
			tank.hitWall(w2);
			tank.hitTanks(tankList);
			tank.paint(g);			
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
