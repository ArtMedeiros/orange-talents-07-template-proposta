package com.zupacademy.kleysson.proposta.repository;

import com.zupacademy.kleysson.proposta.model.BloqueioCartao;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BloqueioCartaoRepository extends CrudRepository<BloqueioCartao, Long> {

    Optional<BloqueioCartao> findByCartaoId(String id);
}
