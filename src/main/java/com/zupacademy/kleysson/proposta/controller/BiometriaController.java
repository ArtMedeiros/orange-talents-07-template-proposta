package com.zupacademy.kleysson.proposta.controller;

import com.zupacademy.kleysson.proposta.dto.request.BiometriaRequest;
import com.zupacademy.kleysson.proposta.model.Biometria;
import com.zupacademy.kleysson.proposta.repository.BiometriaRepository;
import com.zupacademy.kleysson.proposta.repository.CartaoRepository;
import com.zupacademy.kleysson.proposta.utils.services.Metricas;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("/biometria")
public class BiometriaController {

    private final BiometriaRepository biometriaRepository;
    private final CartaoRepository cartaoRepository;
    private final Metricas metricas;

    public BiometriaController(BiometriaRepository biometriaRepository, CartaoRepository cartaoRepository, Metricas metricas) {
        this.biometriaRepository = biometriaRepository;
        this.cartaoRepository = cartaoRepository;
        this.metricas = metricas;
    }

    @PostMapping("/{cartaoId}")
    public ResponseEntity<?> cadastrarBiometria(@RequestBody @Valid BiometriaRequest request, @PathVariable String cartaoId, UriComponentsBuilder uriBuilder) {
        if(!checarIntegridade(request.getFingerprint()))
            return ResponseEntity.badRequest().build();

        Optional<Biometria> biometriaObj = request.toModel(cartaoRepository, cartaoId);

        if(biometriaObj.isEmpty())
            return ResponseEntity.notFound().build();

        Biometria biometria = biometriaObj.get();
        biometriaRepository.save(biometria);
        metricas.incrementarBiometrias();

        URI uri = uriBuilder.path("/biometria/{id}").buildAndExpand(biometria.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    private boolean checarIntegridade(String valor) {
        try {
            Base64.getDecoder().decode(valor);
            return true;
        }catch (IllegalArgumentException e) {
            return false;
        }
    }
}
