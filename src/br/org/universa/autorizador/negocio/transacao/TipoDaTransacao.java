package br.org.universa.autorizador.negocio.transacao;

import br.org.universa.autorizador.negocio.conta.TipoDoLancamento;

public enum TipoDaTransacao {

	SAQUE_EM_CONTA(10, "Saque em Conta", TipoDoLancamento.DEBITO), DEPOSITO_EM_CONTA(
			20, "Depósito em Conta", TipoDoLancamento.CREDITO), TRANSFERENCIA_ENTRE_CONTAS(
			30, "Transferência entre Contas", TipoDoLancamento.DEBITO), INVESTIMENTO_EM_FUNDO(
			40, "Investimento em Fundo", TipoDoLancamento.CREDITO), DOC_TED_ENTRE_BANCOS(
			50, "DOC/TED entre Bancos", TipoDoLancamento.DEBITO), PAGAMENTO_DE_TITULOS(
			60, "Pagamento de Títulos", TipoDoLancamento.DEBITO);

	private Integer chave;
	private String valor;
	private TipoDoLancamento tipoDoLancamento;

	private TipoDaTransacao(Integer chave, String valor,
			TipoDoLancamento tipoDoLancamento) {
		this.chave = chave;
		this.valor = valor;
		this.tipoDoLancamento = tipoDoLancamento;
	}

	public Integer getChave() {
		return chave;
	}

	public String getValor() {
		return valor;
	}

	public TipoDoLancamento getTipoDoLancamento() {
		return tipoDoLancamento;
	}

	public static TipoDaTransacao get(String valor) {
		for (TipoDaTransacao tipoDaTransacao : TipoDaTransacao.values()) {
			if (valor.equals(tipoDaTransacao.getValor())) {
				return tipoDaTransacao;
			}
		}

		return null;
	}
}