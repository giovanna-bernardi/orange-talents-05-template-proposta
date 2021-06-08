package br.com.zupacademy.giovanna.proposta.servicosExternos.analiseFinanceira;

import br.com.zupacademy.giovanna.proposta.proposta.Proposta;

public class AnaliseFinanceiraResponse {

    private String documento;
    private String nome;
    private StatusAnaliseFinanceira resultadoSolicitacao;
    private String idProposta;

    public AnaliseFinanceiraResponse(String documento,
                                     String nome,
                                     StatusAnaliseFinanceira resultadoSolicitacao,
                                     String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.resultadoSolicitacao = resultadoSolicitacao;
        this.idProposta = idProposta;
    }

    public Proposta.StatusProposta getResultadoSolicitacao() {
        return resultadoSolicitacao.toStatusProposta();
    }
}
