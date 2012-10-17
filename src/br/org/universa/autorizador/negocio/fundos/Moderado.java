package br.org.universa.autorizador.negocio.fundos;

public class Moderado implements FundoDeInvestimento {

	public double calculaRentabilidade(double valor) {
		double probabilidade = Math.random();
		if (probabilidade <= 0.5) {
			return valor * 0.006;
		} else {
			return valor * 0.015;
		}

	}

}
