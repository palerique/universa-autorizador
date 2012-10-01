package br.org.universa.autorizador.negocio.comum;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UtilHelper {

	private static final Integer BANCO_UNIVERSA = 123;
	private static final DecimalFormat FORMATO_BANCO = new DecimalFormat("000");
	private static final DecimalFormat FORMATO_AGENCIA = new DecimalFormat(
			"0000");
	private static final DecimalFormat FORMATO_CONTA = new DecimalFormat(
			"0000000");
	private static final DecimalFormat FORMATO_VALOR = new DecimalFormat(
			"0000.00");
	private static final SimpleDateFormat FORMATO_DATA_ARQUIVO = new SimpleDateFormat(
			"yyyyMMdd");
	private static final SimpleDateFormat FORMATO_DATA = new SimpleDateFormat(
			"dd/MM/yyyy");
	private static final SimpleDateFormat FORMATO_DATA_HORA = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");

	public static int getBancoUniversa() {
		return BANCO_UNIVERSA;
	}

	public static String getBancoUniversaFormatado() {
		return FORMATO_BANCO.format(BANCO_UNIVERSA);
	}

	public static String getBancoFormatado(int banco) {
		return FORMATO_BANCO.format(banco);
	}

	public static String getAgenciaFormatada(int agencia) {
		return FORMATO_AGENCIA.format(agencia);
	}

	public static String getContaFormatada(int conta) {
		return FORMATO_CONTA.format(conta);
	}

	public static String getValorFormatado(double valor) {
		return FORMATO_VALOR.format(valor);
	}
	
	public static String getValorFormatado(BigDecimal valor) {
		return FORMATO_VALOR.format(valor);
	}

	public static String getDataFormatadaParaArquivo(Date data) {
		return FORMATO_DATA_ARQUIVO.format(data);
	}

	public static String getDataFormatada(Date data) {
		return FORMATO_DATA.format(data);
	}

	public static Date getData(String data) throws Exception {
		try {
			return FORMATO_DATA.parse(data);
		} catch (ParseException e) {
			throw new Exception("A data não é válida.");
		}
	}

	public static String getDataHoraFormatada(Date data) {
		return FORMATO_DATA_HORA.format(data);
	}

	public static boolean isCpfValido(String cpf) {
		int i, soma1, soma2, digito1, digito2;

		if (cpf.length() == 10) {
			cpf = "0" + cpf;
		}

		if (cpf.length() != 11)
			return false;

		if ((cpf.equals("00000000000")) || (cpf.equals("11111111111"))
				|| (cpf.equals("22222222222")) || (cpf.equals("33333333333"))
				|| (cpf.equals("44444444444")) || (cpf.equals("55555555555"))
				|| (cpf.equals("66666666666")) || (cpf.equals("77777777777"))
				|| (cpf.equals("88888888888")) || (cpf.equals("99999999999")))
			return false;

		// Calcula o primeiro dígito
		soma1 = 0;
		for (i = 0; i <= 8; i++)
			soma1 = soma1 + Integer.parseInt(cpf.substring(i, i + 1))
					* (10 - i);

		if (soma1 % 11 < 2)
			digito1 = 0;
		else
			digito1 = 11 - (soma1 % 11);

		// Calcula o segundo dígito
		soma2 = 0;
		for (i = 0; i <= 9; i++)
			soma2 = soma2 + Integer.parseInt(cpf.substring(i, i + 1))
					* (11 - i);

		if (soma2 % 11 < 2)
			digito2 = 0;
		else
			digito2 = 11 - (soma2 % 11);

		if ((digito1 == Integer.parseInt(cpf.substring(9, 10)))
				&& (digito2 == Integer.parseInt(cpf.substring(10))))
			return true;

		return false;
	}

	public static Date getDataSemHora(Date dataDeReferencia) {
		Calendar calendario;

		if (dataDeReferencia == null) {
			throw new IllegalArgumentException(
					"Data de referência não pode ser nula.");
		}

		calendario = getCalendario(dataDeReferencia);
		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.MINUTE, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);

		return calendario.getTime();
	}

	public static Calendar getCalendario(Date dataDeReferencia) {
		Calendar calendario;

		if (dataDeReferencia == null) {
			throw new IllegalArgumentException(
					"Data de referência não pode ser nula.");
		}

		calendario = Calendar.getInstance();
		calendario.setTime(dataDeReferencia);

		return calendario;
	}

	public static boolean isCampoPreenchido(String valor) {
		if (valor == null || valor.trim().equals("")) {
			return false;
		}

		return true;
	}

	public static boolean isCampoPreenchido(Object valor) {
		if (valor == null) {
			return false;
		}

		return true;
	}

	public static boolean isCampoPreenchido(double valor) {
		if (valor == 0.0) {
			return false;
		}

		return true;
	}

	public static boolean isCampoPreenchido(int valor) {
		if (valor == 0) {
			return false;
		}

		return true;
	}
	
	public static double arredonda(double valor, int numeroDeDecimais) {
		BigDecimal bd = new BigDecimal(Double.toString(valor));
		
		bd = bd.setScale(numeroDeDecimais, BigDecimal.ROUND_HALF_UP);

		return bd.doubleValue();
	}
}
