package com.zupacademy.kleysson.proposta.model;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;

@Entity
public class AvisoViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Future
    private LocalDate validoAte;

    @NotBlank
    private String destino;

    @NotNull
    private Instant instante = Instant.now();

    @NotBlank
    private String userAgent;

    @NotBlank
    private String ipCliente;

    @ManyToOne
    private Cartao cartao;

    private AvisoViagem() {}

    public AvisoViagem(LocalDate validoAte, String destino, String userAgent, String ipCliente, Cartao cartao) {
        this.validoAte = validoAte;
        this.destino = destino;
        this.userAgent = userAgent;
        this.ipCliente = ipCliente;
        this.cartao = cartao;
    }
}
