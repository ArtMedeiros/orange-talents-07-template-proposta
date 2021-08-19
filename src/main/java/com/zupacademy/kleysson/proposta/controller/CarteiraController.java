package com.zupacademy.kleysson.proposta.controller;

import com.zupacademy.kleysson.proposta.dto.ErrorFormatDTO;
import com.zupacademy.kleysson.proposta.dto.request.CarteiraPagamentoRequest;
import com.zupacademy.kleysson.proposta.dto.response.CarteiraPagamentoResponse;
import com.zupacademy.kleysson.proposta.model.Cartao;
import com.zupacademy.kleysson.proposta.model.CarteiraPagamento;
import com.zupacademy.kleysson.proposta.repository.CartaoRepository;
import com.zupacademy.kleysson.proposta.repository.CarteiraPagamentoRepository;
import com.zupacademy.kleysson.proposta.utils.enums.StatusCarteira;
import com.zupacademy.kleysson.proposta.utils.services.ConsultarCartaoClient;
import com.zupacademy.kleysson.proposta.utils.services.Metricas;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/carteiras")
public class CarteiraController {

    private final Metricas metricas;
    private final CartaoRepository cartaoRepository;
    private final ConsultarCartaoClient consultarCartaoClient;
    private final CarteiraPagamentoRepository carteiraPagamentoRepository;

    public CarteiraController(Metricas metricas, CartaoRepository cartaoRepository, CarteiraPagamentoRepository carteiraPagamentoRepository, ConsultarCartaoClient consultarCartaoClient) {
        this.metricas = metricas;
        this.cartaoRepository = cartaoRepository;
        this.carteiraPagamentoRepository = carteiraPagamentoRepository;
        this.consultarCartaoClient = consultarCartaoClient;
    }

    @PostMapping("/cartoes/{id}")
    public ResponseEntity<?> cadastrarCarteira(@PathVariable String id, @RequestBody @Valid CarteiraPagamentoRequest carteiraPagamentoRequest, UriComponentsBuilder uriBuilder){
        Optional<Cartao> cartaoBanco = cartaoRepository.findById(id);
        if(cartaoBanco.isEmpty())
            return ResponseEntity.notFound().build();

        Optional<CarteiraPagamento> carteiraBanco = carteiraPagamentoRepository.findByCarteiraAndCartaoId(carteiraPagamentoRequest.getCarteira(), cartaoBanco.get().getId());
        if(carteiraBanco.isPresent())
            return ResponseEntity.status(422).body(new ErrorFormatDTO("request", "Cartão já associado"));

        try {
            CarteiraPagamentoResponse carteiraPagamentoResponse = consultarCartaoClient.associarCarteira(id, carteiraPagamentoRequest);
            if(verificarAssociacaoCarteira(carteiraPagamentoResponse)){
                CarteiraPagamento carteiraPagamento = carteiraPagamentoRequest.toModel(carteiraPagamentoResponse.getId(), cartaoBanco.get());
                carteiraPagamentoRepository.save(carteiraPagamento);
                metricas.incrementarCarteirasAssosciadas();
            }
        }catch(FeignException e){
            return ResponseEntity.status(422).body(new ErrorFormatDTO("request", "Não foi possível associar o cartão à carteira "+carteiraPagamentoRequest.getCarteira()));
        }

        URI uri = uriBuilder.path("/carteiras/{id}").buildAndExpand(1L).toUri();
        return ResponseEntity.created(uri).build();
    }

    public boolean verificarAssociacaoCarteira(CarteiraPagamentoResponse response){
        return response.getResultado().equals(StatusCarteira.ASSOCIADA);
    }
}
