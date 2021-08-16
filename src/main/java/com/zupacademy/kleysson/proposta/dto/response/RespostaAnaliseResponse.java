package com.zupacademy.kleysson.proposta.dto.response;

import com.zupacademy.kleysson.proposta.utils.enums.StatusAnalise;

public class RespostaAnaliseResponse {

    private String documento;
    private String nome;
    private StatusAnalise resultadoSolicitacao;
    private String idProposta;

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public StatusAnalise getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
