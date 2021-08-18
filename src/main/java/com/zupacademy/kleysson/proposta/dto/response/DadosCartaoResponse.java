package com.zupacademy.kleysson.proposta.dto.response;

import com.zupacademy.kleysson.proposta.model.Cartao;
import com.zupacademy.kleysson.proposta.model.Proposta;
import com.zupacademy.kleysson.proposta.repository.PropostaRepository;

import javax.swing.text.html.Option;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public class DadosCartaoResponse {

    @NotBlank
    private String id;

    @NotBlank
    private String titular;

    @NotBlank
    private Long idProposta;

    @NotNull
    private LocalDateTime emitidoEm;

    @NotNull
    private BigDecimal limite;

    public DadosCartaoResponse(Cartao cartao) {
        this.id = cartao.getId();
        this.titular = cartao.getTitular();
        this.idProposta = cartao.getProposta().getId();
        this.emitidoEm = cartao.getEmitidoEm();
        this.limite = cartao.getLimite();
    }

    @Deprecated
    public DadosCartaoResponse() {}

    public Cartao toModel(PropostaRepository propostaRepository){
        Optional<Proposta> propostaBanco = propostaRepository.findById(idProposta);

        return new Cartao(id, emitidoEm, titular, limite, propostaBanco.get());
    }

    public String getId() {
        return id;
    }

    public String getTitular() {
        return titular;
    }

    public Long getIdProposta() {
        return idProposta;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public BigDecimal getLimite() {
        return limite;
    }
}
