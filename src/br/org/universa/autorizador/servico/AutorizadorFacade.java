package br.org.universa.autorizador.servico;

import java.util.Date;
import java.util.List;

import br.org.universa.autorizador.negocio.autorizacao.Autorizacao;
import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.conta.ContaMediator;
import br.org.universa.autorizador.negocio.transacao.AbstractTransacaoMediator;
import br.org.universa.autorizador.negocio.transacao.Transacao;
import br.org.universa.autorizador.negocio.transacao.TransacaoFactory;

public class AutorizadorFacade {

	private static AutorizadorFacade INSTANCIA;

	private AutorizadorFacade() {
		// Construtor privado
	}

	public static AutorizadorFacade get() {

		if (INSTANCIA == null) {
			INSTANCIA = new AutorizadorFacade();
		}
		return INSTANCIA;
	}

	public List<Transacao> consultaTransacoes(Date dataDeReferencia) {
		// TODO Auto-generated method stub
		return null;
	}

	public Conta consultaConta(Integer agencia, Integer numero) {
		return ContaMediator.get().consultaConta(agencia, numero);
	}

	public Autorizacao executa(Transacao transacao) {

		AbstractTransacaoMediator transacaoMediator = TransacaoFactory.get()
				.cria(transacao);

		return transacaoMediator.executa(transacao);
	}

}
