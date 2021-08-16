package com.zupacademy.kleysson.proposta.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.zupacademy.kleysson.proposta.model.Proposta;
import com.zupacademy.kleysson.proposta.utils.enums.StatusProposta;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PropostaResponse {

    private StatusProposta status;
    private String nome;
    private String documento;
    private String email;
    private String endereco;
    private BigDecimal salario;

    public PropostaResponse(Proposta proposta) {
        this.nome = proposta.getNome();
        this.documento = proposta.getDocumento();
        this.email = proposta.getEmail();
        this.endereco = proposta.getEndereco();
        this.salario = proposta.getSalario();
        this.status = proposta.getStatusProposta();
    }

    public StatusProposta getStatus() {
        return status;
    }

    public String getNome() {
        return nome;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }
}
