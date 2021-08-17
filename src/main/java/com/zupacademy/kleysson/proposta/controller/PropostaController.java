package com.zupacademy.kleysson.proposta.controller;

import com.zupacademy.kleysson.proposta.config.exceptions.ApiErroException;
import com.zupacademy.kleysson.proposta.dto.request.PropostaRequest;
import com.zupacademy.kleysson.proposta.dto.response.PropostaResponse;
import com.zupacademy.kleysson.proposta.dto.response.SolicitarAnaliseResponse;
import com.zupacademy.kleysson.proposta.model.Proposta;
import com.zupacademy.kleysson.proposta.repository.PropostaRepository;
import com.zupacademy.kleysson.proposta.utils.services.AnaliseSolicitanteClient;
import feign.FeignException;
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

    private final PropostaRepository propostaRepository;
    private final AnaliseSolicitanteClient analiseSolicitanteClient;

    public PropostaController(PropostaRepository propostaRepository, AnaliseSolicitanteClient analiseSolicitanteClient) {
        this.propostaRepository = propostaRepository;
        this.analiseSolicitanteClient = analiseSolicitanteClient;
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid PropostaRequest request, UriComponentsBuilder uriBuilder) {
        Optional<Proposta> verificaProposta = propostaRepository.findByDocumento(request.getDocumento());
        if(verificaProposta.isPresent())
            return ResponseEntity.status(422).build();

        Proposta proposta = request.toModel();

        propostaRepository.save(proposta);

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

        return ResponseEntity.ok(new PropostaResponse(propostaBanco.get()));
    }
}
