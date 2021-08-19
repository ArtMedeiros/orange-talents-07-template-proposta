package com.zupacademy.kleysson.proposta.controller;

import com.zupacademy.kleysson.proposta.config.exceptions.ApiErroException;
import com.zupacademy.kleysson.proposta.dto.ErrorFormatDTO;
import com.zupacademy.kleysson.proposta.dto.request.BloquearCartaoRequest;
import com.zupacademy.kleysson.proposta.dto.response.BloqueioCartaoResponse;
import com.zupacademy.kleysson.proposta.model.BloqueioCartao;
import com.zupacademy.kleysson.proposta.model.Cartao;
import com.zupacademy.kleysson.proposta.repository.BloqueioCartaoRepository;
import com.zupacademy.kleysson.proposta.repository.CartaoRepository;
import com.zupacademy.kleysson.proposta.utils.enums.StatusBloqueio;
import com.zupacademy.kleysson.proposta.utils.services.ConsultarCartaoClient;
import com.zupacademy.kleysson.proposta.utils.services.Metricas;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class BloqueioCartaoController {

    private final BloqueioCartaoRepository bloqueioCartaoRepository;
    private final ConsultarCartaoClient consultarCartaoClient;
    private final CartaoRepository cartaoRepository;
    private final Metricas metricas;

    public BloqueioCartaoController(BloqueioCartaoRepository bloqueioCartaoRepository, ConsultarCartaoClient consultarCartaoClient, CartaoRepository cartaoRepository, Metricas metricas) {
        this.bloqueioCartaoRepository = bloqueioCartaoRepository;
        this.consultarCartaoClient = consultarCartaoClient;
        this.cartaoRepository = cartaoRepository;
        this.metricas = metricas;
    }

    @PostMapping("/cartoes/{id}/bloqueio")
    public ResponseEntity<?> bloquearCartao(@PathVariable String id, HttpServletRequest request) {
        Optional<Cartao> cartaoBanco = cartaoRepository.findById(id);

        if(cartaoBanco.isEmpty())
            return ResponseEntity.notFound().build();

        Optional<BloqueioCartao> bloqueioBanco = bloqueioCartaoRepository.findByCartaoId(id);
        if(bloqueioBanco.isPresent())
            return ResponseEntity.status(422).body(new ErrorFormatDTO("request", "Cartão já bloqueado"));

        try{
            metricas.incrementarSolicitacoesBloqueio();
            BloquearCartaoRequest bloqueioRequest = new BloquearCartaoRequest("Proposta");
            BloqueioCartaoResponse bloqueioResponse = consultarCartaoClient.bloquearCartao(id, bloqueioRequest);

            if(verificarBloqueio(bloqueioResponse)){
                BloqueioCartao bloqueio = bloqueioResponse.toModel(request, cartaoBanco.get());
                bloqueioCartaoRepository.save(bloqueio);
                metricas.incrementarBloqueiosRealizados();
            }
            else
                throw new ApiErroException(422, "Não foi possível realizar bloqueio", "request");
        }catch (FeignException e){
            return ResponseEntity.status(422).body(new ErrorFormatDTO("request", "Não foi possível realizar bloqueio"));
        }

        return ResponseEntity.ok().build();
    }

    private boolean verificarBloqueio(BloqueioCartaoResponse response) {
        return response.getResultado().equals(StatusBloqueio.BLOQUEADO);
    }
}
