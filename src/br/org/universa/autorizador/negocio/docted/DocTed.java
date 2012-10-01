package br.org.universa.autorizador.negocio.docted;


public class DocTed {

	private String identificadorDaTransacao;
	private int bancoDeOrigem;
	private int agenciaDeOrigem;
	private int contaDeOrigem;
	private String cpfDoTitularDaContaDeOrigem;
	private double valor;
	private int bancoFavorecido;
	private int agenciaFavorecida;
	private int contaFavorecida;
	private String cpfDoTitularDaContaFavorecida;
	private CategoriaDaTransferencia categoriaDaTransferencia;
	private TipoDoDocTed tipoDoDocTed;

	public String getIdentificadorDaTransacao() {
		return identificadorDaTransacao;
	}

	public void setIdentificadorDaTransacao(String identificadorDaTransacao) {
		this.identificadorDaTransacao = identificadorDaTransacao;
	}

	public int getBancoDeOrigem() {
		return bancoDeOrigem;
	}

	public void setBancoDeOrigem(int bancoDeOrigem) {
		this.bancoDeOrigem = bancoDeOrigem;
	}

	public int getAgenciaDeOrigem() {
		return agenciaDeOrigem;
	}

	public void setAgenciaDeOrigem(int agenciaDeOrigem) {
		this.agenciaDeOrigem = agenciaDeOrigem;
	}

	public int getContaDeOrigem() {
		return contaDeOrigem;
	}

	public void setContaDeOrigem(int contaDeOrigem) {
		this.contaDeOrigem = contaDeOrigem;
	}

	public String getCpfDoTitularDaContaDeOrigem() {
		return cpfDoTitularDaContaDeOrigem;
	}

	public void setCpfDoTitularDaContaDeOrigem(
			String cpfDoTitularDaContaDeOrigem) {
		this.cpfDoTitularDaContaDeOrigem = cpfDoTitularDaContaDeOrigem;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getBancoFavorecido() {
		return bancoFavorecido;
	}

	public void setBancoFavorecido(int bancoFavorecido) {
		this.bancoFavorecido = bancoFavorecido;
	}

	public int getAgenciaFavorecida() {
		return agenciaFavorecida;
	}

	public void setAgenciaFavorecida(int agenciaFavorecida) {
		this.agenciaFavorecida = agenciaFavorecida;
	}

	public int getContaFavorecida() {
		return contaFavorecida;
	}

	public void setContaFavorecida(int contaFavorecida) {
		this.contaFavorecida = contaFavorecida;
	}

	public String getCpfDoTitularDaContaFavorecida() {
		return cpfDoTitularDaContaFavorecida;
	}

	public void setCpfDoTitularDaContaFavorecida(
			String cpfDoTitularDaContaFavorecida) {
		this.cpfDoTitularDaContaFavorecida = cpfDoTitularDaContaFavorecida;
	}

	public CategoriaDaTransferencia getCategoriaDaTransferencia() {
		return categoriaDaTransferencia;
	}

	public void setCategoriaDaTransferencia(
			CategoriaDaTransferencia categoriaDaTransferencia) {
		this.categoriaDaTransferencia = categoriaDaTransferencia;
	}

	public TipoDoDocTed getTipoDoDocTed() {
		return tipoDoDocTed;
	}

	public void setTipoDoDocTed(TipoDoDocTed tipoDoDocTed) {
		this.tipoDoDocTed = tipoDoDocTed;
	}
}
