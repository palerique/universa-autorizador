package br.org.universa.autorizador.negocio.transacao;

import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.conta.ContaMediator;
import br.org.universa.autorizador.negocio.conta.LancamentoDaConta;

public class TransacaoDeDocTedMediator extends AbstractTransacaoMediator {

	@Override
	protected void executaRegrasEspecificas(Transacao transacao) {
		Conta conta = ContaMediator.get().consultaConta(transacao.getAgencia(),
				transacao.getConta());

		conta.credita(transacao.getValor());

		ContaMediator.get().atualiza(conta);
	}
}
