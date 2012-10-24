package br.org.universa.autorizador.negocio.tarifacao;

import br.org.universa.autorizador.negocio.transacao.Transacao;

public class TarifacaoMediator {

	private static final TarifacaoMediator INSTANCIA = new TarifacaoMediator();

	private TarifacaoMediator() {
	}

	public double tarifa(Transacao transacao) {

		TarifacaoPorValor tarifacaoPorValor = new TarifacaoPorValor();
		TarifacaoPorCesta tarifacaoPorCesta = new TarifacaoPorCesta();
		TarifacaoPorQuantidade tarifacaoPorQuantidade = new TarifacaoPorQuantidade();

		tarifacaoPorValor.setProximaTarifacao(tarifacaoPorCesta);
		tarifacaoPorCesta.setProximaTarifacao(tarifacaoPorQuantidade);
		tarifacaoPorQuantidade.setProximaTarifacao(new TarifacaoVazia());

		return tarifacaoPorValor.tarifa(transacao);
	}

	public static TarifacaoMediator get() {
		return INSTANCIA;
	}
}
