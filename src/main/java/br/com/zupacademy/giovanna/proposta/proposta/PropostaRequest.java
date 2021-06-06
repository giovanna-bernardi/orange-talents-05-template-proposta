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


    /*documento do solicitante deve ser obrigatório e válido
    email não pode ser vazio, nulo ou inválido
    nome não pode ser vazio ou nulo
    endereço não pode ser vazio ou nulo
    salário bruto não pode ser vazio, nulo ou negativo*/

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
