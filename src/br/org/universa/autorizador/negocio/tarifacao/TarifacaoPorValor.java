package br.org.universa.autorizador.negocio.tarifacao;

import br.org.universa.autorizador.negocio.cestadeservicos.TipoDaCestaDeServicos;
import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.transacao.Transacao;
import br.org.universa.autorizador.servico.AutorizadorFacade;

public class TarifacaoPorValor implements Tarifacao {

	private Tarifacao proximaTarifacao;

	public double tarifa(Transacao transacao) {
		if (transacao.getValor() >= 3000.0) {

			// CestaDeServicosHelper helper = CestaDeServicosHelper.get();
			Conta conta = AutorizadorFacade.get().consultaConta(
					transacao.getAgencia(), transacao.getConta());

			if (conta.getTipoDaCestaDeServicos().equals(
					TipoDaCestaDeServicos.BASICA)) {
				return transacao.getValor() * 0.0015;
			} else {
				return transacao.getValor() * 0.001;
			}
		} else {
			return proximaTarifacao.tarifa(transacao);
		}
	}

	@Override
	public void setProximaTarifacao(Tarifacao tarifacao) {
		this.proximaTarifacao = tarifacao;

	}

}
