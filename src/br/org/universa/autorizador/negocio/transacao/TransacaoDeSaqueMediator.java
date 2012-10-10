package br.org.universa.autorizador.negocio.transacao;

import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.conta.ContaMediator;

public class TransacaoDeSaqueMediator extends AbstractTransacaoMediator {

	@Override
	protected void executaRegrasEspecificas(Transacao transacao) {
		Conta conta = ContaMediator.get().consultaConta(transacao.getAgencia(),
				transacao.getConta());

		try {
			conta.debita(transacao.getValor());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

		ContaMediator.get().atualiza(conta);
	}
}
