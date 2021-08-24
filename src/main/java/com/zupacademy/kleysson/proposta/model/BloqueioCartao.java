package com.zupacademy.kleysson.proposta.model;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
public class BloqueioCartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Cartao cartao;

    @NotBlank
    private String ipCliente;

    @NotBlank
    private String userAgent;

    @NotNull
    private Instant instante = Instant.now();

    private BloqueioCartao() {}

    public BloqueioCartao(Cartao cartao, String ipCliente, String userAgent) {
        Assert.notNull(cartao, "O cartão deve ser informado");
        Assert.hasLength(ipCliente, "O IP do cliente deve ser informado");
        Assert.hasLength(userAgent, "O User Agent deve ser informado");

        this.cartao = cartao;
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
    }
}
