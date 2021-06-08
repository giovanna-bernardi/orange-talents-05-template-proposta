package br.com.zupacademy.giovanna.proposta.proposta;

import br.com.zupacademy.giovanna.proposta.servicosExternos.cartao.Cartao;
import br.com.zupacademy.giovanna.proposta.servicosExternos.cartao.CartaoDetalhe;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class AcompanhamentoProposta {

    private String documento;
    private String email;
    private String nome;
    private String endereco;
    private BigDecimal salarioBruto;
    private Proposta.StatusProposta statusProposta;
    private CartaoDetalhe cartao;

    public AcompanhamentoProposta(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.email = proposta.getEmail();
        this.nome = proposta.getNome();
        this.endereco = proposta.getEndereco();
        this.salarioBruto = proposta.getSalarioBruto();
        this.statusProposta = proposta.getStatus();
        if(proposta.getCartao() != null) {
            this.cartao = new CartaoDetalhe(proposta.getCartao());
        }
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalarioBruto() {
        return salarioBruto;
    }

    public Proposta.StatusProposta getStatusProposta() {
        return statusProposta;
    }

    public CartaoDetalhe getCartao() {
        return cartao;
    }
}
