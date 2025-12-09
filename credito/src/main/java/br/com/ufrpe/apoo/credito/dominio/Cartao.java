package br.com.ufrpe.apoo.credito.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Double limite;
    private String idIdentidade;
    private int diaVencimentoFatura;
    private int diaFechamentoFatura;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getLimite() {
        return limite;
    }

    public void setLimite(Double limite) {
        this.limite = limite;
    }

    public String getIdIdentidade() {
        return idIdentidade;
    }

    public void setIdIdentidade(String idIdentidade) {
        this.idIdentidade = idIdentidade;
    }

    public int getDiaVencimentoFatura() {
        return diaVencimentoFatura;
    }

    public void setDiaVencimentoFatura(int diaVencimentoFatura) {
        this.diaVencimentoFatura = diaVencimentoFatura;
    }

    public int getDiaFechamentoFatura() {
        return diaFechamentoFatura;
    }

    public void setDiaFechamentoFatura(int diaFechamentoFatura) {
        this.diaFechamentoFatura = diaFechamentoFatura;
    }
}
