package com.zupacademy.kleysson.proposta.config.validation;

import com.zupacademy.kleysson.proposta.config.exceptions.ApiErroException;
import com.zupacademy.kleysson.proposta.dto.request.PropostaRequest;
import com.zupacademy.kleysson.proposta.model.Proposta;
import com.zupacademy.kleysson.proposta.repository.PropostaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class VerificaSolicitanteProposta implements Validator {

    private final PropostaRepository propostaRepository;

    public VerificaSolicitanteProposta(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PropostaRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if(errors.hasErrors())
            return;

        PropostaRequest request = (PropostaRequest) o;

        Optional<Proposta> propostaBanco = propostaRepository.findByDocumento(request.getDocumento());

        if(propostaBanco.isPresent())
            throw new ApiErroException(HttpStatus.UNPROCESSABLE_ENTITY, "Proposta já cadastrada para esse solicitante", "documento");
    }
}