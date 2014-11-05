package neuralNetwork;

import java.util.ArrayList;

public class Camada {
	private char id;
	private Neuronio[] neuronios;
//	private double out;
	private double[] saida;
	private double[] saidabp;
	
	public Camada(char id, int size,FuncaoAtivacao func,double bias, int nEntradas){
		neuronios= new Neuronio[size];
		this.id=id;
		double pesos[];
		for(int i=0;i<size;i++){
			pesos = new double[nEntradas];
			for(int j=0;j<nEntradas;j++){
				pesos[j]=Math.random();
				pesos[j]=pesos[j]/2 - 0.25;//garantir pesos inicais proximo a zero -0,25 ~ 0,25
			}
			System.out.print(". Pesos iniciais ["+id+i+"]: ");
			for(Double d : pesos)System.out.print(d+" ");
			System.out.println();
			neuronios[i]=new Neuronio(""+id+i, nEntradas, bias, func,pesos);
			saida = new double[neuronios.length];
		}
	}
	public Neuronio getNeuronio(int x){
		return neuronios[x];
	}
	public double[] processa(double[] entrada){
		saidabp=new double[entrada.length];
		if(id=='S'){
			saida=entrada;
		}else{
//			System.out.println(". Inicio do processamento da camada "+id+".");
			for(int i=0;i<neuronios.length;i++){
				saida[i]=neuronios[i].processa(entrada);
			}
//			System.out.println(". Termino do processamento da camada "+id+".");
		}
		return saida;
	}
	public int size() {
		// TODO Auto-generated method stub
		return neuronios.length;
	
	}
	public char getId(){
		return id;
	}
	public double[] processaBackpropagationSaida(double[] t) {
		double[] out = new double[neuronios.length];
		if(t.length!=neuronios.length)System.out.println("##### FALHA! : quanditdade de resultados esperados não equivale a quantidade de saida!");
		for(int i=0;i<neuronios.length;i++){
			out[i]=neuronios[i].nSaidaAjuste(t[i]);
		}
		return out;
		
	}
	public double getDelta(int x) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
