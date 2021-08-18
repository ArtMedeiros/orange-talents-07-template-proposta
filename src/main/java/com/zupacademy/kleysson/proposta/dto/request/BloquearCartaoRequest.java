package com.zupacademy.kleysson.proposta.dto.request;

import javax.validation.constraints.NotBlank;

public class BloquearCartaoRequest {

    @NotBlank
    private String sistemaResponsavel;

    public BloquearCartaoRequest(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }
}
