package br.org.universa.autorizador.negocio.transacao;

import java.io.IOException;
import java.util.Properties;

public class TrasancaoFactory {

	private static TrasancaoFactory INSTANCIA;

	private TrasancaoFactory() {
		// Construtor privado
	}

	public static TrasancaoFactory get() {

		if (INSTANCIA == null) {
			INSTANCIA = new TrasancaoFactory();
		}
		return INSTANCIA;
	}

	public AbstractTransacaoMediator cria(Transacao transacao) {

		AbstractTransacaoMediator mediator = null;
		Properties properties = new Properties();

		try {
			properties.load(this.getClass().getResourceAsStream(
					"TipoDaTransacao.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String tipoDaTransacao = transacao.getTipoDaTransacao().getChave()
				.toString();

		String classeConcreta = properties.getProperty(tipoDaTransacao);

		try {
			mediator = (AbstractTransacaoMediator) Class
					.forName(classeConcreta).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return mediator;
	}
}
