package br.org.universa.autorizador.negocio.transacao;

import br.org.universa.autorizador.negocio.comum.UtilHelper;
import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.conta.ContaMediator;
import br.org.universa.autorizador.negocio.conta.TipoDoLancamento;
import br.org.universa.autorizador.negocio.docted.ArquivoDeDOCHelper;
import br.org.universa.autorizador.negocio.docted.CategoriaDaTransferencia;
import br.org.universa.autorizador.negocio.docted.DocTed;
import br.org.universa.autorizador.negocio.docted.DocTedBuilder;
import br.org.universa.autorizador.servico.SPBAdapter;

public class TransacaoDeDocTedMediator extends AbstractTransacaoMediator {

	@Override
	protected void executaRegrasEspecificas(Transacao transacao)
			throws Exception {
		Conta contaADebitar = ContaMediator.get().consultaConta(
				transacao.getAgencia(), transacao.getConta());

		TransacaoDeDocTed transacaoDeDocTed = (TransacaoDeDocTed) transacao;

		DocTed docTed = DocTedBuilder.get()
				.utilizandoATransacao(transacaoDeDocTed).controi();

		ContaMediator.get().geraLancamentoEmConta(contaADebitar,
				TipoDoLancamento.DEBITO,
				getDescricaoDoLancamentoO(docTed, transacaoDeDocTed),
				transacao.getValor());

		if (docTed.getCategoriaDaTransferencia().equals(
				CategoriaDaTransferencia.DOC)) {

			ArquivoDeDOCHelper.get().registraDOC(docTed);

		} else {
			SPBAdapter.get().registraTED(docTed);
		}

		contaADebitar.debita(transacao.getValor());

		ContaMediator.get().atualiza(contaADebitar);
	}

	private String getDescricaoDoLancamentoO(DocTed docTed,
			TransacaoDeDocTed transacaoDeDocTed) {
		return docTed.getCategoriaDaTransferencia().getValor()
				+ "-"
				+ docTed.getTipoDoDocTed().getValor()
				+ " para a conta "
				+ UtilHelper.getBancoFormatado(transacaoDeDocTed
						.getBancoFavorecido())
				+ ":"
				+ UtilHelper.getAgenciaFormatada(transacaoDeDocTed
						.getAgenciaFavorecida())
				+ ":"
				+ UtilHelper.getContaFormatada(transacaoDeDocTed
						.getContaFavorecida());
	}
}
