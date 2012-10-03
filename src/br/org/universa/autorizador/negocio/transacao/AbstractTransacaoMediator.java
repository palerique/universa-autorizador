package br.org.universa.autorizador.negocio.transacao;

import br.org.universa.autorizador.negocio.autorizacao.Autorizacao;
import br.org.universa.autorizador.negocio.autorizacao.EstadoDaAutorizacao;

public abstract class AbstractTransacaoMediator {

	public Autorizacao executa(Transacao transacao) {

		Autorizacao autorizacao = new Autorizacao();
		autorizacao.setEstado(EstadoDaAutorizacao.AUTORIZADA);

		// TODO Irá executar várias coisas comuns;
		try {
			executaRegrasEspecificas(transacao);
		} catch (Exception e) {
			autorizacao.setEstado(EstadoDaAutorizacao.NEGADA);
			autorizacao.setMotivoDaNegacao(e.getMessage());
		}

		return autorizacao;
	}

	protected abstract void executaRegrasEspecificas(Transacao transacao);

}
