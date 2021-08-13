package com.zupacademy.kleysson.proposta.controller;

import com.zupacademy.kleysson.proposta.config.validation.VerificaSolicitanteProposta;
import com.zupacademy.kleysson.proposta.dto.request.PropostaRequest;
import com.zupacademy.kleysson.proposta.model.Proposta;
import com.zupacademy.kleysson.proposta.repository.PropostaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private final PropostaRepository propostaRepository;
    private final VerificaSolicitanteProposta verificaSolicitanteProposta;

    public PropostaController(PropostaRepository propostaRepository, VerificaSolicitanteProposta verificaSolicitanteProposta) {
        this.propostaRepository = propostaRepository;
        this.verificaSolicitanteProposta = verificaSolicitanteProposta;
    }

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(verificaSolicitanteProposta);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid PropostaRequest request, UriComponentsBuilder uriBuilder) {
        Proposta proposta = request.toModel();

        propostaRepository.save(proposta);

        URI uri = uriBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
