package br.com.zupacademy.giovanna.proposta.proposta;

import br.com.zupacademy.giovanna.proposta.validations.CPFouCNPJ;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class PropostaRequest {

    @NotBlank @CPFouCNPJ
    private String documento;

    @NotBlank @Email
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String endereco;

    @NotNull @Positive
    private BigDecimal salarioBruto;

    public PropostaRequest(@NotBlank @CPFouCNPJ String documento,
                           @NotBlank @Email String email,
                           @NotBlank String nome,
                           @NotBlank String endereco,
                           @NotNull @Positive BigDecimal salarioBruto) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salarioBruto = salarioBruto;
    }

    public Proposta toModel() {
        return new Proposta(this.documento, this.email, this.nome, this.endereco, this.salarioBruto);
    }

    public String getDocumento() {
        return documento;
    }

    @Override
    public String toString() {
        return "PropostaRequest{" +
                "documento='" + documento + '\'' +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", salarioBruto=" + salarioBruto +
                '}';
    }

}
