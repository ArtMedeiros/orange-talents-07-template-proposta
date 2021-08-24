package com.zupacademy.kleysson.proposta.dto.response;

import com.zupacademy.kleysson.proposta.model.BloqueioCartao;
import com.zupacademy.kleysson.proposta.model.Cartao;
import com.zupacademy.kleysson.proposta.utils.enums.StatusBloqueio;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class BloqueioCartaoResponse {

    @NotBlank
    private StatusBloqueio resultado;

    public StatusBloqueio getResultado() {
        return resultado;
    }

    public BloqueioCartao toModel(String userAgent, List<String> listaIps, Cartao cartao) {
        String ipCliente = listaIps.get(0);
        return new BloqueioCartao(cartao, ipCliente, userAgent);
    }
}
