package br.org.universa.autorizador.persistencia.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.org.universa.autorizador.negocio.comum.UtilHelper;
import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.transacao.TipoDaTransacao;
import br.org.universa.autorizador.negocio.transacao.Transacao;

public class TransacaoDAO {

	private static TransacaoDAO instancia = null;
	private static Map<Conta, List<Transacao>> transacoes = new HashMap<Conta, List<Transacao>>();

	private TransacaoDAO() {
		// Construtor privado
	}

	public static TransacaoDAO get() {
		if (instancia == null) {
			instancia = new TransacaoDAO();
		}

		return instancia;
	}

	public List<Transacao> consulta(Conta conta, Date dataDeReferencia) {
		List<Transacao> transacoesDoDia = new ArrayList<Transacao>();
		List<Transacao> transacoesDaConta = getTransacoesDaConta(conta);

		Date dataAtual = UtilHelper.getDataSemHora(new Date());
		for (Transacao transacao : transacoesDaConta) {
			if (UtilHelper.getDataSemHora(transacao.getDataHoraCriacao())
					.compareTo(dataAtual) == 0) {
				transacoesDoDia.add(transacao);
			}
		}

		return transacoesDoDia;
	}

	public List<Transacao> consulta(Conta conta, Date dataDeReferencia,
			TipoDaTransacao tipoDaTransacao) {
		List<Transacao> transacoesDeMesmoTipo = new ArrayList<Transacao>();
		List<Transacao> transacoesDoDia = consulta(conta, dataDeReferencia);

		for (Transacao transacao : transacoesDoDia) {
			if (transacao.getTipoDaTransacao().equals(tipoDaTransacao)) {
				transacoesDeMesmoTipo.add(transacao);
			}
		}

		return transacoesDeMesmoTipo;
	}

	public List<Transacao> consulta(Date dataDeReferencia) {
		List<Transacao> todasTransacoes = getTodasTransacoes();
		List<Transacao> transacoesDoDia = new ArrayList<Transacao>();

		Date dataAtual = UtilHelper.getDataSemHora(new Date());
		for (Transacao transacao : todasTransacoes) {
			if (UtilHelper.getDataSemHora(transacao.getDataHoraCriacao())
					.compareTo(dataAtual) == 0) {
				transacoesDoDia.add(transacao);
			}
		}

		return transacoesDoDia;
	}

	public void insere(Conta conta, Transacao transacao) {
		List<Transacao> transacoesDaConta = getTransacoesDaConta(conta);

		transacoesDaConta.add(transacao);

		transacoes.put(conta, transacoesDaConta);
	}

	private List<Transacao> getTransacoesDaConta(Conta conta) {
		List<Transacao> transacoesDaConta = transacoes.get(conta);

		if (transacoesDaConta == null) {
			transacoesDaConta = new ArrayList<Transacao>();
		}

		return transacoesDaConta;
	}

	private List<Transacao> getTodasTransacoes() {
		List<Transacao> todasTransacoes = new ArrayList<Transacao>();

		for (List<Transacao> listaDeTransacoes : transacoes.values()) {
			todasTransacoes.addAll(listaDeTransacoes);
		}

		return todasTransacoes;
	}
}