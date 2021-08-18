package com.zupacademy.kleysson.proposta.utils.services;

import com.zupacademy.kleysson.proposta.dto.response.DadosCartaoResponse;
import com.zupacademy.kleysson.proposta.model.Cartao;
import com.zupacademy.kleysson.proposta.model.Proposta;
import com.zupacademy.kleysson.proposta.repository.CartaoRepository;
import com.zupacademy.kleysson.proposta.repository.PropostaRepository;
import com.zupacademy.kleysson.proposta.utils.enums.StatusProposta;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class AssociarCartao {

    private final ConsultarCartaoClient consultarCartaoClient;
    private final PropostaRepository propostaRepository;
    private final CartaoRepository cartaoRepository;
    private final Logger logger;

    public AssociarCartao(ConsultarCartaoClient consultarCartaoClient, PropostaRepository propostaRepository, CartaoRepository cartaoRepository) {
        this.consultarCartaoClient = consultarCartaoClient;
        this.propostaRepository = propostaRepository;
        this.cartaoRepository = cartaoRepository;
        this.logger = LoggerFactory.getLogger(AssociarCartao.class);
    }

    @Scheduled(cron = "0 */5 * ? * *") //Executa a cada 5 minutos
    public void associarCartaoEProposta() {
        logger.info("Verificando cartões...");

        List<Proposta> propostasSemCartao = propostaRepository.findByStatusPropostaAndCartaoIsNull(StatusProposta.ELEGIVEL);
        propostasSemCartao.forEach(p -> {
            Cartao cartao = consultarCartao(p.getId());
            if (Objects.nonNull(cartao)){
                cartaoRepository.save(cartao);
            }
        });
    }

    private Cartao consultarCartao(Long id) {
        try{
            DadosCartaoResponse response = consultarCartaoClient.consultarCartaoByProposta(String.valueOf(id));
            return response.toModel(propostaRepository);
        }catch (FeignException e) {
            return null;
        }
    }
}
