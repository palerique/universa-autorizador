package br.org.universa.autorizador.negocio.transacao;

import br.org.universa.autorizador.negocio.comum.UtilHelper;
import br.org.universa.autorizador.negocio.fundos.TipoDoFundo;

public class TransacaoDeInvestimentoEmFundo extends Transacao {

	private TipoDoFundo tipoDoFundo;

	public TipoDoFundo getTipoDoFundo() {
		return tipoDoFundo;
	}

	public void setTipoDoFundo(TipoDoFundo tipoDoFundo) {
		this.tipoDoFundo = tipoDoFundo;
	}

	@Override
	protected boolean validaDados() {
		return super.validaDados() && UtilHelper.isCampoPreenchido(tipoDoFundo);
	}
}
