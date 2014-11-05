package Jogo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
 



import javax.swing.JFrame;
import javax.swing.JOptionPane;
  
public class Ambiente extends JFrame implements KeyListener {
 

	public static int largura = 800;
	public static int altura = 600;
	public static int XJogador=0, YJogador=0;
	public static boolean learning=false;
 
	private final int N = 1; /// numero de npcs
	
	private Graphics g;
	
	
	public static Jogador jogador;
	private int fireLimit = 0;
	private ArrayList<Inimigo> inimigos;
	private ArrayList<Inimigo> inimigosPassado;
	private int fase = 1;
	private int dir=1;
 
	private Random rand = new Random();
 
	private ArrayList<Integer> keys = new ArrayList<Integer>();
 
	public static void main(String[] args)throws Exception {
		new Ambiente();
	}
 
	public Ambiente()throws Exception{
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(largura, altura);
		this.setResizable(false);
		this.setLocation(100, 100);
		this.setVisible(true);
		learning=true;
		this.addKeyListener(this);
 
		this.createBufferStrategy(2);
 
		jogador = new Jogador(largura/2, altura/2);
		 
		inimigos = new ArrayList<Inimigo>();
//		inimigosPassado = new ArrayList<Inimigo>();
		
		for(int n = 0; n < fase * N; n++) {
			inimigos.add(new Inimigo(rand.nextInt(largura), rand.nextInt(altura), 10 + rand.nextInt(63)));
		}
		
//		for (int i = 0; i < inimigos.size(); i++) {
//			inimigosPassado.add(new Inimigo((int)inimigos.get(i).getX(),(int)inimigos.get(i).getY(), inimigos.get(i).getSize()));
//		}		
//		
		//###############
		//# JOGO COMEÇA #
		//###############
		
		while(true) {
			long start = System.currentTimeMillis();
			gameLoop();
			while(System.currentTimeMillis()-start < 9){}
		}
		
	}
// ALGORITIMO GENÉTICO
//	private int roleta(int aux) {
//		int somatorio, retorno = 0;
//
//		somatorio = 0;
//		for(int i=0; i<inimigosPassado.size(); i++)
//		{
//			somatorio = somatorio + inimigosPassado.get(i).getSize();
//			if(somatorio >= aux){
//				retorno = inimigosPassado.get(i).getSize();
//				break;
//			}
//		}
//		return retorno;
//
//	}
//	ALGORITIMO GENÉTICO
//	private int geraFilhos() {
//		int som = 1;
//		for(int i=0; i<inimigosPassado.size(); i++)
//		{
//			som  += inimigosPassado.get(i).getSize();
//		}
//		
//		int x1 = rand.nextInt(som);
//		int x2 = rand.nextInt(som);
//
//		int pai = roleta(x1);
//		int mae = roleta(x2);
//		
//		char [] bin = {'0','0','0','0','0','0'};
//		String s = Integer.toBinaryString(pai);
//
//		
//		int k = 5;
//		for (int i = s.length()-1; i >= 0 ; i--) {
//			if(k<0) break;
//			bin[k] = s.charAt(i);
//			k--;
//		}
//
//		char [] bin2 = {'0','0','0','0','0','0'};
//		String s2 = Integer.toBinaryString(mae);
//
//		k = 5;
//		for (int i = s2.length()-1; i >= 0 ; i--) {
//			if(k<0) break;
//			bin2[k] = s2.charAt(i);
//			k--;
//		}
//		
//		char [] binF = {'0','0','0','0','0','0'};
//		for (int i = 0; i < 3; i++) {
//			binF[i] = bin[i];
//		}
//		for (int i = 3; i < 6; i++) {
//			binF[i] = bin2[i];
//		}
//		
//		for(int j=0; j<6; j++)
//		{
//			double tx = rand.nextDouble();
//			if(tx <= 0.002)
//			{
//				if(binF[j] == '1') binF[j] = '0'; else binF[j] = '1';
//			}
//		}
//
//		
//		int filho=0;
//		int z=0;
//		for (int i = 5; i >= 0; i--, z++) {
//			filho += Integer.parseInt(""+binF[i]) * Math.pow(2,z); 
//		}
//		
//		return filho;
//
//	}
	
