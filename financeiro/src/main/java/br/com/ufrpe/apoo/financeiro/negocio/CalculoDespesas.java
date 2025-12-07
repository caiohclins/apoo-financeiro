package br.com.ufrpe.apoo.financeiro.negocio;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.ufrpe.apoo.financeiro.dominio.Lancamento;

@Component("calculoDespesas")
public class CalculoDespesas implements IEstrategiaCalculo {
    @Override
    public Double calcularTotal(List<Lancamento> lancamentos) {
        return lancamentos.stream()
                .filter(l -> "DESPESA".equalsIgnoreCase(l.getTipo()))
                .map(Lancamento::getValor)
                .filter(valor -> valor != null)
                .mapToDouble(v -> -v.doubleValue())
                .sum();
    }
}
