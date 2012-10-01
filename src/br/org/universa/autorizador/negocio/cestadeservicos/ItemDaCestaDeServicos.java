package br.org.universa.autorizador.negocio.cestadeservicos;

import br.org.universa.autorizador.negocio.transacao.TipoDaTransacao;

public class ItemDaCestaDeServicos {

	private TipoDaTransacao tipoDaTransacao;
	private int quantidadeContratada;
	private double tarifaPorExcedente;

	public ItemDaCestaDeServicos(TipoDaTransacao tipoDaTransacao,
			int quantidadeContratada, double tarifaPorExcedente) {
		super();
		this.tipoDaTransacao = tipoDaTransacao;
		this.quantidadeContratada = quantidadeContratada;
		this.tarifaPorExcedente = tarifaPorExcedente;
	}

	public TipoDaTransacao getTipoDaTransacao() {
		return tipoDaTransacao;
	}

	public Integer getQuantidadeContratada() {
		return quantidadeContratada;
	}

	public double getTarifaPorExcedente() {
		return tarifaPorExcedente;
	}
}
