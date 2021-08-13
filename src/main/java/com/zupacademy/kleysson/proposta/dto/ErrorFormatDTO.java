package com.zupacademy.kleysson.proposta.dto;

public class ErrorFormatDTO {
    private String campo;
    private String erro;

    public ErrorFormatDTO(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return erro;
    }
}
