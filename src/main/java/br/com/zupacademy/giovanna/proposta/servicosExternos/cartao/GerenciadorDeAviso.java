package br.com.zupacademy.giovanna.proposta.servicosExternos.cartao;

import br.com.zupacademy.giovanna.proposta.aviso.AvisoViagemRequest;
import br.com.zupacademy.giovanna.proposta.aviso.AvisoViagemResponse;
import feign.FeignException;
import org.springframework.stereotype.Component;

@Component
public class GerenciadorDeAviso {

    private final CartaoClient client;

    public GerenciadorDeAviso(CartaoClient client) {
        this.client = client;
    }

    public boolean tentaAvisar(String idCartao, AvisoViagemRequest request) {
        try {
            AvisoViagemResponse status= client.atualizaAviso(idCartao, request);
            return status.getResultado().equals("CRIADO");
        } catch (FeignException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


}
