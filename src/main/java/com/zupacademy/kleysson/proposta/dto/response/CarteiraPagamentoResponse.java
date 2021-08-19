package com.zupacademy.kleysson.proposta.dto.response;

import com.zupacademy.kleysson.proposta.utils.enums.StatusCarteira;

public class CarteiraPagamentoResponse {

    private StatusCarteira resultado;
    private String id;

    public StatusCarteira getResultado() {
        return resultado;
    }

    public String getId() {
        return id;
    }
}
