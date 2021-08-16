package com.zupacademy.kleysson.proposta.model;

import com.zupacademy.kleysson.proposta.dto.request.SolicitarAnaliseRequest;
import com.zupacademy.kleysson.proposta.utils.enums.StatusAnalise;
import com.zupacademy.kleysson.proposta.utils.enums.StatusProposta;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String documento;

    @NotBlank
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusProposta statusProposta;

    @NotNull
    private BigDecimal salario;

    private String numeroCartao;

    private Proposta() {}

    @Valid
    public Proposta(@NotBlank String documento, @NotBlank String email, @NotBlank String nome, @NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
        Assert.hasLength(documento, "O número do documento deve ser preenchido");
        Assert.hasLength(email, "O e-mail deve ser preenchido");
        Assert.hasLength(nome, "O nome deve ser preenchido");
        Assert.hasLength(endereco, "O endereço deve ser preenchido");
        Assert.notNull(salario, "O valor do salário deve ser preenchido");

        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.statusProposta = StatusProposta.NAO_ELEGIVEL;
    }

    public Long getId() {
        return id;
    }

    public StatusProposta getStatusProposta() {
        return statusProposta;
    }

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

    public void atualizarStatus(StatusAnalise status) {
        this.statusProposta = status.toProposta();
    }

    public SolicitarAnaliseRequest analisarSolicitante(){
        return new SolicitarAnaliseRequest(documento, nome, id.toString());
    }

    public void associarCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }
}
