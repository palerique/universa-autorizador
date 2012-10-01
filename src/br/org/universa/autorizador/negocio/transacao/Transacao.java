package br.org.universa.autorizador.negocio.transacao;

import java.util.Date;
import java.util.UUID;

import br.org.universa.autorizador.negocio.autorizacao.CanalDeAtendimento;
import br.org.universa.autorizador.negocio.comum.UtilHelper;

public class Transacao {

	private String identificadorDaTransacao;
	private Date dataHoraCriacao;
	private CanalDeAtendimento canalDeAtendimento;
	private TipoDaTransacao tipoDaTransacao;
	private int agencia;
	private int conta;
	private double valor;

	public Transacao() {
		identificadorDaTransacao = UUID.randomUUID().toString();
		dataHoraCriacao = new Date();
	}

	public String getIdentificadorDaTransacao() {
		return identificadorDaTransacao;
	}

	public Date getDataHoraCriacao() {
		return dataHoraCriacao;
	}

	public CanalDeAtendimento getCanalDeAtendimento() {
		return canalDeAtendimento;
	}

	public void setCanalDeAtendimento(CanalDeAtendimento canalDeAtendimento) {
		this.canalDeAtendimento = canalDeAtendimento;
	}

	public TipoDaTransacao getTipoDaTransacao() {
		return tipoDaTransacao;
	}

	public void setTipoDaTransacao(TipoDaTransacao tipoDaTransacao) {
		this.tipoDaTransacao = tipoDaTransacao;
	}

	public int getAgencia() {
		return agencia;
	}

	public void setAgencia(int agencia) {
		this.agencia = agencia;
	}

	public int getConta() {
		return conta;
	}

	public void setConta(int conta) {
		this.conta = conta;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	protected boolean validaDados() {
		return UtilHelper.isCampoPreenchido(getCanalDeAtendimento())
				&& UtilHelper.isCampoPreenchido(getTipoDaTransacao())
				&& UtilHelper.isCampoPreenchido(getAgencia())
				&& UtilHelper.isCampoPreenchido(getConta())
				&& UtilHelper.isCampoPreenchido(getValor());
	}
}
