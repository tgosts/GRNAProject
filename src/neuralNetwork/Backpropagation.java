package neuralNetwork;

import java.util.HashMap;

public class Backpropagation {
	private double erro;
	private double delta_peso;
	private double momento;
	private double alvo;
	
	HashMap<String, Double> listaSoma;
	
	public Backpropagation(){
		listaSoma = new HashMap<>();
	}
	
	
	public void addSoma(String id, double soma) {
		if(listaSoma.containsKey(id)){
			listaSoma.remove(id);
		}
		listaSoma.put(id, soma);
		
	}
	public double getSoma(String id){
		return listaSoma.get(id);
	}
//	public static void start(Rede r, double[] out){
//		double t[] = buildT();
//		int i = r.camadas.length;
//		//ajuste da camada de saida
//		for(int n=0;n< r.camadas[i-1].size();n++){
//			r.camadas[i-1].getNeuronio(n).ajuste(t[n]);
//		}
//		//ajuste das camadas ocultas
//		for(int j = i -2; j>0;j--){
//			System.out.println("processado camada "+r.camadas[j].getId());
//		}
//	}
	//metodo especifico para este jogo onde sei que necessite de dois dados e sei o que espero
//	public static double[] buildT(double [] in){
//		double out[] = new double[2];
//		out[0]=analise(in,0);//x
//		out[1]=analise(in, 1);//y
//		return out;
//	}

	
}
