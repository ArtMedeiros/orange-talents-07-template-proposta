package com.zupacademy.kleysson.proposta.repository;

import com.zupacademy.kleysson.proposta.model.Cartao;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartaoRepository extends CrudRepository<Cartao, Long> {

    Optional<Cartao> findById(String id);
}
