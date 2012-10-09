package br.org.universa.autorizador.negocio.conta;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.org.universa.autorizador.negocio.comum.EntradaDeConta;
import br.org.universa.autorizador.negocio.comum.Mensagens;
import br.org.universa.autorizador.persistencia.dao.ContaDAO;

public class ContaMediatorTest {

	@Before
	public void populaConta() {
		EntradaDeConta.get().insere(3, 3, "Nicole Cruz", "", 1890.00, null);

		EntradaDeConta.get().insere(4, 4, "Erica Cruz", "", 0.0, null);
	}

	@Test(expected = Exception.class)
	public void testSalvaContaComValorNulo() {
		ContaDAO.get().insere(null);
	}

	@Test
	public void testConsultaContaInexistente() {
		try {
			ContaMediator.get().consultaConta(1, 2);

			Assert.fail();
		} catch (Exception e) {
			Assert.assertEquals(Mensagens.NAO_EXISTE_CONTA_PARA_AGENCIA_NUMERO,
					e.getMessage());
		}
	}
}