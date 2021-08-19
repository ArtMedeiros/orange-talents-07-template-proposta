package com.zupacademy.kleysson.proposta.repository;

import com.zupacademy.kleysson.proposta.model.Cartao;
import com.zupacademy.kleysson.proposta.model.CarteiraPagamento;
import com.zupacademy.kleysson.proposta.utils.enums.GatewayPagamento;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CarteiraPagamentoRepository extends CrudRepository<CarteiraPagamento, Long> {

    Optional<CarteiraPagamento> findByCarteiraAndCartaoId(GatewayPagamento carteira, String id);
}
