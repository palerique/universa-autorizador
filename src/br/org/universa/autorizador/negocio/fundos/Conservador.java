package br.org.universa.autorizador.negocio.fundos;

public class Conservador implements FundoDeInvestimento {

	public double calculaRentabilidade(double valor) {
		return valor * 0.008;
	}

}
