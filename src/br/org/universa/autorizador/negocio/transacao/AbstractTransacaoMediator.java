package br.org.universa.autorizador.negocio.transacao;

import br.org.universa.autorizador.negocio.autorizacao.Autorizacao;
import br.org.universa.autorizador.negocio.autorizacao.EstadoDaAutorizacao;
import br.org.universa.autorizador.negocio.comum.Mensagens;
import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.conta.ContaMediator;
import br.org.universa.autorizador.negocio.conta.TipoDoLancamento;
import br.org.universa.autorizador.negocio.tarifacao.TarifacaoMediator;
import br.org.universa.autorizador.servico.SPBAdapter;

public abstract class AbstractTransacaoMediator {

	public Autorizacao executa(Transacao transacao) {

		Autorizacao autorizacao = new Autorizacao();
		autorizacao.setEstado(EstadoDaAutorizacao.AUTORIZADA);

		try {

			if (!transacao.validaDados()) {
				throw new RuntimeException(
						Mensagens.DADOS_INSUFICIENTES_REALIZAR_TRANSACAO);
			}

			Conta conta = ContaMediator.get().consultaConta(
					transacao.getAgencia(), transacao.getConta());

			executaRegrasEspecificas(transacao);
			
			SPBAdapter.get().notificaTransacao(transacao);

			verificaSeHaTarifacao(transacao, conta);

			TransacaoMediator.get().insereTransacaoDaConta(transacao);
		} catch (Exception e) {
			autorizacao.setEstado(EstadoDaAutorizacao.NEGADA);
			autorizacao.setMotivoDaNegacao(e.getMessage());
		}

		return autorizacao;
	}

	private void verificaSeHaTarifacao(Transacao transacao, Conta conta)
			throws Exception {
		double tarifa = TarifacaoMediator.get().tarifa(transacao);

		if (tarifa > 0.0) {
			conta.debita(tarifa);

			ContaMediator.get().geraLancamentoEmConta(conta,
					TipoDoLancamento.DEBITO,
					"Tarifação - " + transacao.getTipoDaTransacao().getValor(),
					tarifa);

			ContaMediator.get().atualiza(conta);
		}
	}

	protected abstract void executaRegrasEspecificas(Transacao transacao)
			throws Exception;

}
