package br.org.universa.autorizador.negocio.autorizacao;

import br.org.universa.autorizador.negocio.transacao.Transacao;

public class Autorizacao {

	private Transacao transacao;
	private String motivoDaNegacao;
	private EstadoDaAutorizacao estado;

	public Transacao getTransacao() {
		return transacao;
	}

	public void setTransacao(Transacao transacao) {
		this.transacao = transacao;
	}

	public String getMotivoDaNegacao() {
		return motivoDaNegacao;
	}

	public void setMotivoDaNegacao(String motivoDaNegacao) {
		this.motivoDaNegacao = motivoDaNegacao;
	}

	public EstadoDaAutorizacao getEstado() {
		return estado;
	}

	public void setEstado(EstadoDaAutorizacao estado) {
		this.estado = estado;
	}
}