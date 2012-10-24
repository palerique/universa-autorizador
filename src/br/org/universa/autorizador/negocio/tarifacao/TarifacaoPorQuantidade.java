package br.org.universa.autorizador.negocio.tarifacao;

import br.org.universa.autorizador.negocio.cestadeservicos.TipoDaCestaDeServicos;
import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.transacao.Transacao;
import br.org.universa.autorizador.negocio.transacao.TransacaoMediator;
import br.org.universa.autorizador.servico.AutorizadorFacade;

public class TarifacaoPorQuantidade implements Tarifacao {

	private Tarifacao proximaTarifacao;

	public double tarifa(Transacao transacao) {
		Conta conta = AutorizadorFacade.get().consultaConta(
				transacao.getAgencia(), transacao.getConta());

		int transacoes = TransacaoMediator.get()
				.consultaQuantidadeDeTransacoesDaContaNoDia(conta) + 1;

		if (conta.getTipoDaCestaDeServicos().equals(
				TipoDaCestaDeServicos.BASICA)) {
			if (transacoes > 2) {
				return 2.0;
			}
		} else if (conta.getTipoDaCestaDeServicos().equals(
				TipoDaCestaDeServicos.ESPECIAL)) {
			if (transacoes > 4) {
				return 1.5;
			}
		}

		return proximaTarifacao.tarifa(transacao);
	}

	@Override
	public void setProximaTarifacao(Tarifacao tarifacao) {
		this.proximaTarifacao = tarifacao;

	}

}
