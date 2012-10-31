package br.org.universa.autorizador.negocio.docted;

import java.util.UUID;

import br.org.universa.autorizador.negocio.comum.UtilHelper;
import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.conta.ContaMediator;
import br.org.universa.autorizador.negocio.transacao.TransacaoDeDocTed;

public class DocTedBuilder {

	private static DocTedBuilder instancia;
	private TransacaoDeDocTed transacaoDocTed;

	public DocTed controi() {
		DocTed docTed = new DocTed();

		docTed.setAgenciaDeOrigem(transacaoDocTed.getAgencia());
		docTed.setAgenciaFavorecida(transacaoDocTed.getAgenciaFavorecida());
		docTed.setBancoDeOrigem(UtilHelper.getBancoUniversa());
		docTed.setBancoFavorecido(transacaoDocTed.getBancoFavorecido());

		if (transacaoDocTed.getValor() < 3000.0) {
			docTed.setCategoriaDaTransferencia(CategoriaDaTransferencia.DOC);
		} else {
			docTed.setCategoriaDaTransferencia(CategoriaDaTransferencia.TED);
		}
		docTed.setContaDeOrigem(transacaoDocTed.getConta());
		docTed.setContaFavorecida(transacaoDocTed.getContaFavorecida());

		Conta conta = ContaMediator.get().consultaConta(
				transacaoDocTed.getAgencia(), transacaoDocTed.getConta());

		docTed.setCpfDoTitularDaContaDeOrigem(conta.getTitular().getCpf());

		docTed.setCpfDoTitularDaContaFavorecida(transacaoDocTed
				.getCpfDoTitularDaContaFavorecida());
		docTed.setIdentificadorDaTransacao(UUID.randomUUID().toString());

		if (conta.getTitular().getCpf()
				.equals(transacaoDocTed.getCpfDoTitularDaContaFavorecida())) {
			docTed.setTipoDoDocTed(TipoDoDocTed.C);
		} else {
			docTed.setTipoDoDocTed(TipoDoDocTed.D);
		}

		docTed.setValor(transacaoDocTed.getValor());

		return docTed;
	}

	public static DocTedBuilder get() {
		if (instancia == null) {
			instancia = new DocTedBuilder();
		}
		return instancia;
	}

	public DocTedBuilder utilizandoATransacao(
			TransacaoDeDocTed transacaoDeDocTed) {
		this.transacaoDocTed = transacaoDeDocTed;
		return this;
	}

}
