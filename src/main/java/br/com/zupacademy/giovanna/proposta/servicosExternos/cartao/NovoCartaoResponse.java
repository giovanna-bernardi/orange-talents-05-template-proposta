package br.com.zupacademy.giovanna.proposta.servicosExternos.cartao;

import br.com.zupacademy.giovanna.proposta.proposta.Proposta;

import java.time.LocalDateTime;

public class NovoCartaoResponse {
    private String id;
    private LocalDateTime emitidoEm;
    private String titular;
    private String idProposta;

    public NovoCartaoResponse(String id, LocalDateTime emitidoEm,
                              String titular, String idProposta) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.idProposta = idProposta;
    }

    public Cartao toModel(Proposta proposta) {
        return new Cartao(this.id, this.emitidoEm, this.titular, proposta);
    }

    @Override
    public String toString() {
        return "NovoCartaoResponse{" +
                "id='" + id + '\'' +
                ", emitidoEm=" + emitidoEm +
                ", titular='" + titular + '\'' +
                ", idProposta='" + idProposta + '\'' +
                '}';
    }
}
