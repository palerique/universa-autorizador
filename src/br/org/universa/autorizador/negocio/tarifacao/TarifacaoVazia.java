package br.org.universa.autorizador.negocio.tarifacao;

import br.org.universa.autorizador.negocio.transacao.Transacao;

public class TarifacaoVazia implements Tarifacao {

	public double tarifa(Transacao transacao) {
		return 0.0;
	}

	@Override
	public void setProximaTarifacao(Tarifacao tarifacao) {
	}

}
