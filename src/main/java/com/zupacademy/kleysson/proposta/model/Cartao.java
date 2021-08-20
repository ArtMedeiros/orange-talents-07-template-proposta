package com.zupacademy.kleysson.proposta.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cartao {

    @Id
    @NotBlank
    private String id;

    @NotNull
    private LocalDateTime emitidoEm;

    @NotBlank
    private String titular;

    @NotNull
    private BigDecimal limite;

    @OneToOne
    private Proposta proposta;

    @ManyToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
    private Set<Biometria> biometrias = new HashSet<>();

    @ManyToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
    private Set<AvisoViagem> viagens = new HashSet<>();

    @ManyToMany(mappedBy = "cartao", cascade = CascadeType.MERGE)
    private Set<CarteiraPagamento> carteiras = new HashSet<>();

    @OneToOne(mappedBy = "cartao", cascade = CascadeType.MERGE)
    private BloqueioCartao bloqueio;

    private Cartao() {}

    public Cartao(String id, LocalDateTime emitidoEm, String titular, BigDecimal limite, Proposta proposta) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.limite = limite;
        this.proposta = proposta;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public Proposta getProposta() {
        return proposta;
    }
}
