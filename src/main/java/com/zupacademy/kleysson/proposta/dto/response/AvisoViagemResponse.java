package com.zupacademy.kleysson.proposta.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.zupacademy.kleysson.proposta.model.AvisoViagem;
import com.zupacademy.kleysson.proposta.utils.enums.StatusAvisoViagem;

import java.time.LocalDate;

public class AvisoViagemResponse {

    private StatusAvisoViagem resultado;

    public StatusAvisoViagem getResultado() {
        return resultado;
    }
}
