package com.zhaojun.tankClient;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import com.zhaojun.tankClient.Tank.Direction;

public class Missile {
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;
	public static final int MISSILE_LENGTH = 10;
	public static final int MISSILE_HIGTH = 10;
	private TankClient tankClient;
	private int x;
	private int y;
	private boolean good;
	private boolean live = true;
	private Tank.Direction dir;
	public Missile(int x, int y, boolean good, Direction dir, TankClient tankClient) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.good = good;
		this.tankClient = tankClient;
	}
	
	public void paint(Graphics g) {
		if(!live){
			return;
		} 			
		Color c = g.getColor();
		g.setColor(Color.red);
		g.fillOval(x, y, MISSILE_LENGTH, MISSILE_HIGTH);
		g.setColor(c);
		move();
	}
	public void	move(){
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
		if(x < 0 || y < 0 || x > TankClient.GAME_LENGTH || y > TankClient.GAME_HIGTH) 
			tankClient.getMissileList().remove(this);
	}
	public boolean isLive() {
		return live;
	}
	public Rectangle getRectangle(){
		return new Rectangle(x, y, MISSILE_LENGTH, MISSILE_HIGTH);
	}
	public boolean hitTank(Tank tank){
		if(tank.isGood() != good && this.getRectangle().intersects(tank.getRectangle()) && tank.isLive()) {
			tank.setLive(false);
			live = false;
			tankClient.getMissileList().remove(this);
			tankClient.getExplodeList().add(new Explode(x, y, tankClient));
			return true;
		}			
		return false;
	}
	public boolean hitTanks(List<Tank> list){
		for (Tank tank : list) {
			if(tank.isGood() != good && hitTank(tank)){
				tank.setLive(false);
				list.remove(tank);
				return true;
			}			
		}
		return false;
	}
	public boolean hitWall(Wall w){
		if(live && getRectangle().intersects(w.getRectangle())){
			live = false;
			tankClient.getMissileList().remove(this);
			return true;
		}			
		return false;			
	}
}
