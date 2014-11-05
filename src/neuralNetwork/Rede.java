package neuralNetwork;

public class Rede {
	
	public static Camada camadas[];
	
	public Rede(FuncaoAtivacao type, double bias, String layers){
		String []layer;
		char idCamada[] = {'S','A','B','C','D','E','F','G','H','I','J','K'};
		layer=layers.split(",");
//		System.out.println("+ Criando Rede Neural com "+(layer.length)+1+" camadas.");
		
		this.camadas = new Camada[(layer.length)+1];
		int nEntradas;
		for(int i =0;i<(layer.length+1);i++){
			if(i!=0){
				nEntradas=camadas[i-1].size();
				camadas[i]=new Camada(idCamada[i], Integer.parseInt(layer[i-1]), type, bias, nEntradas);
			}else{
				camadas[i]=new Camada(idCamada[i], Integer.parseInt(layer[i]), FuncaoAtivacao.IN, bias, 1);
			}
			
		}
	}
	public double[] processa(double[] in){
//		System.out.println(""+in[0]+in[1]);
		for(Camada camada : camadas){
			in = camada.processa(in);
		}
		return in;//saida
	}
	public void processaBackpropagation(double[] t){//processamento backpropagation
		int i = camadas.length;
		double myDelta;
		double[] delta = new double[t.length];
//		camadas[i-1].processaBackpropagationSaida(t);
		//ajuste da camada de saida
		for(int n=0;n< camadas[i-1].size();n++){
			camadas[i-1].getNeuronio(n).setDelta(camadas[i-1].getNeuronio(n).nSaidaAjuste(t[n]));
		}
		//ajuste das camadas ocultas
		//preparando o delta para as camadas oculta
		for(int j = i -2; j>0;j--){
//			System.out.println("processado camada "+camadas[j].getId());
			for(int k=0;k< camadas[j].size();k++){
				myDelta=0;
				for(int x=0; x<camadas[j+1].size(); x++) myDelta=myDelta+(camadas[j+1].getNeuronio(x).getDelta()*camadas[j+1].getNeuronio(x).getVariacaoPesos(k));
				camadas[j].getNeuronio(k).nOcultoAjuste(myDelta);
			}
		}
	}
}
