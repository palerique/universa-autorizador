package br.org.universa.autorizador.negocio.transacao;

import br.org.universa.autorizador.negocio.autorizacao.Autorizacao;
import br.org.universa.autorizador.negocio.autorizacao.EstadoDaAutorizacao;
import br.org.universa.autorizador.negocio.comum.Mensagens;
import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.conta.ContaMediator;
import br.org.universa.autorizador.negocio.conta.LancamentoDaConta;

public abstract class AbstractTransacaoMediator {

	public Autorizacao executa(Transacao transacao) {

		Autorizacao autorizacao = new Autorizacao();
		autorizacao.setEstado(EstadoDaAutorizacao.AUTORIZADA);

		// TODO Irá executar várias coisas comuns;
		try {

			if (!transacao.validaDados()) {
				throw new RuntimeException(
						Mensagens.DADOS_INSUFICIENTES_REALIZAR_TRANSACAO);
			}

//			if (transacao.getValor() <= 0) {
//				throw new RuntimeException(
//						Mensagens.DADOS_INSUFICIENTES_REALIZAR_TRANSACAO);
//			}

			Conta conta = ContaMediator.get().consultaConta(
					transacao.getAgencia(), transacao.getConta());

			LancamentoDaConta lancamentoDaConta = new LancamentoDaConta(
					transacao.getTipoDaTransacao().getTipoDoLancamento(),
					transacao.getTipoDaTransacao().getValor(),
					transacao.getValor());
			conta.adicionaLancamentoDaConta(lancamentoDaConta);

			executaRegrasEspecificas(transacao);
		} catch (Exception e) {
			autorizacao.setEstado(EstadoDaAutorizacao.NEGADA);
			autorizacao.setMotivoDaNegacao(e.getMessage());
		}

		return autorizacao;
	}

	protected abstract void executaRegrasEspecificas(Transacao transacao) throws Exception;

}
