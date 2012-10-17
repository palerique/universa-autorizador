package br.org.universa.autorizador.negocio.fundos;

import br.org.universa.autorizador.negocio.fundos.TipoDoFundo;

public class FundoDeInvestimentoMediator {
	private static FundoDeInvestimentoMediator instancia = null;

	private FundoDeInvestimentoMediator() {
	}

	public static FundoDeInvestimentoMediator get() {
		if (instancia == null) {
			instancia = new FundoDeInvestimentoMediator();
		}
		return instancia;

	}

	public double calculaRentabilidade(TipoDoFundo tipoDoFundo, double valor) {
		double RENTABILIDADE_LIQUIDA = 0.75;
		FundoDeInvestimento fundo = criaFundoDeInvestimento(tipoDoFundo);
		return fundo.calculaRentabilidade(valor) * RENTABILIDADE_LIQUIDA;
	}

	private FundoDeInvestimento criaFundoDeInvestimento(TipoDoFundo tipoDoFundo) {
		if (tipoDoFundo.equals(TipoDoFundo.CONSERVADOR)) {
			return new Conservador();
		} else if (tipoDoFundo.equals(TipoDoFundo.MODERADO)) {
			return new Moderado();
		} else if (tipoDoFundo.equals(TipoDoFundo.AGRESSIVO)) {
			return new Agressivo();
		} else {
			return null;
		}
	}

}
