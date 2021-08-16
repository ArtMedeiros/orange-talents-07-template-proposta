package com.zupacademy.kleysson.proposta.utils.services;

import com.zupacademy.kleysson.proposta.dto.response.DadosCartaoResponse;
import com.zupacademy.kleysson.proposta.model.Proposta;
import com.zupacademy.kleysson.proposta.repository.PropostaRepository;
import com.zupacademy.kleysson.proposta.utils.enums.StatusProposta;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AssociarCartao {

    private final ConsultarCartaoClient consultarCartaoClient;
    private final PropostaRepository propostaRepository;
    private final Logger logger;

    public AssociarCartao(ConsultarCartaoClient consultarCartaoClient, PropostaRepository propostaRepository) {
        this.consultarCartaoClient = consultarCartaoClient;
        this.propostaRepository = propostaRepository;
        this.logger = LoggerFactory.getLogger(AssociarCartao.class);
    }

    @Scheduled(cron = "0 */30 * ? * *") //Executa a cada 30 minutos
    public void associarCartaoEProposta() {
        logger.info("Verificando cartões...");

        Iterable<Proposta> propostasSemCartao = propostaRepository.findByElegivelESemCartao();

        propostasSemCartao.forEach(p -> {
            String cartao = consultarCartao(p.getId());
            if (cartao != null)
                p.associarCartao(cartao);
        });

        propostaRepository.saveAll(propostasSemCartao);
    }

    private String consultarCartao(Long id) {
        try{
            DadosCartaoResponse response = consultarCartaoClient.consultarCartaoByProposta(String.valueOf(id));
            return response.getId();
        }catch (FeignException e) {
            return null;
        }

    }
}
