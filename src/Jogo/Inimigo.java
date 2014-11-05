package Jogo;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import neuralNetwork.Camada;
import neuralNetwork.FuncaoAtivacao;
import neuralNetwork.Rede;
 
 
public class Inimigo {
	private double x;
	private double y;
	private double dx;
	private double dy;
	private int size;
	private int prob;
	private Rede inteligencia;
	private BufferedReader br1;
	private double[] esperado;
	private int countlerarn;
	
	private Random rand = new Random();
 
	public Inimigo(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.esperado=new double[2];
		prob = rand.nextInt(10);
		String layers="";
		String[]pesos;
		String buffer;
		try{
		if(!Ambiente.isLearning())
		br1 = new BufferedReader(new FileReader("data\\dataIn.txt"));   
		else{
		br1 = new BufferedReader(new FileReader("data\\dataInTreino.txt"));
		}
        if(br1.ready()) layers = br1.readLine();
		inteligencia=new Rede(FuncaoAtivacao.SIGMOID,1,layers);
        if(br1.ready()) Ambiente.learning = br1.readLine().equals("1");
        	System.out.println("iniciando carregamento da intelig�ncia");
        if(!Ambiente.learning)
        	System.out.println("iniciando carregamento da intelig�ncia");
        	while(br1.ready()){
        		pesos=br1.readLine().split(":");
        		for(Camada c : inteligencia.camadas){
        			for(int i=0;i<c.size();i++){
        				buffer= c.getNeuronio(i).getId();
        				if(buffer.equals(pesos[0])){
        					for(int j=0;j<c.getNeuronio(i).size();j++){
        						c.getNeuronio(i).setPeso1(j,Double.parseDouble(pesos[j+1]));
        					}
        				}
        			}
        		}
        	}
			
		}catch(Exception e){
			System.out.println("Falha na leitura do arquivo dataIn");
			System.out.println(e.getStackTrace());
		}
		while(this.dx==0)
			this.dx = rand.nextDouble()*2-1;
		while(this.dy==0)
			this.dy = rand.nextDouble()*2-1;
		countlerarn=40;
 
	}
 
