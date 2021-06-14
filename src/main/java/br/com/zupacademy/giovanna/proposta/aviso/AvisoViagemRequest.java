package br.com.zupacademy.giovanna.proposta.aviso;

import br.com.zupacademy.giovanna.proposta.cartao.Cartao;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemRequest {

    @NotBlank
    private String destino;

    @NotNull @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate validoAte;

    public AvisoViagemRequest(String destino, LocalDate validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public AvisoViagem toModel(String ipClient, String userAgentClient, Cartao cartao) {
        return new AvisoViagem(this.destino, this.validoAte, ipClient, userAgentClient, cartao);
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }
}
