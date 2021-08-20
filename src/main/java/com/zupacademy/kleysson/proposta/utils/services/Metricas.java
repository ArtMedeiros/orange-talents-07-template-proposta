package com.zupacademy.kleysson.proposta.utils.services;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class Metricas {

    private final MeterRegistry registry;

    private Counter counterPropostas;
    private Counter countersolicitacoesBloqueio;
    private Counter counterBloqueiosRealizados;
    private Counter counterCartoesAssociados;
    private Counter counterCadastroBiometria;
    private Counter counterAvisosViagem;
    private Counter counterCarteirasAssociadas;
    private Counter counterStatusProposta;

    public Metricas(MeterRegistry registry) {
        this.registry = registry;

        inicializarContadores();
    }

    private void inicializarContadores() {
        this.counterPropostas = this.registry.counter("propostas_criadas");
        this.countersolicitacoesBloqueio = this.registry.counter("solicitacoes_bloqueio", "responsavel", "Propostas");
        this.counterBloqueiosRealizados = this.registry.counter("bloqueios_realizados", "responsavel", "API Legada");
        this.counterCartoesAssociados = this.registry.counter("cartoes_associados", "responsavel", "API Legada");
        this.counterCadastroBiometria = this.registry.counter("biometrias");
        this.counterAvisosViagem = this.registry.counter("avisos_viagem");
        this.counterCarteirasAssociadas = this.registry.counter("carteiras_associadas", "responsavel", "API Legada");
        this.counterStatusProposta = this.registry.counter("status_proposta");
    }

    public void incrementarPropostas(){
        this.counterPropostas.increment();
    }

    public void incrementarSolicitacoesBloqueio() {
        this.countersolicitacoesBloqueio.increment();
    }

    public void incrementarBloqueiosRealizados() {
        this.counterBloqueiosRealizados.increment();
    }

    public void incrementarCartoesAssociados() {
        this.counterCartoesAssociados.increment();
    }

    public void incrementarBiometrias() {
        this.counterCadastroBiometria.increment();
    }

    public void incrementarAvisosViagem() {
        this.counterAvisosViagem.increment();
    }

    public void incrementarCarteirasAssosciadas() {
        this.counterCarteirasAssociadas.increment();
    }

    public void incrementarStatusProposta() {
        this.counterStatusProposta.increment();
    }
}
