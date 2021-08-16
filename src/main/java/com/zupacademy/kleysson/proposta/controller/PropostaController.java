package com.zupacademy.kleysson.proposta.controller;

import com.zupacademy.kleysson.proposta.dto.request.PropostaRequest;
import com.zupacademy.kleysson.proposta.dto.response.RespostaAnaliseResponse;
import com.zupacademy.kleysson.proposta.model.Proposta;
import com.zupacademy.kleysson.proposta.repository.PropostaRepository;
import com.zupacademy.kleysson.proposta.utils.services.AnaliseSolicitante;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private final PropostaRepository propostaRepository;
    private final AnaliseSolicitante analiseSolicitante;

    public PropostaController(PropostaRepository propostaRepository, AnaliseSolicitante analiseSolicitante) {
        this.propostaRepository = propostaRepository;
        this.analiseSolicitante = analiseSolicitante;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid PropostaRequest request, UriComponentsBuilder uriBuilder) {
        Optional<Proposta> verificaProposta = propostaRepository.findByDocumento(request.getDocumento());
        if(verificaProposta.isPresent())
            return ResponseEntity.status(422).build();

        Proposta proposta = request.toModel();

        propostaRepository.save(proposta);

        RespostaAnaliseResponse analise = analiseSolicitante.enviar(proposta);
        proposta.atualizarStatus(analise.getResultadoSolicitacao());

        URI uri = uriBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
