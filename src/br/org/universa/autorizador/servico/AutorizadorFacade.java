package br.org.universa.autorizador.servico;

import java.util.Date;
import java.util.List;

import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.transacao.Transacao;

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
		// TODO Auto-generated method stub
		return null;
	}

}
