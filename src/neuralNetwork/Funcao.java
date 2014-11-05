package neuralNetwork;

public class Funcao {
	private FuncaoAtivacao funcao;
	
	public Funcao(FuncaoAtivacao func){
		funcao=func;
	}
	
	public double calcula(double x){
		double a;
		switch(funcao){
		case SIGMOID:
			a = ((1/( 1 + Math.pow(Math.E,(-1*x))))*2)-1;
			break;
		case IN:
			a=x;
			break;
		default:
			System.out.println("# Falha(Função.java): função de ativação não identificada");
			a=0;
			break;
		}
//		System.out.println(". Recusltado da função da ativação: "+a);
		return a;
	}
	public double calculaDerivada(double x){
		double a;
		double b;
		switch(funcao){
		case SIGMOID:
			a = ((1/( 1 + Math.pow(Math.E,(-1*x))))*2)-1;
			b= a*(1-a);
			break;
		case IN:
			b=x;
			break;
		default:
			System.out.println("# Falha(Função.java): função derivada não identificada");
			b=0;
			break;
		}
//		System.out.println(". Recusltado da função da derivada: "+b);
		return b;
	}
	
}
