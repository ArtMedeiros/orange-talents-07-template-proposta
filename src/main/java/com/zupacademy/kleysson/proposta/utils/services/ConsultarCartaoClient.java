package com.zupacademy.kleysson.proposta.utils.services;

import com.zupacademy.kleysson.proposta.dto.request.BloquearCartaoRequest;
import com.zupacademy.kleysson.proposta.dto.response.BloqueioCartaoResponse;
import com.zupacademy.kleysson.proposta.dto.response.DadosCartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@FeignClient(name="cartoes", url = "${cartao.host}")
public interface ConsultarCartaoClient {

    @GetMapping("/api/cartoes")
    DadosCartaoResponse consultarCartaoByProposta(@RequestParam String idProposta);

    @PostMapping("/api/cartoes/{id}/bloqueios")
    BloqueioCartaoResponse bloquearCartao(@PathVariable String id, @RequestBody @Valid BloquearCartaoRequest request);
}
