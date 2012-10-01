package br.org.universa.autorizador.negocio.conta;

import java.util.Date;

public class LancamentoDaConta {

	private Date dataDoLancamento;
	private TipoDoLancamento tipoDoLancamento;
	private String descricao;
	private double valor;

	public LancamentoDaConta(TipoDoLancamento tipoDoLancamento,
			String descricao, double valor) {
		super();
		this.dataDoLancamento = new Date();
		this.tipoDoLancamento = tipoDoLancamento;
		this.descricao = descricao;
		this.valor = valor;
	}

	public Date getDataDoLancamento() {
		return dataDoLancamento;
	}

	public TipoDoLancamento getTipoDoLancamento() {
		return tipoDoLancamento;
	}

	public String getDescricao() {
		return descricao;
	}

	public double getValor() {
		return valor;
	}
}
