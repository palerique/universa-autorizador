package br.org.universa.autorizador.negocio.transacao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.org.universa.autorizador.negocio.autorizacao.Autorizacao;
import br.org.universa.autorizador.negocio.autorizacao.CanalDeAtendimento;
import br.org.universa.autorizador.negocio.autorizacao.EstadoDaAutorizacao;
import br.org.universa.autorizador.negocio.cestadeservicos.TipoDaCestaDeServicos;
import br.org.universa.autorizador.negocio.comum.EntradaDeConta;
import br.org.universa.autorizador.negocio.comum.Mensagens;
import br.org.universa.autorizador.negocio.comum.UtilHelper;
import br.org.universa.autorizador.negocio.conta.Conta;
import br.org.universa.autorizador.negocio.conta.ContaMediator;
import br.org.universa.autorizador.negocio.conta.TipoDoLancamento;
import br.org.universa.autorizador.negocio.fundos.TipoDoFundo;

public class AbstractTransacaoMediatorTest {

	@Before
	public void populaConta() {
		EntradaDeConta.get().insere(3, 3, "Penelope Cruz", "77276469115",
				1890.00, TipoDaCestaDeServicos.ESPECIAL);

		EntradaDeConta.get().insere(4, 4, "Nicole Kidman", "02728594430",
				3010.00, TipoDaCestaDeServicos.BASICA);
	}

	@Test(expected = RuntimeException.class)
	public void testValidaTransacaoNula() {
		TransacaoFactory.get().cria(null);
	}

	@Test(expected = RuntimeException.class)
	public void testValidaTransacaoSemDadosInformados() {
		Transacao transacao = new Transacao();

		TransacaoFactory.get().cria(transacao);
	}

