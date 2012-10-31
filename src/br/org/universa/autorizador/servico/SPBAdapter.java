package br.org.universa.autorizador.servico;

import br.com.phd.spb.servico.SPBFacade;
import br.com.phd.spb.servico.TED;
import br.com.phd.spb.servico.TransacaoSPB;
import br.org.universa.autorizador.negocio.comum.Mensagens;
import br.org.universa.autorizador.negocio.comum.UtilHelper;
import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.conta.ContaMediator;
import br.org.universa.autorizador.negocio.docted.DocTed;
import br.org.universa.autorizador.negocio.transacao.Transacao;

public class SPBAdapter {

	private static SPBAdapter instancia;

	public static SPBAdapter get() {
		if (instancia == null) {
			instancia = new SPBAdapter();
		}
		return instancia;
	}

	public void notificaTransacao(Transacao transacao) {

		if (transacao.getValor() >= 3000.00) {
			TransacaoSPB ts = SPBFacade.get().criaTransacaoSPB();

			ts.setTipoDaTransacao(transacao.getTipoDaTransacao()
					.getTipoDoLancamento().getChave());
			ts.setBanco(UtilHelper.getBancoUniversa());
			ts.setAgencia(transacao.getAgencia());
			ts.setConta(transacao.getConta());

			Conta c = ContaMediator.get().consultaConta(transacao.getAgencia(),
					transacao.getConta());

			ts.setCpfDoTitular(c.getTitular().getCpf());
			ts.setValor(transacao.getValor());
			ts.setIdentificadorDaTransacaoDeOrigem(transacao
					.getIdentificadorDaTransacao());

			int codigoDeRetorno = SPBFacade.get().notificaTransacao(ts);

			analisaCodigoDeRetornoSPB(codigoDeRetorno);
		}

	}

	private void analisaCodigoDeRetornoSPB(int codigoDeRetorno) {
		switch (codigoDeRetorno) {
		case 1:
			throw new RuntimeException(Mensagens.DADOS_INSUFICIENTES_SPB);
		case 2:
			throw new RuntimeException(Mensagens.CPF_TITULAR_CONTA_INVALIDO);
		default:
			break;
		}

	}

	public void registraTED(DocTed docTed) {

		TED ted = SPBFacade.get().criaTED();

		ted.setIdDaTransacaoDeOrigem(docTed.getIdentificadorDaTransacao());
		ted.setTipoDaTED(docTed.getTipoDoDocTed().getValor().charAt(0));
		ted.setValorDaTransacao(docTed.getValor());
		ted.setBancoDeOrigem(docTed.getBancoDeOrigem());
		ted.setAgenciaDeOrigem(docTed.getAgenciaDeOrigem());
		ted.setContaDeOrigem(docTed.getContaDeOrigem());
		ted.setCpfDaContaDeOrigem(docTed.getCpfDoTitularDaContaDeOrigem());
		ted.setBancoFavorecido(docTed.getBancoFavorecido());
		ted.setAgenciaFavorecida(docTed.getAgenciaFavorecida());
		ted.setContaFavorecida(docTed.getContaFavorecida());
		ted.setCpfDaContaFavorecida(docTed.getCpfDoTitularDaContaFavorecida());

		int codigoDeRetorno = SPBFacade.get().registraTED(ted);

		analisaCodigoDeRetornoDeTEDDoSPB(codigoDeRetorno);

	}

	private void analisaCodigoDeRetornoDeTEDDoSPB(int codigoDeRetorno) {
		switch (codigoDeRetorno) {
		case 1:
			throw new RuntimeException(
					Mensagens.DADOS_INSUFICIENTES_REGISTRO_TED);
		case 2:
			throw new RuntimeException(
					Mensagens.CPF_TITULAR_CONTA_FAVORECIDA_INVALIDO);
		case 3:
			throw new RuntimeException(Mensagens.TIPO_TED_INVALIDA);
		case 4:
			throw new RuntimeException(Mensagens.VALOR_MENOR_QUE_PERMITIDO_TED);
		default:
			break;
		}

	}
}
