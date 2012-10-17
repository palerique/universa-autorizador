package br.org.universa.autorizador.negocio.transacao;

import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.conta.ContaMediator;
import br.org.universa.autorizador.negocio.conta.LancamentoDaConta;
import br.org.universa.autorizador.negocio.conta.TipoDoLancamento;

public class TransacaoDeTransferenciaMediator extends AbstractTransacaoMediator {

	@Override
	protected void executaRegrasEspecificas(Transacao transacao) throws Exception {
		Conta conta = ContaMediator.get().consultaConta(transacao.getAgencia(),
				transacao.getConta());

		TransacaoDeTransferencia tdt = (TransacaoDeTransferencia) transacao;

		Conta debita = ContaMediator.get().consultaConta(tdt.getAgencia(),
				tdt.getConta());
		Conta credita = ContaMediator.get().consultaConta(
				tdt.getAgenciaFavorecida(), tdt.getContaFavorecida());

		try {
			debita.debita(tdt.getValor());
			credita.credita(tdt.getValor());

			LancamentoDaConta lancamentoDaContaDebita = new LancamentoDaConta(
					TipoDoLancamento.DEBITO, tdt.getTipoDaTransacao()
							.getValor(), tdt.getValor());

			debita.adicionaLancamentoDaConta(lancamentoDaContaDebita);
			LancamentoDaConta lancamentoDaContaCredita = new LancamentoDaConta(
					TipoDoLancamento.CREDITO, tdt.getTipoDaTransacao()
							.getValor(), tdt.getValor());

			credita.adicionaLancamentoDaConta(lancamentoDaContaCredita);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

		ContaMediator.get().atualiza(conta);
	}
}