	@Test
	public void testValidaTransacaoComDadosInsuficientes() {
		Transacao transacao = new Transacao();
		transacao.setTipoDaTransacao(TipoDaTransacao.DEPOSITO_EM_CONTA);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.NEGADA, autorizacao.getEstado());
		Assert.assertEquals(Mensagens.DADOS_INSUFICIENTES_REALIZAR_TRANSACAO,
				autorizacao.getMotivoDaNegacao());
	}

	@Test
	public void testValidaTransacaoComValorZero() {
		Transacao transacao = new Transacao();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.INTERNET_BANKING);
		transacao.setTipoDaTransacao(TipoDaTransacao.DEPOSITO_EM_CONTA);
		transacao.setAgencia(1);
		transacao.setConta(1);
		transacao.setValor(0.0);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.NEGADA, autorizacao.getEstado());
		Assert.assertEquals(Mensagens.DADOS_INSUFICIENTES_REALIZAR_TRANSACAO,
				autorizacao.getMotivoDaNegacao());
	}

	@Test
	public void testValidaTransacaoDeInvestimentoEmFundoComDadosInsuficientes() {
		TransacaoDeInvestimentoEmFundo transacao = new TransacaoDeInvestimentoEmFundo();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.INTERNET_BANKING);
		transacao.setTipoDaTransacao(TipoDaTransacao.INVESTIMENTO_EM_FUNDO);
		transacao.setAgencia(1);
		transacao.setConta(1);
		transacao.setValor(1.00);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.NEGADA, autorizacao.getEstado());
		Assert.assertEquals(Mensagens.DADOS_INSUFICIENTES_REALIZAR_TRANSACAO,
				autorizacao.getMotivoDaNegacao());
	}

	@Test
	public void testValidaTransacaoDeTransferenciaComDadosInsuficientes() {
		TransacaoDeTransferencia transacao = new TransacaoDeTransferencia();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.INTERNET_BANKING);
		transacao
				.setTipoDaTransacao(TipoDaTransacao.TRANSFERENCIA_ENTRE_CONTAS);
		transacao.setAgencia(1);
		transacao.setConta(1);
		transacao.setValor(1.00);
		transacao.setAgenciaFavorecida(0);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.NEGADA, autorizacao.getEstado());
		Assert.assertEquals(Mensagens.DADOS_INSUFICIENTES_REALIZAR_TRANSACAO,
				autorizacao.getMotivoDaNegacao());
	}

	@Test
	public void testValidaTransacaoDeDocTedComDadosInsuficientes() {
		TransacaoDeDocTed transacao = new TransacaoDeDocTed();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.INTERNET_BANKING);
		transacao.setTipoDaTransacao(TipoDaTransacao.DOC_TED_ENTRE_BANCOS);
		transacao.setAgencia(1);
		transacao.setConta(1);
		transacao.setValor(1.00);
		transacao.setBancoFavorecido(0);
		transacao.setAgenciaFavorecida(0);
		transacao.setContaFavorecida(0);
		transacao.setCpfDoTitularDaContaFavorecida("");

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.NEGADA, autorizacao.getEstado());
		Assert.assertEquals(Mensagens.DADOS_INSUFICIENTES_REALIZAR_TRANSACAO,
				autorizacao.getMotivoDaNegacao());
	}

	@Test
	public void testSaqueEmContaSemSaldoSuficiente() {
		Transacao transacao = criaTransacaoDeSaque(1891.00);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.NEGADA, autorizacao.getEstado());
		Assert.assertEquals(Mensagens.SALDO_INSUFICIENTE,
				autorizacao.getMotivoDaNegacao());
	}

	@Test
	public void testSaqueEmConta() {
		Transacao transacao = criaTransacaoDeSaque(90.00);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			Conta conta = ContaMediator.get().consultaConta(3, 3);
			Assert.assertEquals(1800.00, conta.getSaldo());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testVerificaLancamentoEmContaParaSaque() {
		Transacao transacao = criaTransacaoDeSaque(90.00);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			Conta conta = ContaMediator.get().consultaConta(3, 3);
			if (!conta.getLancamentosDaConta().isEmpty()) {
				Assert.assertEquals(90.00, conta.getLancamentosDaConta().get(0)
						.getValor());
				Assert.assertEquals(TipoDoLancamento.DEBITO, conta
						.getLancamentosDaConta().get(0).getTipoDoLancamento());
				Assert.assertEquals(TipoDaTransacao.SAQUE_EM_CONTA.getValor(),
						conta.getLancamentosDaConta().get(0).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testDepositaEmConta() {
		Transacao transacao = criaTransacaoDeDeposito(99.99);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			Conta conta = ContaMediator.get().consultaConta(3, 3);
			Assert.assertEquals(1989.99, conta.getSaldo());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testVerificaLancamentoEmContaParaDeposito() {
		Transacao transacao = criaTransacaoDeDeposito(99.99);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			Conta conta = ContaMediator.get().consultaConta(3, 3);
			if (!conta.getLancamentosDaConta().isEmpty()) {
				Assert.assertEquals(99.99, conta.getLancamentosDaConta().get(0)
						.getValor());
				Assert.assertEquals(TipoDoLancamento.CREDITO, conta
						.getLancamentosDaConta().get(0).getTipoDoLancamento());
				Assert.assertEquals(
						TipoDaTransacao.DEPOSITO_EM_CONTA.getValor(), conta
								.getLancamentosDaConta().get(0).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testTransfereEntreContasSemSaldoSuficiente() {
		TransacaoDeTransferencia transacao = criaTransacaoDeTransferencia(1890.01);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.NEGADA, autorizacao.getEstado());
		Assert.assertEquals(Mensagens.SALDO_INSUFICIENTE,
				autorizacao.getMotivoDaNegacao());

	}

	@Test
	public void testTransfereEntreContas() {
		TransacaoDeTransferencia transacao = criaTransacaoDeTransferencia(99.99);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			Conta contaDeDebito = ContaMediator.get().consultaConta(3, 3);
			Assert.assertEquals(1790.01, contaDeDebito.getSaldo());

			Conta contaFavorecida = ContaMediator.get().consultaConta(4, 4);
			Assert.assertEquals(3109.99, contaFavorecida.getSaldo());
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testVerificaLancamentosEmContasParaTransferenciaEntreContas() {
		TransacaoDeTransferencia transacao = criaTransacaoDeTransferencia(90.00);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			Conta contaDeDebito = ContaMediator.get().consultaConta(3, 3);
			if (contaDeDebito.getLancamentosDaConta().isEmpty()) {
				Assert.fail();
			} else {
				Assert.assertEquals(90.00, contaDeDebito
						.getLancamentosDaConta().get(0).getValor());
				Assert.assertEquals(TipoDoLancamento.DEBITO, contaDeDebito
						.getLancamentosDaConta().get(0).getTipoDoLancamento());
				Assert.assertEquals(
						TipoDaTransacao.TRANSFERENCIA_ENTRE_CONTAS.getValor(),
						contaDeDebito.getLancamentosDaConta().get(0)
								.getDescricao());
			}

			Conta contaFavorecida = ContaMediator.get().consultaConta(4, 4);
			if (contaFavorecida.getLancamentosDaConta().isEmpty()) {
				Assert.fail();
			} else {
				Assert.assertEquals(90.00, contaFavorecida
						.getLancamentosDaConta().get(0).getValor());
				Assert.assertEquals(TipoDoLancamento.CREDITO, contaFavorecida
						.getLancamentosDaConta().get(0).getTipoDoLancamento());
				Assert.assertEquals(
						TipoDaTransacao.TRANSFERENCIA_ENTRE_CONTAS.getValor(),
						contaFavorecida.getLancamentosDaConta().get(0)
								.getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testInvesteEmFundoDeInvestimentoSemSaldoSuficiente() {
		TransacaoDeInvestimentoEmFundo transacao = criaTransacaoDeInvestimentoEmFundo(
				1891.00, TipoDoFundo.CONSERVADOR);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.NEGADA, autorizacao.getEstado());
		Assert.assertEquals(Mensagens.SALDO_INSUFICIENTE,
				autorizacao.getMotivoDaNegacao());

	}

	private void investeEmFundoDeInvestimento(double valor,
			TipoDoFundo tipoDoFundo) {
		TransacaoDeInvestimentoEmFundo transacao = criaTransacaoDeInvestimentoEmFundo(
				valor, tipoDoFundo);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());
	}

	@Test
	public void testInvesteEmFundoDeInvestimentoConservador() {
		investeEmFundoDeInvestimento(1890.00, TipoDoFundo.CONSERVADOR);

		try {
			Conta conta = ContaMediator.get().consultaConta(3, 3);

			double rentabilidadeLiquida = UtilHelper.arredonda(
					conta.getSaldo() - 1890.00, 2);

			Assert.assertEquals(11.34, rentabilidadeLiquida);

			if (conta.getLancamentosDaConta().isEmpty()) {
				Assert.fail();
			} else {
				Assert.assertEquals(rentabilidadeLiquida, conta
						.getLancamentosDaConta().get(0).getValor());
				Assert.assertEquals(TipoDoLancamento.CREDITO, conta
						.getLancamentosDaConta().get(0).getTipoDoLancamento());
				Assert.assertEquals(
						TipoDaTransacao.INVESTIMENTO_EM_FUNDO.getValor() + " "
								+ TipoDoFundo.CONSERVADOR.getValor(), conta
								.getLancamentosDaConta().get(0).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testInvesteEmFundoDeInvestimentoModerado() {
		investeEmFundoDeInvestimento(1890.00, TipoDoFundo.MODERADO);

		try {
			Conta conta = ContaMediator.get().consultaConta(3, 3);

			double rentabilidadeLiquida = UtilHelper.arredonda(
					conta.getSaldo() - 1890.00, 2);

			if (rentabilidadeLiquida == 8.51 || (rentabilidadeLiquida == 21.26)) {
				Assert.assertTrue(true);
			} else {
				Assert.assertTrue(false);
			}

			if (conta.getLancamentosDaConta().isEmpty()) {
				Assert.fail();
			} else {
				Assert.assertEquals(rentabilidadeLiquida, UtilHelper.arredonda(
						conta.getLancamentosDaConta().get(0).getValor(), 2),
						0.1);
				Assert.assertEquals(TipoDoLancamento.CREDITO, conta
						.getLancamentosDaConta().get(0).getTipoDoLancamento());
				Assert.assertEquals(
						TipoDaTransacao.INVESTIMENTO_EM_FUNDO.getValor() + " "
								+ TipoDoFundo.MODERADO.getValor(), conta
								.getLancamentosDaConta().get(0).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testSaldoDaContaAposAplicacaoEmFundoAgressivo() {
		investeEmFundoDeInvestimento(1890.00, TipoDoFundo.AGRESSIVO);

		try {
			Conta conta = ContaMediator.get().consultaConta(3, 3);

			double rentabilidadeLiquida = UtilHelper.arredonda(
					conta.getSaldo() - 1890.00, 2);

			if (rentabilidadeLiquida == 5.67 || rentabilidadeLiquida == 17.01
					|| rentabilidadeLiquida == 28.35) {
				Assert.assertTrue(true);
			} else {
				System.out.println(rentabilidadeLiquida);
				Assert.assertTrue(false);
			}

			if (conta.getLancamentosDaConta().isEmpty()) {
				Assert.fail();
			} else {
				Assert.assertEquals(rentabilidadeLiquida, UtilHelper.arredonda(
						conta.getLancamentosDaConta().get(0).getValor(), 2));
				Assert.assertEquals(TipoDoLancamento.CREDITO, conta
						.getLancamentosDaConta().get(0).getTipoDoLancamento());
				Assert.assertEquals(
						TipoDaTransacao.INVESTIMENTO_EM_FUNDO.getValor() + " "
								+ TipoDoFundo.AGRESSIVO.getValor(), conta
								.getLancamentosDaConta().get(0).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testRealizaDOC() {
		TransacaoDeDocTed transacao = new TransacaoDeDocTed();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.INTERNET_BANKING);
		transacao.setTipoDaTransacao(TipoDaTransacao.DOC_TED_ENTRE_BANCOS);
		transacao.setAgencia(3);
		transacao.setConta(3);
		transacao.setValor(890.00);
		transacao.setBancoFavorecido(444);
		transacao.setAgenciaFavorecida(5555);
		transacao.setContaFavorecida(6666666);
		transacao.setCpfDoTitularDaContaFavorecida("77276469115");

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			Conta conta = ContaMediator.get().consultaConta(3, 3);
			Assert.assertEquals(1000.00, conta.getSaldo());

			if (conta.getLancamentosDaConta().isEmpty()) {
				Assert.fail();
			} else {
				Assert.assertEquals(890.00, conta.getLancamentosDaConta()
						.get(0).getValor());
				Assert.assertEquals(TipoDoLancamento.DEBITO, conta
						.getLancamentosDaConta().get(0).getTipoDoLancamento());
				Assert.assertEquals("DOC-C para a conta 444:5555:6666666",
						conta.getLancamentosDaConta().get(0).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testRealizaTED() {
		TransacaoDeDocTed transacao = new TransacaoDeDocTed();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.INTERNET_BANKING);
		transacao.setTipoDaTransacao(TipoDaTransacao.DOC_TED_ENTRE_BANCOS);
		transacao.setAgencia(4);
		transacao.setConta(4);
		transacao.setValor(3000.00);
		transacao.setBancoFavorecido(444);
		transacao.setAgenciaFavorecida(5555);
		transacao.setContaFavorecida(6666666);
		transacao.setCpfDoTitularDaContaFavorecida("77276469115");

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			Conta conta = ContaMediator.get().consultaConta(4, 4);
			Assert.assertEquals(5.50, conta.getSaldo());

			if (conta.getLancamentosDaConta().isEmpty()) {
				Assert.fail();
			} else {
				Assert.assertEquals(3000.00,
						conta.getLancamentosDaConta().get(0).getValor());
				Assert.assertEquals(TipoDoLancamento.DEBITO, conta
						.getLancamentosDaConta().get(0).getTipoDoLancamento());
				Assert.assertEquals("TED-D para a conta 444:5555:6666666",
						conta.getLancamentosDaConta().get(0).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testVerificaTarifacaoPorValorDaTransacaoParaContaEspecial() {
		Transacao transacao = criaTransacaoDeDeposito(3000.00);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			Conta conta = ContaMediator.get().consultaConta(3, 3);

			if (!conta.getLancamentosDaConta().isEmpty()) {
				Assert.assertEquals(3.00, conta.getLancamentosDaConta().get(1)
						.getValor());
				Assert.assertEquals(TipoDoLancamento.DEBITO, conta
						.getLancamentosDaConta().get(1).getTipoDoLancamento());
				Assert.assertEquals("Tarifação - Depósito em Conta", conta
						.getLancamentosDaConta().get(1).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testVerificaTarifacaoPorValorDaTransacaoParaContaBasica() {
		Transacao transacao = new Transacao();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.INTERNET_BANKING);
		transacao.setTipoDaTransacao(TipoDaTransacao.DEPOSITO_EM_CONTA);
		transacao.setAgencia(4);
		transacao.setConta(4);
		transacao.setValor(3000.00);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			Conta conta = ContaMediator.get().consultaConta(4, 4);

			if (!conta.getLancamentosDaConta().isEmpty()) {
				Assert.assertEquals(4.50, conta.getLancamentosDaConta().get(1)
						.getValor());
				Assert.assertEquals(TipoDoLancamento.DEBITO, conta
						.getLancamentosDaConta().get(1).getTipoDoLancamento());
				Assert.assertEquals("Tarifação - Depósito em Conta", conta
						.getLancamentosDaConta().get(1).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testVerificaTarifacaoPorCestaDeServicosParaContaEspecial() {
		TransacaoDeDocTed transacao = new TransacaoDeDocTed();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.INTERNET_BANKING);
		transacao.setTipoDaTransacao(TipoDaTransacao.DOC_TED_ENTRE_BANCOS);
		transacao.setAgencia(3);
		transacao.setConta(3);
		transacao.setValor(900.00);
		transacao.setBancoFavorecido(123);
		transacao.setAgenciaFavorecida(1234);
		transacao.setContaFavorecida(1234567);
		transacao.setCpfDoTitularDaContaFavorecida("02728594430");

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			/* Executa a segunda transação para testar cesta de serviços */
			tratadorDaTransacao.executa(transacao);

			Conta conta = ContaMediator.get().consultaConta(3, 3);

			if (!conta.getLancamentosDaConta().isEmpty()) {
				Assert.assertEquals(5.50, conta.getLancamentosDaConta().get(2)
						.getValor());
				Assert.assertEquals(TipoDoLancamento.DEBITO, conta
						.getLancamentosDaConta().get(2).getTipoDoLancamento());
				Assert.assertEquals("Tarifação - DOC/TED entre Bancos", conta
						.getLancamentosDaConta().get(2).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testVerificaTarifacaoPorCestaDeServicosParaContaBasica() {
		TransacaoDeDocTed transacao = new TransacaoDeDocTed();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.INTERNET_BANKING);
		transacao.setTipoDaTransacao(TipoDaTransacao.DOC_TED_ENTRE_BANCOS);
		transacao.setAgencia(4);
		transacao.setConta(4);
		transacao.setValor(1000.00);
		transacao.setBancoFavorecido(123);
		transacao.setAgenciaFavorecida(1234);
		transacao.setContaFavorecida(1234567);
		transacao.setCpfDoTitularDaContaFavorecida("02728594430");

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			/* Executa a segunda transa��o para testar cesta de servi�os */
			tratadorDaTransacao.executa(transacao);

			Conta conta = ContaMediator.get().consultaConta(4, 4);

			Assert.assertEquals(1003.00, conta.getSaldo());

			if (!conta.getLancamentosDaConta().isEmpty()) {
				Assert.assertEquals(7.00, conta.getLancamentosDaConta().get(2)
						.getValor());
				Assert.assertEquals(TipoDoLancamento.DEBITO, conta
						.getLancamentosDaConta().get(2).getTipoDoLancamento());
				Assert.assertEquals("Tarifação - DOC/TED entre Bancos", conta
						.getLancamentosDaConta().get(2).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testVerificaTarifacaoPorQuantidadeDeTransacoesParaContaBasica() {
		Transacao transacao = new Transacao();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.INTERNET_BANKING);
		transacao.setTipoDaTransacao(TipoDaTransacao.DEPOSITO_EM_CONTA);
		transacao.setAgencia(4);
		transacao.setConta(4);
		transacao.setValor(100.00);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			/* Executa mais 2 transa��es para testar quantidade */
			tratadorDaTransacao.executa(transacao);
			tratadorDaTransacao.executa(transacao);

			Conta conta = ContaMediator.get().consultaConta(4, 4);

			if (!conta.getLancamentosDaConta().isEmpty()) {
				Assert.assertEquals(2.00, conta.getLancamentosDaConta().get(3)
						.getValor());
				Assert.assertEquals(TipoDoLancamento.DEBITO, conta
						.getLancamentosDaConta().get(3).getTipoDoLancamento());
				Assert.assertEquals("Tarifação - Depósito em Conta", conta
						.getLancamentosDaConta().get(3).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testVerificaTarifacaoPorQuantidadeDeTransacoesParaContaEspecial() {
		Transacao transacao = criaTransacaoDeDeposito(100.00);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			/* Executa mais 4 transações para testar quantidade */
			tratadorDaTransacao.executa(transacao);
			tratadorDaTransacao.executa(transacao);
			tratadorDaTransacao.executa(transacao);
			tratadorDaTransacao.executa(transacao);

			Conta conta = ContaMediator.get().consultaConta(3, 3);

			if (!conta.getLancamentosDaConta().isEmpty()) {
				Assert.assertEquals(1.50, conta.getLancamentosDaConta().get(5)
						.getValor());
				Assert.assertEquals(TipoDoLancamento.DEBITO, conta
						.getLancamentosDaConta().get(5).getTipoDoLancamento());
				Assert.assertEquals("Tarifação - Depósito em Conta", conta
						.getLancamentosDaConta().get(5).getDescricao());
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testVerificaQueTransacaoFoiGravada() {
		Transacao transacao = criaTransacaoDeDeposito(99.99);

		AbstractTransacaoMediator tratadorDaTransacao = TransacaoFactory.get()
				.cria(transacao);

		Autorizacao autorizacao = tratadorDaTransacao.executa(transacao);
		Assert.assertEquals(EstadoDaAutorizacao.AUTORIZADA,
				autorizacao.getEstado());

		try {
			Conta conta = ContaMediator.get().consultaConta(3, 3);

			List<Transacao> transacoes = TransacaoMediator.get()
					.consultaTransacoesDaContaNoDia(conta);

			Assert.assertEquals(TipoDaTransacao.DEPOSITO_EM_CONTA.getChave(),
					transacoes.get(0).getTipoDaTransacao().getChave());
			Assert.assertEquals(CanalDeAtendimento.TERMINAL_CAIXA.getChave(),
					transacoes.get(0).getCanalDeAtendimento().getChave());
			Assert.assertEquals(99.99, transacoes.get(0).getValor());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	private Transacao criaTransacaoDeSaque(double valor) {
		Transacao transacao = new Transacao();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.TERMINAL_CAIXA);
		transacao.setTipoDaTransacao(TipoDaTransacao.SAQUE_EM_CONTA);
		transacao.setAgencia(3);
		transacao.setConta(3);
		transacao.setValor(valor);

		return transacao;
	}

	private Transacao criaTransacaoDeDeposito(double valor) {
		Transacao transacao = new Transacao();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.TERMINAL_CAIXA);
		transacao.setTipoDaTransacao(TipoDaTransacao.DEPOSITO_EM_CONTA);
		transacao.setAgencia(3);
		transacao.setConta(3);
		transacao.setValor(valor);

		return transacao;
	}

	private TransacaoDeTransferencia criaTransacaoDeTransferencia(double valor) {
		TransacaoDeTransferencia transacao = new TransacaoDeTransferencia();
		transacao.setCanalDeAtendimento(CanalDeAtendimento.INTERNET_BANKING);
		transacao
				.setTipoDaTransacao(TipoDaTransacao.TRANSFERENCIA_ENTRE_CONTAS);
		transacao.setAgencia(3);
		transacao.setConta(3);
		transacao.setValor(valor);
		transacao.setAgenciaFavorecida(4);
		transacao.setContaFavorecida(4);

		return transacao;
	}

	private TransacaoDeInvestimentoEmFundo criaTransacaoDeInvestimentoEmFundo(
			double valor, TipoDoFundo tipoDoFundo) {
		TransacaoDeInvestimentoEmFundo transacao = new TransacaoDeInvestimentoEmFundo();

		transacao.setCanalDeAtendimento(CanalDeAtendimento.INTERNET_BANKING);
		transacao.setTipoDaTransacao(TipoDaTransacao.INVESTIMENTO_EM_FUNDO);
		transacao.setAgencia(3);
		transacao.setConta(3);
		transacao.setValor(valor);
		transacao.setTipoDoFundo(tipoDoFundo);

		return transacao;
	}
}