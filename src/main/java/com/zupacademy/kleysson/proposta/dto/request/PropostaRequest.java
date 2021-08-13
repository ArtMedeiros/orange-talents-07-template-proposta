package com.zupacademy.kleysson.proposta.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.zupacademy.kleysson.proposta.config.validation.CPFouCNPJ;
import com.zupacademy.kleysson.proposta.model.Proposta;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Optional;

public class PropostaRequest {

    @NotBlank
    @CPFouCNPJ
    private String documento;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @NotNull
    @Positive
    private BigDecimal salario;

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public Proposta toModel() {
        return new Proposta(documento, email, nome, endereco, salario);
    }
}
