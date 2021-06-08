package br.com.zupacademy.giovanna.proposta.proposta;

import br.com.zupacademy.giovanna.proposta.servicosExternos.cartao.Cartao;
import br.com.zupacademy.giovanna.proposta.validations.CPFouCNPJ;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
public class Proposta {

    public enum StatusProposta {
        ELEGIVEL, NAO_ELEGIVEL
    }

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

    @Enumerated(EnumType.STRING)
    private StatusProposta status;

    @OneToOne(cascade = CascadeType.MERGE)
    private Cartao cartao;

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

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public void atualizaStatus(StatusProposta status) {
        this.status = status;
    }

    public void associaCartao(Cartao cartao) {
        Assert.isTrue(this.aprovada(), "Não se pode associar um cartão a uma proposta não aprovada.");
        Assert.isTrue(this.cartao == null, "Esta proposta já possui um cartão associado a ela");
        this.cartao = cartao;
    }

    public boolean aprovada() {
        return this.status.equals(StatusProposta.ELEGIVEL);
    }

    @Override
    public String toString() {
        return "Proposta{" +
                "id=" + id +
                ", documento='" + documento + '\'' +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", salarioBruto=" + salarioBruto +
                ", status=" + status +
                '}';
    }
}
