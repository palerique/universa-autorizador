package br.org.universa.autorizador.negocio.fundos;

public class Agressivo implements FundoDeInvestimento {

	public double calculaRentabilidade(double valor) {
		double prob = Math.random();
		if (prob <= 0.5) {
			return valor * 0.004;
		} else if (prob <= 0.8) {
			return valor * 0.012;
		} else {
			return valor * 0.02;
		}

	}
}