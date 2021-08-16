package com.zupacademy.kleysson.proposta.repository;

import com.zupacademy.kleysson.proposta.model.Proposta;
import com.zupacademy.kleysson.proposta.utils.enums.StatusProposta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PropostaRepository extends CrudRepository<Proposta, Long> {

    Optional<Proposta> findByDocumento(String documento);

    @Query("SELECT prop FROM Proposta prop " +
            "WHERE prop.statusProposta = com.zupacademy.kleysson.proposta.utils.enums.StatusProposta.ELEGIVEL AND " +
            "(prop.numeroCartao = null OR prop.numeroCartao = '') ")
    Iterable<Proposta> findByElegivelESemCartao();
}
