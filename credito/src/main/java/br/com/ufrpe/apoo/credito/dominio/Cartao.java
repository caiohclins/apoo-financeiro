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
    private String numero;
    private Double limite;
    private String usuarioId;
    private int diaVencimentoFatura;
    private int melhorDiaCompra;

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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Double getLimite() {
        return limite;
    }

    public void setLimite(Double limite) {
        this.limite = limite;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getDiaVencimentoFatura() {
        return diaVencimentoFatura;
    }

    public void setDiaVencimentoFatura(int diaVencimentoFatura) {
        this.diaVencimentoFatura = diaVencimentoFatura;
    }

    public int getMelhorDiaCompra() {
        return melhorDiaCompra;
    }

    public void setMelhorDiaCompra(int melhorDiaCompra) {
        this.melhorDiaCompra = melhorDiaCompra;
    }
}
