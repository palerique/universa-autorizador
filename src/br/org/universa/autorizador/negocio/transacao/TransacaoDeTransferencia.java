package br.org.universa.autorizador.negocio.transacao;

import br.org.universa.autorizador.negocio.comum.UtilHelper;

public class TransacaoDeTransferencia extends Transacao {

	private Integer agenciaFavorecida;
	private Integer contaFavorecida;

	public Integer getAgenciaFavorecida() {
		return agenciaFavorecida;
	}

	public void setAgenciaFavorecida(Integer agenciaFavorecida) {
		this.agenciaFavorecida = agenciaFavorecida;
	}

	public Integer getContaFavorecida() {
		return contaFavorecida;
	}

	public void setContaFavorecida(Integer contaFavorecida) {
		this.contaFavorecida = contaFavorecida;
	}

	@Override
	protected boolean validaDados() {
		return super.validaDados()
				&& UtilHelper.isCampoPreenchido(agenciaFavorecida)
				&& UtilHelper.isCampoPreenchido(contaFavorecida);
	}
}
