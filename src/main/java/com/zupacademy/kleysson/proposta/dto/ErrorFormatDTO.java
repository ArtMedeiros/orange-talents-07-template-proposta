package com.zupacademy.kleysson.proposta.dto;

public class ErrorFormatDTO {
    private String causa;
    private String erro;

    public ErrorFormatDTO(String causa, String erro) {
        this.causa = causa;
        this.erro = erro;
    }

    public String getCausa() {
        return causa;
    }

    public String getErro() {
        return erro;
    }
}
