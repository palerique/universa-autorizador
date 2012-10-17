package br.org.universa.autorizador.negocio.transacao;

import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.conta.ContaMediator;
import br.org.universa.autorizador.negocio.fundos.FundoDeInvestimentoMediator;
import br.org.universa.autorizador.negocio.transacao.AbstractTransacaoMediator;
import br.org.universa.autorizador.negocio.transacao.Transacao;
import br.org.universa.autorizador.negocio.transacao.TransacaoDeInvestimentoEmFundo;

public class TransacaoDeInvestimentoEmFundoMediator extends
		AbstractTransacaoMediator {

	@Override
	protected void executaRegrasEspecificas(Transacao transacao)
			throws Exception {
		TransacaoDeInvestimentoEmFundo transacaoDeInvestimentoEmFundo = (TransacaoDeInvestimentoEmFundo) transacao;

		Conta conta = ContaMediator.get().consultaConta(transacao.getAgencia(),
				transacao.getConta());

		conta.debita(transacao.getValor());

		double rentabilidadeLiquida = FundoDeInvestimentoMediator.get()
				.calculaRentabilidade(
						transacaoDeInvestimentoEmFundo.getTipoDoFundo(),
						transacao.getValor());
		
		conta.credita(transacao.getValor() + rentabilidadeLiquida);

		// gera lancamento na conta

		ContaMediator.get().atualiza(conta);

	}
}