package Jogo;

import java.awt.Graphics;
 
public class Tiro {
	private int x;
	private int y;
	private int dir;
 
	public Tiro(int x, int y, int dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
 
	public void draw(Graphics g) {
		g.drawOval(x, y, 5, 5);
	}
 
	public void move() {
		switch (dir) {
		case 1:
			x-=3;
			break;
		case 2:
			x+=3;
			break;
		case 3:
			y-=3;
			break;
		case 4:
			y+=3;
			break;

		default:
			break;
		}
	}
 
	public int getX() {
		return x;
	}
 
	public void setX(int x) {
		this.x = x;
	}
 
	public int getY() {
		return y;
	}
 
	public void setY(int y) {
		this.y = y;
	}
	
	public int getDir() {
		return dir;
	}
 
}


