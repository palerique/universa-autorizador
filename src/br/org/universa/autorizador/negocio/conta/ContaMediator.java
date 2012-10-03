package br.org.universa.autorizador.negocio.conta;

import br.org.universa.autorizador.negocio.comum.Mensagens;
import br.org.universa.autorizador.persistencia.dao.ContaDAO;

public class ContaMediator {

	private static ContaMediator INSTANCIA;

	public static ContaMediator get() {

		if (INSTANCIA == null) {

			INSTANCIA = new ContaMediator();
		}
		return INSTANCIA;
	}

	public Conta consultaConta(Integer agencia, Integer numero)
			throws RuntimeException {
		Conta conta = ContaDAO.get().consulta(agencia, numero);

		if (conta == null) {
			throw new RuntimeException(
					Mensagens.NAO_EXISTE_CONTA_PARA_AGENCIA_NUMERO);
		}

		return conta;
	}

	public void atualiza(Conta conta) {
		// TODO algum comportamento;
		ContaDAO.get().atualiza(conta);
	}

}
