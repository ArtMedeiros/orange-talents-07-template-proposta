package com.zupacademy.kleysson.proposta.controller;

import com.zupacademy.kleysson.proposta.config.exceptions.ApiErroException;
import com.zupacademy.kleysson.proposta.dto.request.PropostaRequest;
import com.zupacademy.kleysson.proposta.dto.response.PropostaResponse;
import com.zupacademy.kleysson.proposta.dto.response.SolicitarAnaliseResponse;
import com.zupacademy.kleysson.proposta.model.Proposta;
import com.zupacademy.kleysson.proposta.repository.PropostaRepository;
import com.zupacademy.kleysson.proposta.utils.services.AnaliseSolicitanteClient;
import com.zupacademy.kleysson.proposta.utils.services.Metricas;
import feign.FeignException;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private final Metricas metricas;
    private final PropostaRepository propostaRepository;
    private final AnaliseSolicitanteClient analiseSolicitanteClient;

    public PropostaController(MeterRegistry registry, Metricas metricas, PropostaRepository propostaRepository, AnaliseSolicitanteClient analiseSolicitanteClient) {
        this.metricas = metricas;
        this.propostaRepository = propostaRepository;
        this.analiseSolicitanteClient = analiseSolicitanteClient;
    }

    @PostMapping
    public ResponseEntity<?> cadastrarProposta(@RequestBody @Valid PropostaRequest request, UriComponentsBuilder uriBuilder) {
        Optional<Proposta> verificaProposta = propostaRepository.findByDocumento(request.getDocumento());
        if(verificaProposta.isPresent())
            return ResponseEntity.status(422).build();

        Proposta proposta = request.toModel();

        propostaRepository.save(proposta);
        metricas.incrementarPropostas();

        try {
            SolicitarAnaliseResponse analise = analiseSolicitanteClient.solicitarAnalise(proposta.analisarSolicitante());
            proposta.atualizarStatus(analise.getResultadoSolicitacao());
            propostaRepository.save(proposta);
        }
        catch (FeignException e) {
            throw new ApiErroException(HttpStatus.BAD_REQUEST, "Erro ao analisar solicitante", "analise_solicitante");
        }

        URI uri = uriBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropostaResponse> statusProposta(@PathVariable Long id) {
        Optional<Proposta> propostaBanco = propostaRepository.findById(id);

        if (propostaBanco.isEmpty())
            return ResponseEntity.notFound().build();

        metricas.incrementarStatusProposta();
        return ResponseEntity.ok(new PropostaResponse(propostaBanco.get()));
    }
}
