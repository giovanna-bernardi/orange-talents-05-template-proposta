package br.com.zupacademy.giovanna.proposta.proposta;

import br.com.zupacademy.giovanna.proposta.validations.CPFouCNPJ;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @CPFouCNPJ
    @Column(nullable = false)
    private String documento;

    @NotBlank @Email
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotBlank
    @Column(nullable = false)
    private String endereco;

    @NotNull @Positive
    @Column(nullable = false)
    private BigDecimal salarioBruto;

    /**
     * For Hibernate use only
     */
    @Deprecated
    public Proposta() {
    }

    public Proposta(@NotBlank @CPFouCNPJ String documento,
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

    public Long getId() {
        return id;
    }
}
