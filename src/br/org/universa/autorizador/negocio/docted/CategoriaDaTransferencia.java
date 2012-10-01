package br.org.universa.autorizador.negocio.docted;

public enum CategoriaDaTransferencia {

	DOC(1, "DOC"), TED(2, "TED");

	private Integer chave;
	private String valor;

	private CategoriaDaTransferencia(Integer chave, String valor) {
		this.chave = chave;
		this.valor = valor;
	}

	public Integer getChave() {
		return chave;
	}

	public String getValor() {
		return valor;
	}
}
