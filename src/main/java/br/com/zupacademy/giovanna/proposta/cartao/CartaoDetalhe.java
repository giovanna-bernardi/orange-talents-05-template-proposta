package br.com.zupacademy.giovanna.proposta.cartao;
import java.time.LocalDateTime;

public class CartaoDetalhe {

    private String numeroCartao;
    private LocalDateTime dataEmissao;
    private String nomeTitular;

    public CartaoDetalhe(Cartao cartao) {
        this.numeroCartao = cartao.getNumeroCartao();
        this.dataEmissao = cartao.getDataEmissao();
        this.nomeTitular = cartao.getNomeTitular();
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public LocalDateTime getDataEmissao() {
        return dataEmissao;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }
}
