package com.zupacademy.kleysson.proposta.model;

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
        this.cartao = cartao;
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
    }
}
