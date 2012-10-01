package br.org.universa.autorizador.negocio.docted;

public enum TipoDoDocTed {

	C(1, "C"), D(2, "D"), E(3, "E");

	private Integer chave;
	private String valor;

	private TipoDoDocTed(Integer chave, String valor) {
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
