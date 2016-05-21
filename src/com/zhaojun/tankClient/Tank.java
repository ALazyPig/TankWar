package com.zhaojun.tankClient;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

public class Tank {				//面向对象，隐藏细节
	
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	public static final int TANK_LENGTH = 30;
	public static final int TANK_HIGTH = 30;
	private int x , y;	
	private int oldX , oldY;
	private boolean good;	
	private TankClient tankClient;
	private Direction dir;
	private Direction barrel;
	private boolean live = true;
	private static Random r = new Random();
	private int enemyStep = r.nextInt(15)+3;
	public enum Direction {
		UP,UP_RIGHT,RIGHT,RIGHT_DOWN,DOWN,LEFT_DOWN,LEFT,UP_LEFT,STOP
	};	
	private boolean[] bool= {false,false,false,false};
	public Tank(int x, int y,boolean good,Direction dir) {
		this.x = x;
		this.y = y;
		this.good = good;
		this.dir = dir;
		//this.barrel=dir == Direction.STOP?Direction.DOWN:dir;
		
	}	
	public Tank(int x, int y, boolean good, TankClient tankClient,Direction dir) {
		this(x, y,good,dir);
		this.tankClient = tankClient;
	}
	
	public void stay(){
		x = oldX;
		y = oldY;
	}

	public void paint(Graphics g) {
		if(!live) return;
		Color c = g.getColor();
		if(good)
			g.setColor(Color.red);
		else
			g.setColor(Color.blue);
		g.fillOval(x, y, TANK_LENGTH, TANK_HIGTH);
		move();
		drawBarrel(g);
		g.setColor(c);		
	}
	public void drawBarrel(Graphics g) {
		g.setColor(Color.black);
		switch (barrel){
			case UP: 		g.drawLine(x + TANK_LENGTH/2, y + TANK_HIGTH/2, x + TANK_LENGTH/2, y);break;
			case RIGHT: 	g.drawLine(x + TANK_LENGTH/2, y + TANK_HIGTH/2, x + TANK_LENGTH, y + TANK_HIGTH/2);break;
			case DOWN: 		g.drawLine(x + TANK_LENGTH/2, y + TANK_HIGTH/2, x + TANK_LENGTH/2, y + TANK_HIGTH);break;
			case LEFT: 		g.drawLine(x + TANK_LENGTH/2, y + TANK_HIGTH/2, x, y + TANK_HIGTH/2);break;
			case UP_RIGHT: 	g.drawLine(x + TANK_LENGTH/2, y + TANK_HIGTH/2, x + TANK_LENGTH, y);break;	
			case RIGHT_DOWN:g.drawLine(x + TANK_LENGTH/2, y + TANK_HIGTH/2, x + TANK_LENGTH, y + TANK_HIGTH);break;		
			case LEFT_DOWN: g.drawLine(x + TANK_LENGTH/2, y + TANK_HIGTH/2, x, y + TANK_HIGTH);break;	
			case UP_LEFT: 	g.drawLine(x + TANK_LENGTH/2, y + TANK_HIGTH/2, x, y );break;
			default:
				break; 		
		}
	}
	public void move() {
		if(!good){
			if(enemyStep == 0){
				Direction[] d = Direction.values();
				dir = d[r.nextInt(d.length)];
				enemyStep = r.nextInt(15)+3;
			}
			enemyStep--;
			if(r.nextInt(40) > 37)
				fire();
		}
		oldX = x;
		oldY = y;
		switch (dir){
			case UP: 		y-=YSPEED;			break;
			case RIGHT: 	x+=XSPEED;			break;
			case DOWN: 		y+=YSPEED;			break;
			case LEFT: 		x-=XSPEED;			break;
			case UP_RIGHT: 	x+=XSPEED;y-=YSPEED;break;		
			case RIGHT_DOWN:x+=XSPEED;y+=YSPEED;break;		
			case LEFT_DOWN: x-=XSPEED;y+=YSPEED;break;		
			case UP_LEFT: 	x-=XSPEED;y-=YSPEED;break;
			default:
				break; 		
		}
		if(this.dir != Direction.STOP) {
			this.barrel = this.dir;
		}else
			this.barrel = Direction.DOWN;
		if(x < 0) x = 0;
		if(y < 30) y = 30;
		if(x + TANK_LENGTH > TankClient.GAME_LENGTH) x = TankClient.GAME_LENGTH - Tank.TANK_LENGTH;
		if(y + TANK_HIGTH > TankClient.GAME_HIGTH) y = TankClient.GAME_HIGTH - TANK_HIGTH;
	}
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key){
			case KeyEvent.VK_RIGHT: bool[0] = true;break;
			case KeyEvent.VK_LEFT:  bool[1] = true;break;
			case KeyEvent.VK_UP: 	bool[2] = true;break;
			case KeyEvent.VK_DOWN: 	bool[3] = true;break;
		}
		moveDirection();
	}
	public void moveDirection() {
		if(bool[0] && !bool[1] && !bool[2] && !bool[3]) 	dir = Direction.RIGHT;
		else if(!bool[0] && bool[1] && !bool[2] && !bool[3])dir = Direction.LEFT;
		else if(!bool[0] && !bool[1] && bool[2] && !bool[3])dir = Direction.UP;
		else if(!bool[0] && !bool[1] && !bool[2] && bool[3])dir = Direction.DOWN;
		else if(bool[0] && !bool[1] && bool[2] && !bool[3]) dir = Direction.UP_RIGHT;
		else if(bool[0] && !bool[1] && !bool[2] && bool[3]) dir = Direction.RIGHT_DOWN;
		else if(!bool[0] && bool[1] && bool[2] && !bool[3]) dir = Direction.UP_LEFT;
		else if(!bool[0] && bool[1] && !bool[2] && bool[3]) dir = Direction.LEFT_DOWN;
	}
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key){
			case KeyEvent.VK_RIGHT: bool[0] = false;break;
			case KeyEvent.VK_LEFT:  bool[1] = false;break;
			case KeyEvent.VK_UP: 	bool[2] = false;break;
			case KeyEvent.VK_DOWN: 	bool[3] = false;break;
			case KeyEvent.VK_CONTROL:if(live) fire();break;
		}
	}
	public void fire() {
		int x = this.x + Tank.TANK_LENGTH/2 - Missile.MISSILE_LENGTH/2;
		int y = this.y + Tank.TANK_HIGTH/2 - Missile.MISSILE_HIGTH/2;
		Missile missile = new Missile(x,y,good,barrel,tankClient);
		tankClient.getMissileList().add(missile);
	}
	public boolean hitWall(Wall w){
		if(live && getRectangle().intersects(w.getRectangle())){
			stay();
			return true;
		}			
		return false;
			
	}
	public void hitTanks(List<Tank> tanks){
		for (Tank tank : tanks) {
			if(this != tank && getRectangle().intersects(tank.getRectangle())) {
				this.stay();
			}
		}						
	}
	public Rectangle getRectangle(){
		return new Rectangle(x, y, TANK_LENGTH, TANK_HIGTH);
	}
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
	public boolean isGood() {
		return good;
	}
	 
}
