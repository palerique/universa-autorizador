package br.org.universa.autorizador.negocio.autorizacao;

public enum CanalDeAtendimento {

	TERMINAL_CAIXA(1, "Terminal de Caixa"), INTERNET_BANKING(2,
			"Internet Banking");

	private Integer chave;
	private String valor;

	private CanalDeAtendimento(Integer chave, String valor) {
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
