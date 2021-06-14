package br.com.zupacademy.giovanna.proposta.aviso;

import br.com.zupacademy.giovanna.proposta.cartao.Cartao;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class AvisoViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String destino;

    @NotNull @FutureOrPresent
    private LocalDate termino;

    @NotBlank
    private String ipClient;

    @NotBlank
    private String userAgentClient;

    @ManyToOne @NotNull
    private Cartao cartao;

    @Deprecated
    public AvisoViagem() {
    }

    public AvisoViagem(String destino,
                       LocalDate termino,
                       String ipClient,
                       String userAgentClient,
                       Cartao cartao) {
        this.destino = destino;
        this.termino = termino;
        this.ipClient = ipClient;
        this.userAgentClient = userAgentClient;
        this.cartao = cartao;
    }
}
