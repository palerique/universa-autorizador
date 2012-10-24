package br.org.universa.autorizador.negocio.tarifacao;

import br.org.universa.autorizador.negocio.cestadeservicos.CestaDeServicosHelper;
import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.transacao.Transacao;
import br.org.universa.autorizador.servico.AutorizadorFacade;

public class TarifacaoPorCesta implements Tarifacao {

	private Tarifacao proximaTarifacao;

	public double tarifa(Transacao transacao) {
		CestaDeServicosHelper helper = CestaDeServicosHelper.get();
		Conta conta = AutorizadorFacade.get().consultaConta(
				transacao.getAgencia(), transacao.getConta());

		if (helper.isTransacaoPassivelDeTarifacao(conta, transacao)) {
			if (helper.isExcedeQuantidadeContratada(conta, transacao)) {
				return helper.consultaTarifaExcedente(conta, transacao);
			}
		}

		return proximaTarifacao.tarifa(transacao);

	}

	@Override
	public void setProximaTarifacao(Tarifacao tarifacao) {
		this.proximaTarifacao = tarifacao;

	}

}
