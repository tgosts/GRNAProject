package Jogo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Jogador {
	private int x;
	private int y;
	private ArrayList<Tiro> Tiros;
 
	public Jogador(int x, int y) {
		this.x = x;
		this.y = y;

		Tiros = new ArrayList<Tiro>();
	}
 

	public void fire(int dir) {
		try {
			Tiros.add(new Tiro(x, y, dir));
		} catch ( ConcurrentModificationException e) { }
	}
 
	private void drawTiros(Graphics g) {
		try {
			for(int n = 0; n < Tiros.size(); n++) {
				Tiro b = Tiros.get(n);
				b.move();
				b.draw(g);
				
				if(b.getX()<0 || b.getX() > Ambiente.largura || b.getY()<0 || b.getY() > Ambiente.altura) {
					Tiros.remove(b);
				}
 
			}
		} catch ( ConcurrentModificationException e) { }
	}
 
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawRect(x, y, 10, 10);
		g.fillRect(x, y, 10, 10);
		drawTiros(g);
	}
		
	public void move(int dir) {
		switch (dir) {
		case 1:
			x--;
			break;
		case 2:
			x++;
			break;
		case 3:
			y--;
			break;
		case 4:
			y++;
			break;
		default:
			break;
		}
	}
	
	public int getX() {
		return x;
	}
 
	public int getY() {
		return y;
	}
 
	public ArrayList<Tiro> getTiros() {
		return Tiros;
	}
}