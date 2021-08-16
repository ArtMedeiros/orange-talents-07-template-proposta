package com.zupacademy.kleysson.proposta.utils.services;

import com.zupacademy.kleysson.proposta.config.exceptions.ApiErroException;
import com.zupacademy.kleysson.proposta.dto.request.SolicitarAnaliseRequest;
import com.zupacademy.kleysson.proposta.dto.response.RespostaAnaliseResponse;
import com.zupacademy.kleysson.proposta.model.Proposta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AnaliseSolicitante {

    public RespostaAnaliseResponse enviar(Proposta proposta){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:9999/api/solicitacao";

        SolicitarAnaliseRequest request = new SolicitarAnaliseRequest(proposta.getDocumento(), proposta.getNome(), proposta.getId().toString());

        try{
            ResponseEntity<RespostaAnaliseResponse> response = restTemplate.postForEntity(url, request, RespostaAnaliseResponse.class);
            return response.getBody();
        }
        catch (HttpClientErrorException e){
            throw new ApiErroException(HttpStatus.BAD_REQUEST, "Problemas na análise da proposta", "request");
        }
    }
}
