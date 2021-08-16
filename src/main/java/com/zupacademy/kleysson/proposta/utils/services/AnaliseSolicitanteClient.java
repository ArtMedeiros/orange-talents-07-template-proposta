package com.zupacademy.kleysson.proposta.utils.services;

import com.zupacademy.kleysson.proposta.dto.request.SolicitarAnaliseRequest;
import com.zupacademy.kleysson.proposta.dto.response.SolicitarAnaliseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="analiseSolicitante", url = "http://localhost:9999")
public interface AnaliseSolicitanteClient {

    @PostMapping("api/solicitacao")
    SolicitarAnaliseResponse solicitarAnalise(@RequestBody SolicitarAnaliseRequest request);

}
