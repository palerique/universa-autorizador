package br.org.universa.autorizador.persistencia.dao;

import java.util.HashMap;
import java.util.Map;

import br.org.universa.autorizador.negocio.cestadeservicos.TipoDaCestaDeServicos;
import br.org.universa.autorizador.negocio.comum.UtilHelper;
import br.org.universa.autorizador.negocio.conta.Cliente;
import br.org.universa.autorizador.negocio.conta.Conta;

public class ContaDAO {
	private static ContaDAO instancia = null;
	private static Map<String, Conta> contas = new HashMap<String, Conta>();

	{
		Conta conta1 = new Conta();
		conta1.setAgencia(1);
		conta1.setNumero(1);
		conta1.setTipoDaCestaDeServicos(TipoDaCestaDeServicos.ESPECIAL);

		Cliente cliente1 = new Cliente();
		cliente1.setNome("Penelope Cruz");
		cliente1.setCpf("77276469115");
		conta1.setTitular(cliente1);

		insere(conta1);

		Conta conta2 = new Conta();

		conta2.setAgencia(2);
		conta2.setNumero(2);
		conta2.setTipoDaCestaDeServicos(TipoDaCestaDeServicos.BASICA);

		Cliente cliente2 = new Cliente();
		cliente2.setNome("Nicole Kidman");
		cliente2.setCpf("02728594430");
		conta2.setTitular(cliente2);

		insere(conta2);
	}

	private ContaDAO() {
		// Construtor privado
	}

	public static ContaDAO get() {
		if (instancia == null) {
			instancia = new ContaDAO();
		}

		return instancia;
	}

	public Conta consulta(Integer agencia, Integer numero) {
		String id = UtilHelper.getAgenciaFormatada(agencia)
				+ UtilHelper.getContaFormatada(numero);

		if (contas.containsKey(id)) {
			return contas.get(id);
		}

		return null;
	}

	public void insere(Conta conta) {
		contas.put(conta.getId(), conta);
	}
	
	public void atualiza(Conta conta) {
		//Não faz nada porque está em memória.
	}
}
