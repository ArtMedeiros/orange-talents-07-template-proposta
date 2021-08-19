package com.zupacademy.kleysson.proposta.model;

import com.zupacademy.kleysson.proposta.utils.enums.GatewayPagamento;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class CarteiraPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String idLegado;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private GatewayPagamento carteira;

    @ManyToOne
    private Cartao cartao;

    private CarteiraPagamento() {}

    public CarteiraPagamento(String idLegado, String email, GatewayPagamento carteira, Cartao cartao) {
        this.idLegado = idLegado;
        this.email = email;
        this.carteira = carteira;
        this.cartao = cartao;
    }
}
