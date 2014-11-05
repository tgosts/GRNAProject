package neuralNetwork;

import java.util.ArrayList;

public class Neuronio {
	private Funcao func;
	private String id;
	private double[] pesos; 
	private double[] pesos_ajust;
	private double[] pesos_variacao;
	private int nEntradas;
	private double bias;
	private double bias_var;
	private double soma;
	private double delta;
	private double resultado;
	private double momentum;
	private double[] entrada_hist;
	
	public void setBias(double bias){this.bias=bias;}
	public void setPeso(int i,double peso){this.pesos[i]=peso;}
	
	public String getId(){return id;}
	public double getPeso(int i){return pesos[i];}
	public double getBias(){return bias;}
	
	public Neuronio(String id, int nEntradas,double bias, FuncaoAtivacao func, double[] pesos){
		System.out.println("+ Criando neuronio "+id);
		this.id=id;
		this.nEntradas=nEntradas;
		this.bias=bias;
		this.func= new Funcao(func);
		this.pesos=pesos;
		this.pesos_ajust = new double[pesos.length];
		if(nEntradas!=pesos.length)System.out.println("# FALHA NA CRIAÇÃO DO NEURONIO: nao confere o numero de entradas com a quantidade de pesos!");
//		else System.out.println("+ Neuronio criado com sucesso");
		this.pesos_variacao= new double[pesos.length];
		bias_var=0.0;
	}
	public double processa(double[] entrada) {
		double soma=0;
		entrada_hist=entrada;
		if(entrada.length != pesos.length){
			System.out.println("# FALHA NO PROCESSAMENTO: nao confere o numero de entrada com o a quantidade de pesos!");
			return 0;
		}

		for(int i=0;i<entrada.length;i++){
			if(i==0)soma=entrada[i]*pesos[i];//para evitar interferencias.
			else soma=soma+(entrada[i]*pesos[i]);
		}
		this.soma=soma;
		resultado=func.calcula(soma);
		return resultado;
	}
	public double[] nOcultoAjuste(double delta_in) {
		delta=delta_in*func.calculaDerivada(soma);
		for(int i=0;i<pesos.length;i++){
			pesos_ajust[i]=geraAlfa()*delta*resultado+pesos_variacao[i];
			pesos_variacao[i]=pesos_ajust[i]+pesos_variacao[i];
		}
//		momentum = resultado - processa(entrada_hist);
		bias_var = geraAlfa()*delta+momentum*bias;
		bias=bias_var;
		double[] result = new double[pesos_variacao.length];
		result[0]=bias;
		for(int i=1;i<pesos.length;i++)result[i]=pesos[i];
		return result;
	}
	public double nSaidaAjuste(double t ){
		delta=(t-resultado)*func.calculaDerivada(soma);
		for(int i=0;i<pesos.length;i++){
			pesos_ajust[i]=geraAlfa()*delta*resultado;
			pesos_variacao[i] = pesos_ajust[i]+pesos_variacao[i];
		}
//		momentum = resultado - processa(entrada_hist);
		bias_var = geraAlfa()*delta+momentum*bias_var;

//		bias_var = geraAlfa()*delta*bias_var;
//		bias=bias_var;
		
		for(int i=0;i<pesos.length;i++){
			pesos[i]=pesos[i]+pesos_variacao[i];
		}
		return delta;
	}
	public double geraAlfa(){
		return (Math.random()/2 - 0.25)/3;//garantir pesos inicais proximo a zero -0,25 ~ 0,25
	}
	public double getVariacaoPesos(int a){
		return pesos_variacao[a];
	}
	public String getPesos() {
		String buffer = "";
		for(double a : pesos){
			buffer=buffer+a;
			if(a != pesos[pesos.length-1]);
		}
		// TODO Auto-generated method stub
		return buffer;
	}
	public void setDelta(double nSaidaAjuste) {
		this.delta=nSaidaAjuste;
		
	}
	public double getDelta(){
		return delta;
	}
	public int size(){
		return pesos.length;
	}
	public void setPeso1(int a, double b){
		if(a<pesos.length)pesos[a]=b;
		else System.out.println("FALHA ao setar peso");
	}
	public double[] getmediaPesos() {
		double buffer[] = new double[2];
		for(double p: pesos){
			buffer[0]=buffer[0]+p;
		}
		buffer[1]=pesos.length;
		return buffer;
	}
}