	public void move() throws Exception {
	
//		x += dx;
//		y += dy;
		double xb, yb;
		int limite=2;
		double [] dataEntrada = inteligencia.processa(dataIn());
		if(Ambiente.isLearning()){
//			printPesos();
			double [] t = esperado;
			inteligencia.processaBackpropagation(t);
		}
		double []movimento = dataEntrada;
		if(movimento.length!=2)System.out.println("# FALHA: resultado do processamento da rede não confere com o esperado");
		else{
			
			xb=movimento[0];
			yb=movimento[1];
//			if(xb>limite)xb=limite;
//			if(xb<(limite*(-1)))xb=(limite*(-1));
//			if(yb>limite)yb=limite;
//			if(yb<(limite*(-1)))yb=(limite*(-1));

			x=x+(xb*100);
			y=y+(yb*100);
//			System.out.println("Movimentado  x("+movimento[0]+") y("+movimento[1]+")");
		}

//		if(prob<8){
//			if(x>Ambiente.XJogador)
//				x--;
//			else
//				x++;
//			
//			if(y>Ambiente.YJogador)
//				y--;
//			else
//				y++;		
//		}

//		if(Ambiente.jogador.getTiros().size() > 0){
//			for(int i=0; i<Ambiente.jogador.getTiros().size();i++){
//				if(Ambiente.jogador.getTiros().get(i).getDir() == 1 &&
//						Ambiente.jogador.getTiros().get(i).getX() > x && 
//						Math.abs(y - Ambiente.jogador.getTiros().get(i).getY()) < 50){
//					y+=-1;
//				} else if(Ambiente.jogador.getTiros().get(i).getDir() == 2 &&
//						Ambiente.jogador.getTiros().get(i).getX() < x &&						
//						Math.abs(y - Ambiente.jogador.getTiros().get(i).getY())<50){
//					y+=-1;
//				} else if(Ambiente.jogador.getTiros().get(i).getDir() == 3 &&
//						Ambiente.jogador.getTiros().get(i).getY() > y &&
//						Math.abs(x - Ambiente.jogador.getTiros().get(i).getX())<50){
//					x+=-1;
//				} else if(Ambiente.jogador.getTiros().get(i).getDir() == 4 &&
//						Ambiente.jogador.getTiros().get(i).getY() < y &&
//						Math.abs(x - Ambiente.jogador.getTiros().get(i).getX())<50){
//					x+=-1;
//				}
//			}
//		} else {
									
			if(x <= 0) {
				x = Ambiente.largura;
			} else if (x >= Ambiente.largura) {
				x = 0;
			}
			if(y <= 0) {
				y = Ambiente.altura;
			} else if(y >= Ambiente.altura) {
				y = 0;
			}
//		}
		
	}
	private double[] getDistanciaAlvo(double xP, double yP){
		double []posicao = new double[2];
//		if(x+size<xP)posicao[0]=x+size/2-xP;
//		else 	if(x>xP) posicao[0]=xP-x+size/2;
//				else posicao[0]=0;
//		if(y+size<yP)posicao[1]=y+size/2-yP;
//		else	if(x>yP)posicao[1]=yP-y+size/2;
//				else posicao[1]=0;
//		if(x+size<xP)
			posicao[0]=(x-xP);
//		else 	if(x>xP) posicao[0]=(xP-x)/400;
//				else posicao[0]=0;
//		if(y+size<yP)
			posicao[1]=(y-yP);
//		else	if(x>yP)posicao[1]=(yP-y)/300;
//				else posicao[1]=0;
		
		return posicao; 
	}
	private double[] dataIn() throws Exception {
		String buffer[];
		double[] dataIn = new double [5];
		if(Ambiente.learning){
			try {
				if(br1.ready()){
					buffer=br1.readLine().split(",");
					if(buffer.length!=7) System.out.println("######### FALHA NA LEITURA DO TREINAMENTO!");
					for(int i=0;i<buffer.length;i++){
						if(i<5)dataIn[i]=Double.parseDouble(buffer[i]);
						else esperado[i-5]=Double.parseDouble(buffer[i]);
					}
				}else{
					if(countlerarn>0){
						br1=new BufferedReader(new FileReader("data\\dataInTreino.txt")); 
						if(br1.ready()){
							br1.readLine();
							br1.readLine();
						}
						countlerarn--;
						
						if(countlerarn==10 || countlerarn==20 ||countlerarn==30 || countlerarn==39){
							printMediadosPesos();
							
							printPesos();
						}
						System.out.println("count ="+countlerarn);
					}else{
					printPesos();
					System.out.println("count ="+countlerarn);
					System.out.println("FIM DO PROCESSO DE APRENDIZAGEM !");
					throw new Exception("TERMINO DO PROCESSO");
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			double bufferx, buffery;
			double[] alvo = getDistanciaAlvo(Ambiente.jogador.getX(),Ambiente.jogador.getY());
			double[] tiroProximo=new double[2];
			dataIn[0]=alvo[0]; //posiçao x do inimigo em relação ao jogador
			dataIn[1]=alvo[1]; //posição y do inimigo em relação ao jogador
			dataIn[2]=Ambiente.jogador.getTiros().size(); //numero de tiros existentes
			tiroProximo=getTiroProximo(x, y);
			dataIn[3]=tiroProximo[0];//posição do tiro mais proximo x
			dataIn[4]=tiroProximo[1];	//posição do tiro mais proximo y
			System.out.println("vai "+dataIn[0]+" "+dataIn[1]);
		}
		return dataIn;
	}

	private void printMediadosPesos(){
		double media[]=new double[2];

		double mediabuffer[]=new double[2];
		for(Camada cm : inteligencia.camadas){

			for(int i=0;i<cm.size();i++){
				mediabuffer=cm.getNeuronio(i).getmediaPesos();
				media[0]=media[0]+mediabuffer[0];
				media[1]=media[1]+mediabuffer[1];
			}
		}
		System.out.println("com o total de "+media[1]+" "+media[0]/media[1]);
	}
	private void printPesos() {

		String bufferline;
		for(Camada cm : inteligencia.camadas){

			bufferline="";
			for(int i=0;i<cm.size();i++){
				System.out.println(""+cm.getNeuronio(i).getId()+":"+cm.getNeuronio(i).getPesos());
			}
		}
		
	}

	private double[] getTiroProximo(double x, double y) {
		ArrayList<Tiro> tiros=Ambiente.jogador.getTiros();
		double[] danger= new double[tiros.size()];
		double[] varDouble = new double[2];
		double[] varOutBuffer= {1,1};
		double[] varOut= {1,1};
		if(!tiros.isEmpty()){
			for(Tiro buffer : tiros){
				varDouble[0]=buffer.getX();
				varDouble[1]=buffer.getY();
				if(x+size<varDouble[0])varOutBuffer[0]=(x+size-varDouble[0])/800;
				else 	if(x>varDouble[0]) varOutBuffer[0]=(varDouble[0]-x)/800;
						else varOutBuffer[0]=1;
				if(y+size<varDouble[1])varOutBuffer[1]=(y+size-varDouble[1])/600;
				else	if(x>varDouble[1])varOutBuffer[1]=(varDouble[1]-y)/600;
						else varOutBuffer[1]=1;
				if(varOut[0]>varOutBuffer[0] && varOut[1]>varOutBuffer[1]){
					varOut[0]=varOutBuffer[0];
					varOut[1]=varOutBuffer[1];
				}
			}
	
		}
		return varOut;
	}

	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.drawOval((int)x, (int)y, size, size);
		g.fillOval((int)x, (int)y, size, size);
	}
 
	public double getX() {
		return x;
	}
 
	public void setX(int x) {
		this.x = x;
	}
 
	public double getY() {
		return y;
	}
 
	public void setY(int y) {
		this.y = y;
	}
 
	public int getSize() {
		return size;
	}
 
	public void setSize(int size) {
		this.size = size;
	}
 
	public Random getRand() {
		return rand;
	}
 
	public void setRand(Random rand) {
		this.rand = rand;
	}
	
	public double[] geraT(double[] depois){
		double espectativa[]={0.0, 0.0};
		double buffer;
		double xr=x+(100*depois[0]);
		double yr=y+(100*depois[1]);
		//analisando tiro proximo X
		double []out1 = modulo(getTiroProximo(x, y));
		double []out2 = modulo(getTiroProximo(xr, yr));
 		if((out2[0]+out2[1])< 0.7){//margem de risco
 			if(out1[0] > out2[0]){
 				if(depois[0]>=0) depois[0]=-1.0;
 				if(depois[0]<0) depois[0]= 1.0;
 			}
			if(out1[1] > out2[1]){
 				if(depois[1]>=0) depois[1]=-1.0;
 				if(depois[1]<0) depois[1]= 1.0;
 			}
		}else{
			out1=modulo(getDistanciaAlvo(x, y));
			out2=modulo(getDistanciaAlvo(xr, yr));
	 		if(out1[0] > out2[0]){
 				if(depois[0]>=0) depois[0]=-1.0;
 				if(depois[0]<0) depois[0]= 1.0;
 			}
			if(out1[1] > out2[1]){
 				if(depois[1]>=0) depois[1]=-1.0;
 				if(depois[1]<0) depois[1]= 1.0;
 			}
		}
		return depois;
	}
	public double[] modulo(double in[]){
		for(int i=0;i<in.length;i++){
			if(in[i]<0)in[i]=in[i]*(-1);
		}
		return in;
	}
}


