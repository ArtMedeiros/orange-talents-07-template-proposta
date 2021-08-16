package com.zupacademy.kleysson.proposta.utils.enums;

public enum StatusAnalise {
    COM_RESTRICAO{
        @Override
        public StatusProposta toProposta() {
            return StatusProposta.NAO_ELEGIVEL;
        }
    },
    SEM_RESTRICAO {
        @Override
        public StatusProposta toProposta() {
            return StatusProposta.ELEGIVEL;
        }
    };

    public abstract StatusProposta toProposta();
}
