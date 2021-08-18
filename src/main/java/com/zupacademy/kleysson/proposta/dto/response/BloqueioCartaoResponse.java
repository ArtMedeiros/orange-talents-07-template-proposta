package com.zupacademy.kleysson.proposta.dto.response;

import com.zupacademy.kleysson.proposta.model.BloqueioCartao;
import com.zupacademy.kleysson.proposta.model.Cartao;
import com.zupacademy.kleysson.proposta.repository.CartaoRepository;
import com.zupacademy.kleysson.proposta.utils.enums.StatusBloqueio;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

public class BloqueioCartaoResponse {

    @NotBlank
    private StatusBloqueio resultado;

    public StatusBloqueio getResultado() {
        return resultado;
    }

    public BloqueioCartao toModel(CartaoRepository cartaoRepository, HttpServletRequest request, Cartao cartao) {
        String ipCliente = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        return new BloqueioCartao(cartao, ipCliente, userAgent);
    }
}
