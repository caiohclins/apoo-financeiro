package br.com.ufrpe.apoo.financeiro.servico;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.ufrpe.apoo.financeiro.cliente.CreditoClient;
import br.com.ufrpe.apoo.financeiro.dominio.Lancamento;
import br.com.ufrpe.apoo.financeiro.dto.CartaoResponseDTO;
import br.com.ufrpe.apoo.financeiro.dto.DashboardDTO;
import br.com.ufrpe.apoo.financeiro.negocio.IEstrategiaCalculo;
import br.com.ufrpe.apoo.financeiro.repositorio.LancamentoRepository;

@Service
public class DashboardService {

    private final LancamentoRepository lancamentoRepository;
    private final IEstrategiaCalculo calculoReceitas;
    private final IEstrategiaCalculo calculoDespesas;

    private final CreditoClient creditoClient;

    public DashboardService(LancamentoRepository lancamentoRepository,
            @Qualifier("calculoReceitas") IEstrategiaCalculo calculoReceitas,
            @Qualifier("calculoDespesas") IEstrategiaCalculo calculoDespesas,
            CreditoClient creditoClient) {
        this.lancamentoRepository = lancamentoRepository;
        this.calculoReceitas = calculoReceitas;
        this.calculoDespesas = calculoDespesas;
        this.creditoClient = creditoClient;
    }

    public DashboardDTO gerarDashboard(String idIdentidade, int mes, int ano) {
        LocalDate inicio = LocalDate.of(ano, mes, 1);
        LocalDate fim = inicio.withDayOfMonth(inicio.lengthOfMonth());

        // Busca apenas lançamentos do usuário no período
        List<Lancamento> lancamentos = lancamentoRepository.findByIdIdentidade(idIdentidade)
                .stream()
                .filter(l -> !l.getDataLancamento().isBefore(inicio) && !l.getDataLancamento().isAfter(fim))
                .collect(Collectors.toList());

        // Aplica as estratégias de cálculo
        Double totalReceitas = calculoReceitas.calcularTotal(lancamentos);
        Double totalDespesas = calculoDespesas.calcularTotal(lancamentos);

        // Distribuição de Despesas por Tag
        Map<String, Double> despesasPorTag = lancamentos.stream()
                .filter(l -> "DESPESA".equalsIgnoreCase(l.getTipo()) && l.getTags() != null)
                .flatMap(l -> l.getTags().stream()
                        .map(tag -> new AbstractMap.SimpleEntry<>(tag.getNome(), l.getValor())))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.summingDouble(Map.Entry::getValue)));

        // Distribuição de Despesas por Cartão (ID -> Valor)
        Map<Long, Double> despesasPorCartaoId = lancamentos.stream()
                .filter(l -> "DESPESA".equalsIgnoreCase(l.getTipo()) && l.getCartaoId() != null)
                .collect(Collectors.groupingBy(
                        Lancamento::getCartaoId,
                        Collectors.summingDouble(Lancamento::getValor)));

        // Mapeia IDs para Nomes
        Map<String, Double> despesasPorNomeCartao = new HashMap<>();
        if (!despesasPorCartaoId.isEmpty()) {
            try {
                // Busca nomes dos cartões via Feign
                java.util.List<CartaoResponseDTO> cartoes = creditoClient.listarCartoes();
                java.util.Map<Long, String> mapaCartoes = cartoes.stream()
                        .collect(java.util.stream.Collectors.toMap(
                                CartaoResponseDTO::id,
                                CartaoResponseDTO::nome));

                despesasPorCartaoId.forEach((id, valor) -> {
                    String nome = mapaCartoes.getOrDefault(id, "Cartão Desconhecido (" + id + ")");
                    despesasPorNomeCartao.put(nome, valor);
                });
            } catch (Exception e) {
                // Fallback se serviço de crédito estiver fora
                despesasPorCartaoId.forEach((id, valor) -> despesasPorNomeCartao.put("ID: " + id, valor));
            }
        }

        return new DashboardDTO(totalReceitas, totalDespesas, despesasPorTag, despesasPorNomeCartao);
    }
}
