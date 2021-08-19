package com.zupacademy.kleysson.proposta.controller;

import com.zupacademy.kleysson.proposta.config.exceptions.ApiErroException;
import com.zupacademy.kleysson.proposta.dto.ErrorFormatDTO;
import com.zupacademy.kleysson.proposta.dto.request.AvisoViagemRequest;
import com.zupacademy.kleysson.proposta.dto.response.AvisoViagemResponse;
import com.zupacademy.kleysson.proposta.model.AvisoViagem;
import com.zupacademy.kleysson.proposta.model.Cartao;
import com.zupacademy.kleysson.proposta.repository.AvisoViagemRepository;
import com.zupacademy.kleysson.proposta.repository.CartaoRepository;
import com.zupacademy.kleysson.proposta.utils.enums.StatusAvisoViagem;
import com.zupacademy.kleysson.proposta.utils.services.ConsultarCartaoClient;
import com.zupacademy.kleysson.proposta.utils.services.Metricas;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class ViagemController {

    private final Metricas metricas;
    private final CartaoRepository cartaoRepository;
    private final ConsultarCartaoClient consultarCartaoClient;
    private final AvisoViagemRepository avisoViagemRepository;

    public ViagemController(Metricas metricas, CartaoRepository cartaoRepository, ConsultarCartaoClient consultarCartaoClient, AvisoViagemRepository avisoViagemRepository) {
        this.metricas = metricas;
        this.cartaoRepository = cartaoRepository;
        this.consultarCartaoClient = consultarCartaoClient;
        this.avisoViagemRepository = avisoViagemRepository;
    }


    @PostMapping("/cartoes/{id}/viagem")
    public ResponseEntity<?> notificarViagem(@PathVariable String id, @RequestBody @Valid AvisoViagemRequest avisoViagemRequest, HttpServletRequest request) {
        Optional<Cartao> cartaoBanco = cartaoRepository.findById(id);
        if(cartaoBanco.isEmpty())
            return ResponseEntity.notFound().build();

        try{
            AvisoViagemResponse avisoViagemResponse = consultarCartaoClient.notificarViagem(id, avisoViagemRequest);
            if(verificarAvisoViagem(avisoViagemResponse)){
                AvisoViagem avisoViagem = avisoViagemRequest.toModel(cartaoBanco.get(), request);
                avisoViagemRepository.save(avisoViagem);
                metricas.incrementarAvisosViagem();
            }
            else
                throw new ApiErroException(422, "Não foi possível realizar a notificação de viagem", "request");
        }catch (FeignException e){
            return ResponseEntity.status(422).body(new ErrorFormatDTO("request", "Não foi possível realizar a notificação de viagem"));
        }

        return ResponseEntity.ok().build();
    }

    private boolean verificarAvisoViagem(AvisoViagemResponse response){
        return response.getResultado().equals(StatusAvisoViagem.CRIADO);
    }
}
