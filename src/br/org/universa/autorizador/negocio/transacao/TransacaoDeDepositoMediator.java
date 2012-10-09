package br.org.universa.autorizador.negocio.transacao;

import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.conta.ContaMediator;

public class TransacaoDeDepositoMediator extends AbstractTransacaoMediator {

	@Override
	protected void executaRegrasEspecificas(Transacao transacao) {
		Conta conta = ContaMediator.get().consultaConta(transacao.getAgencia(),
				transacao.getConta());
		ContaMediator.get().atualiza(conta);
	}
}
