package br.org.universa.autorizador.negocio.cestadeservicos;

public enum TipoDaCestaDeServicos {

	BASICA(1, "Básica"), ESPECIAL(2, "Especial");

	private Integer chave;
	private String valor;

	private TipoDaCestaDeServicos(Integer chave, String valor) {
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
