package com.zupacademy.kleysson.proposta.dto.request;


import com.zupacademy.kleysson.proposta.model.AvisoViagem;
import com.zupacademy.kleysson.proposta.model.Cartao;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemRequest {

    @NotBlank
    private String destino;

    @NotNull
    @Future
    private LocalDate validoAte;

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public AvisoViagem toModel(Cartao cartao, HttpServletRequest request) {
        String ipCliente = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        return new AvisoViagem(validoAte, destino, userAgent, ipCliente, cartao);
    }
}
