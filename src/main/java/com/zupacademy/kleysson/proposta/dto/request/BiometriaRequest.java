package com.zupacademy.kleysson.proposta.dto.request;

import com.zupacademy.kleysson.proposta.model.Biometria;
import com.zupacademy.kleysson.proposta.model.Cartao;
import com.zupacademy.kleysson.proposta.repository.CartaoRepository;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public class BiometriaRequest {

    @NotBlank
    private String fingerprint;

    public String getFingerprint() {
        return fingerprint;
    }

    public Optional<Biometria> toModel(CartaoRepository cartaoRepository, String cartaoId) {
        Optional<Cartao> cartaoBanco = cartaoRepository.findById(cartaoId);

        if(cartaoBanco.isPresent()){
            Cartao cartao = cartaoBanco.get();
            Biometria biometria = new Biometria(this.fingerprint, cartao);

            return Optional.of(biometria);
        }

        return Optional.empty();
    }
}
