package br.com.ufrpe.apoo.financeiro.negocio;

import java.util.List;

import br.com.ufrpe.apoo.financeiro.dominio.Lancamento;

public interface IEstrategiaCalculo {
    Double calcularTotal(List<Lancamento> lancamentos);
}
