package com.zupacademy.kleysson.proposta.model;

import org.bouncycastle.util.Fingerprint;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Base64;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String fingerprint;

    @NotNull
    private Instant instante = Instant.now();

    @ManyToOne
    private Cartao cartao;

    private Biometria() {}

    public Biometria(String fingerprint, Cartao cartao) {
        this.fingerprint = fingerprint;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Biometria{" +
                "id=" + id +
                ", fingerprint='" + fingerprint + '\'' +
                ", instante=" + instante +
                ", cartao=" + cartao.getId() +
                '}';
    }
}
