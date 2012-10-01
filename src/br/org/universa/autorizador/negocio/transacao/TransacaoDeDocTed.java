package br.org.universa.autorizador.negocio.transacao;

import br.org.universa.autorizador.negocio.comum.UtilHelper;

public class TransacaoDeDocTed extends TransacaoDeTransferencia {

	private Integer bancoFavorecido;
	private String cpfDoTitularDaContaFavorecida;

	public Integer getBancoFavorecido() {
		return bancoFavorecido;
	}

	public void setBancoFavorecido(Integer bancoFavorecido) {
		this.bancoFavorecido = bancoFavorecido;
	}

	public String getCpfDoTitularDaContaFavorecida() {
		return cpfDoTitularDaContaFavorecida;
	}

	public void setCpfDoTitularDaContaFavorecida(
			String cpfDoTitularDaContaFavorecida) {
		this.cpfDoTitularDaContaFavorecida = cpfDoTitularDaContaFavorecida;
	}

	@Override
	protected boolean validaDados() {
		return super.validaDados()
				&& UtilHelper.isCampoPreenchido(bancoFavorecido)
				&& UtilHelper.isCampoPreenchido(cpfDoTitularDaContaFavorecida)
				&& UtilHelper.isCpfValido(cpfDoTitularDaContaFavorecida);
	}
}
