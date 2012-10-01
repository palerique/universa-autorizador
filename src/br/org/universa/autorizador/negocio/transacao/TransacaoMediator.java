package br.org.universa.autorizador.negocio.transacao;

import java.util.Date;
import java.util.List;

import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.persistencia.dao.TransacaoDAO;

public class TransacaoMediator {
	private static TransacaoMediator instancia = null;

	private TransacaoMediator() {
		// Construtor privado
	}

	public static TransacaoMediator get() {
		if (instancia == null) {
			instancia = new TransacaoMediator();
		}

		return instancia;
	}

	public List<Transacao> consultaTransacoesDaContaNoDia(Conta conta) {
		return TransacaoDAO.get().consulta(conta, new Date());
	}

	public int consultaQuantidadeDeTransacoesDaContaNoDia(Conta conta) {
		List<Transacao> transacoesDoDia = TransacaoMediator.get()
				.consultaTransacoesDaContaNoDia(conta);

		return transacoesDoDia.size();
	}

	public List<Transacao> consultaTransacoesDaContaNoDiaPorTipoDaTransacao(
			Conta conta, Date dataDeReferencia, TipoDaTransacao tipoDaTransacao) {
		return TransacaoDAO.get().consulta(conta, dataDeReferencia,
				tipoDaTransacao);
	}

	public List<Transacao> consultaTransacoesNoDia(Date dataDeReferencia) {
		return TransacaoDAO.get().consulta(dataDeReferencia);
	}

	public void insereTransacaoDaConta(Transacao transacao) throws Exception {
		// Conta conta =
		// ContaMediator.get().consultaConta(transacao.getAgencia(),
		// transacao.getConta());
		//
		// TransacaoDAO.get().insere(conta, transacao);
		throw new Exception("Metódo não implementado");
	}
}
