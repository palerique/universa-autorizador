package br.org.universa.autorizador.negocio.docted;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import br.org.universa.autorizador.negocio.comum.UtilHelper;

public class ArquivoDeDOCHelper {

	private static ArquivoDeDOCHelper instancia = null;
	private static final String TIPO_ARQUIVO_DOC = ".txt";
	private static final String PREFIXO_ARQUIVO_DOC = "DOC";
	private static final String DELIMITADOR = "|";

	private ArquivoDeDOCHelper() {
		// construtor privado
	}

	public static ArquivoDeDOCHelper get() {
		if (instancia == null) {
			instancia = new ArquivoDeDOCHelper();
		}

		return instancia;
	}
	
	public void registraDOC(DocTed docTed) throws Exception {
		String registro = criaRegistroDeDOCParaArquivo(docTed);

		FileWriter arquivo = ArquivoDeDOCHelper.get().getArquivo(
				getNomeDoArquivoDeDOC());
		
		ArquivoDeDOCHelper.get().gravaTextoNoArquivo(arquivo, registro);
	}

	private String criaRegistroDeDOCParaArquivo(DocTed docTed) {
		return docTed.getTipoDoDocTed().getValor() + DELIMITADOR
				+ UtilHelper.getBancoUniversaFormatado() + DELIMITADOR
				+ UtilHelper.getAgenciaFormatada(docTed.getAgenciaDeOrigem())
				+ DELIMITADOR
				+ UtilHelper.getContaFormatada(docTed.getContaDeOrigem())
				+ DELIMITADOR + UtilHelper.getValorFormatado(docTed.getValor())
				+ DELIMITADOR
				+ UtilHelper.getBancoFormatado(docTed.getBancoFavorecido())
				+ DELIMITADOR
				+ UtilHelper.getAgenciaFormatada(docTed.getAgenciaFavorecida())
				+ DELIMITADOR
				+ UtilHelper.getContaFormatada(docTed.getContaFavorecida())
				+ DELIMITADOR + docTed.getCpfDoTitularDaContaFavorecida()
				+ DELIMITADOR + docTed.getIdentificadorDaTransacao();
	}

	private String getNomeDoArquivoDeDOC() {
		/*
		 * Formato do nome do arquivo DOCBBBYYYYMMDD, onde: DOC é o prefixo do
		 * arquivo, BBB é o código do banco, YYYY é o ano, MM é o mês e DD é o
		 * dia, pois é um arquivo de DOCs por dia útil bancário.
		 */
		return PREFIXO_ARQUIVO_DOC + UtilHelper.getBancoUniversaFormatado()
				+ UtilHelper.getDataFormatadaParaArquivo(new Date())
				+ TIPO_ARQUIVO_DOC;
	}

	private FileWriter getArquivo(String nomeDoArquivo) throws Exception {
		FileWriter fw = null;
		File arquivo = new File(nomeDoArquivo);

		try {
			if (arquivo.exists()) {
				fw = new FileWriter(arquivo, true);
			} else {
				fw = new FileWriter(arquivo);
			}
		} catch (IOException e) {
			throw new Exception("Não foi possível acessar o arquivo "
					+ nomeDoArquivo, e);
		}

		return fw;
	}

	private void gravaTextoNoArquivo(FileWriter arquivo, String texto)
			throws Exception {
		FileWriter fw = arquivo;

		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(texto + "\n");
		bw.close();

		fw.close();
	}
}