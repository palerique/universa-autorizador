package br.org.universa.autorizador.negocio.transacao;

import junit.framework.Assert;

import org.junit.Test;

public class TransacaoFactoryTest {

	@Test(expected = RuntimeException.class)
	public void testClassNotFoundException() {
		Transacao transacao = new Transacao();
		transacao.setTipoDaTransacao(TipoDaTransacao.PAGAMENTO_DE_TITULOS);

		TransacaoFactory.get().cria(transacao);
		Assert.fail();
	}

}
