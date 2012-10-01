package br.org.universa.autorizador.negocio.autorizacao;

public enum EstadoDaAutorizacao {

	AUTORIZADA(1, "Autorizada"), NEGADA(2, "Negada");

	private Integer chave;
	private String valor;

	private EstadoDaAutorizacao(Integer chave, String valor) {
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
