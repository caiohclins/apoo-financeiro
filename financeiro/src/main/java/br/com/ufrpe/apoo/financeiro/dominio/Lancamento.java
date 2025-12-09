package br.com.ufrpe.apoo.financeiro.dominio;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Min;

@Entity
public class Lancamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    @Min(value = 0, message = "Valor deve ser maior ou igual a 0")
    private Double valor;
    private LocalDate dataLancamento;
    private int quantidadeParcelas;
    private boolean recorrente;
    private String tipo;
    private String idIdentidade;
    private Long cartaoId;

    @ManyToMany
    private List<Tag> tags;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataPagamento) {
        this.dataLancamento = dataPagamento;
    }

    public int getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(int numeroParcelas) {
        this.quantidadeParcelas = numeroParcelas;
    }

    public boolean isRecorrente() {
        return recorrente;
    }

    public void setRecorrente(boolean recorrente) {
        this.recorrente = recorrente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdIdentidade() {
        return idIdentidade;
    }

    public void setIdIdentidade(String idIdentidade) {
        this.idIdentidade = idIdentidade;
    }

    public Long getCartaoId() {
        return cartaoId;
    }

    public void setCartaoId(Long cartaoId) {
        this.cartaoId = cartaoId;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

}
