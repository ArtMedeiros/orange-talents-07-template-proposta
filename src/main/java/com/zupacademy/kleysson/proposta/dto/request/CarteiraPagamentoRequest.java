package com.zupacademy.kleysson.proposta.dto.request;

import com.zupacademy.kleysson.proposta.model.Cartao;
import com.zupacademy.kleysson.proposta.model.CarteiraPagamento;
import com.zupacademy.kleysson.proposta.utils.enums.GatewayPagamento;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CarteiraPagamentoRequest {

    @NotBlank
    @Email
    private String email;

    @NotNull
    private GatewayPagamento carteira;

    public String getEmail() {
        return email;
    }

    public GatewayPagamento getCarteira() {
        return carteira;
    }

    public CarteiraPagamento toModel(String idLegado, Cartao cartao) {
        return new CarteiraPagamento(idLegado, email, carteira, cartao);
    }
}
