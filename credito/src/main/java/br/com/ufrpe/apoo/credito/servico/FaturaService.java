package br.com.ufrpe.apoo.credito.servico;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.ufrpe.apoo.credito.cliente.FinanceiroClient;
import br.com.ufrpe.apoo.credito.dominio.Cartao;
import br.com.ufrpe.apoo.credito.dto.FaturaDTO;
import br.com.ufrpe.apoo.credito.dto.LancamentoResponseDTO;
import br.com.ufrpe.apoo.credito.excecao.AcessoNegadoException;
import br.com.ufrpe.apoo.credito.excecao.RecursoNaoEncontradoException;
import br.com.ufrpe.apoo.credito.repositorio.CartaoRepository;

@Service
public class FaturaService {

    private final CartaoRepository cartaoRepository;
    private final FinanceiroClient financeiroClient;

    public FaturaService(CartaoRepository cartaoRepository, FinanceiroClient financeiroClient) {
        this.cartaoRepository = cartaoRepository;
        this.financeiroClient = financeiroClient;
    }

    public FaturaDTO gerarFaturaCartao(Long cartaoId, int mes, int ano, String idIdentidade) {
        Cartao cartao = cartaoRepository.findById(cartaoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cartão não encontrado"));

        if (!cartao.getIdIdentidade().equals(idIdentidade)) {
            throw new AcessoNegadoException("Acesso negado");
        }

        List<LancamentoResponseDTO> lancamentos = financeiroClient.listarLancamentosPorCartao(cartaoId, mes, ano);

        Double valorTotal = lancamentos.stream()
                .mapToDouble(LancamentoResponseDTO::valor)
                .sum();

        // Usa dia de vencimento do cartão
        int diaVencimento = cartao.getDiaVencimentoFatura();
        if (diaVencimento <= 0 || diaVencimento > 31) {
            diaVencimento = 10;
        }

        // Ajusta se o dia for inválido para o mês (ex: 31 em Fevereiro)
        LocalDate dataBase = LocalDate.of(ano, mes, 1);
        int ultimoDiaMes = dataBase.lengthOfMonth();
        if (diaVencimento > ultimoDiaMes) {
            diaVencimento = ultimoDiaMes;
        }

        LocalDate dataVencimento = LocalDate.of(ano, mes, diaVencimento);

        // Usa dia de fechamento do cartão
        int diaFechamento = cartao.getDiaFechamentoFatura();
        if (diaFechamento <= 0 || diaFechamento > 31) {
            diaFechamento = 1;
        }
        if (diaFechamento > ultimoDiaMes) {
            diaFechamento = ultimoDiaMes;
        }

        LocalDate dataFechamento = LocalDate.of(ano, mes, diaFechamento);

        return new FaturaDTO(cartao.getId(), cartao.getNome(), dataVencimento, valorTotal, dataFechamento, lancamentos);
    }

    public List<FaturaDTO> listarFaturas(int mes, int ano, String idIdentidade) {
        return cartaoRepository.findByIdIdentidade(idIdentidade).stream()
                .map(cartao -> gerarFaturaCartao(cartao.getId(), mes, ano, idIdentidade))
                .collect(Collectors.toList());
    }
}
