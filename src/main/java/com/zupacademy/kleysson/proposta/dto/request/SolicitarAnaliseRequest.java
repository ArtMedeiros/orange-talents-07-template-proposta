package com.zupacademy.kleysson.proposta.dto.request;

public class SolicitarAnaliseRequest {

    private String documento;
    private String nome;
    private String idProposta;

    public SolicitarAnaliseRequest() {}

    public SolicitarAnaliseRequest(String documento, String nome, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
