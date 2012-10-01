package br.org.universa.autorizador.negocio.conta;

import java.util.ArrayList;
import java.util.List;

import br.org.universa.autorizador.negocio.cestadeservicos.TipoDaCestaDeServicos;
import br.org.universa.autorizador.negocio.comum.Mensagens;
import br.org.universa.autorizador.negocio.comum.UtilHelper;

public class Conta {

	private int agencia;
	private int numero;
	private Cliente titular;
	private double saldo = 0.0;
	private TipoDaCestaDeServicos tipoDaCestaDeServicos;
	private List<LancamentoDaConta> lancamentosDaConta = new ArrayList<LancamentoDaConta>();
	
	public String getId() {
		return UtilHelper.getAgenciaFormatada(agencia)
				+ UtilHelper.getContaFormatada(numero);
	}

	public int getAgencia() {
		return agencia;
	}

	public void setAgencia(int agencia) {
		this.agencia = agencia;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Cliente getTitular() {
		return titular;
	}

	public void setTitular(Cliente titular) {
		this.titular = titular;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setTipoDaCestaDeServicos(
			TipoDaCestaDeServicos tipoDaCestaDeServicos) {
		this.tipoDaCestaDeServicos = tipoDaCestaDeServicos;
	}

	public TipoDaCestaDeServicos getTipoDaCestaDeServicos() {
		return tipoDaCestaDeServicos;
	}

	public void credita(double valor) {
		saldo = saldo + valor;
	}

	public void debita(double valor) throws Exception {
		if (saldo >= valor) {
			saldo = saldo - valor;
		} else {
			throw new Exception(Mensagens.SALDO_INSUFICIENTE);
		}
	}

	public List<LancamentoDaConta> getLancamentosDaConta() {
		return lancamentosDaConta;
	}

	public void adicionaLancamentoDaConta(LancamentoDaConta lancamentoDaConta) {
		this.lancamentosDaConta.add(lancamentoDaConta);
	}
}
