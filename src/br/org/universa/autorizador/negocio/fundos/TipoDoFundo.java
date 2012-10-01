package br.org.universa.autorizador.negocio.fundos;

public enum TipoDoFundo {

	CONSERVADOR(1, "Conservador"), MODERADO(2, "Moderado"), AGRESSIVO(3,
			"Agressivo");

	private Integer chave;
	private String valor;

	private TipoDoFundo(Integer chave, String valor) {
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
