package br.com.zupacademy.giovanna.proposta.servicosExternos.analiseFinanceira;

import br.com.zupacademy.giovanna.proposta.proposta.Proposta;

public enum StatusAnaliseFinanceira {
    SEM_RESTRICAO, COM_RESTRICAO;

    public Proposta.StatusProposta toStatusProposta() {
        return this.equals(SEM_RESTRICAO) ? Proposta.StatusProposta.ELEGIVEL :
                Proposta.StatusProposta.NAO_ELEGIVEL;
    }
}