	private void initGame() {

		jogador = new Jogador(largura/2, altura/2);
 
		inimigos = new ArrayList<Inimigo>();		
		
		for(int n = 0; n < fase * N; n++) {
			inimigos.add(new Inimigo(rand.nextInt(largura), rand.nextInt(altura), 10 + rand.nextInt(63)));
//			inimigos.add(new Inimigo(rand.nextInt(largura), rand.nextInt(altura), geraFilhos()));
		}
//		inimigosPassado = new ArrayList<Inimigo>();
//		for (int i = 0; i < inimigos.size(); i++) {
//			inimigosPassado.add(new Inimigo((int)inimigos.get(i).getX(),(int)inimigos.get(i).getY(), inimigos.get(i).getSize()));
//		}
//		
	}
 
	private void gameLoop() throws Exception{
		if(keys.contains(new Integer(37))) {
			jogador.move(1);
			dir = 1;
		} if(keys.contains(new Integer(39))) {
			jogador.move(2);
			dir = 2;
		} if (keys.contains(new Integer(38))) {
			jogador.move(3);
			dir = 3;
		} if (keys.contains(new Integer(40))) {
			jogador.move(4);
			dir = 4;
			}

		
		if (keys.contains(new Integer(32))) {
			if(fireLimit==0) {
				jogador.fire(dir);
				fireLimit = 5;
			} else {
				fireLimit--;
			}
		}
 
		for(Tiro b:jogador.getTiros()) {
			for(int n = 0; n < inimigos.size(); n++) {
				Inimigo r = inimigos.get(n);
				Point bp = new Point(b.getX(), b.getY());
				Point rp = new Point((int)r.getX(), (int)r.getY());
				if(bp.distance(rp) <= r.getSize()) {
					inimigos.remove(n);
					if(r.getSize()>10){
						inimigos.add(new Inimigo((int)r.getX(), (int)r.getY(), r.getSize()-5));
					}
				}
			}
		}
 
		for(int n = 0; n < inimigos.size(); n++) {
			Inimigo r = inimigos.get(n);
			Point sp = new Point(jogador.getX(), jogador.getY());
			Point rp = new Point((int)r.getX(), (int)r.getY());
			if(sp.distance(rp) <= r.getSize()&&learning==false) {
				initGame();
				mostraGameOver();				
			}
		}
 
		Ambiente.XJogador = jogador.getX();
		Ambiente.YJogador = jogador.getY();
		

		if(inimigos.size() <= 0) {
			fase++;
			for (int i = 0; i < 7; i++) {
				initGame();
			}
		}
 
		drawFrame();
	}
 
	private void mostraGameOver() {
		JOptionPane.showMessageDialog(null, "GAME OVER!!!",
				 "", 1);
		System.exit(1);
	}
	
	private void mostraFase(Graphics g) {
		g.setColor(Color.GREEN);
		g.setFont(new Font("Arial", 20, 30));
		g.drawString("fase: " + fase, 10, 50);
	}
 
	private void drawFrame() throws Exception {
		BufferStrategy bf = this.getBufferStrategy();
		//Graphics g = null;
		g = null;
 
		try {
			g = bf.getDrawGraphics();
 
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, largura, altura);
 
			for(int n = 0; n < inimigos.size(); n++) {
				Inimigo r = inimigos.get(n);
				r.move();
				r.draw(g);
			}
 
			jogador.draw(g);
			mostraFase(g);
 
		} finally {
			g.dispose();
		}
 
		bf.show();

        Toolkit.getDefaultToolkit().sync();
	}
 
	@Override
	public void keyPressed(KeyEvent e) { 
		if(!keys.contains(new Integer(e.getKeyCode())))
			keys.add(new Integer(e.getKeyCode()));
	}
 
	@Override
	public void keyReleased(KeyEvent e) { 
		keys.remove(new Integer(e.getKeyCode()));
	}
 
	@Override
	public void keyTyped(KeyEvent e) {
	}

	public static boolean isLearning() {
		return learning;
	}

	public static void setLearning(boolean learning) {
		Ambiente.learning = learning;
	}
}

