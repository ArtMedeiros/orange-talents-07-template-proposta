package com.zupacademy.kleysson.proposta.repository;

import com.zupacademy.kleysson.proposta.model.Proposta;
import com.zupacademy.kleysson.proposta.utils.enums.StatusProposta;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PropostaRepository extends CrudRepository<Proposta, Long> {

    Optional<Proposta> findByDocumento(String documento);

    List<Proposta> findByStatusPropostaAndCartaoIsNull(StatusProposta status);
}
