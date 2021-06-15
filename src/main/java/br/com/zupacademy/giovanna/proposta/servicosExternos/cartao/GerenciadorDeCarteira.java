package br.com.zupacademy.giovanna.proposta.servicosExternos.cartao;

import br.com.zupacademy.giovanna.proposta.carteira.CarteiraRequest;
import br.com.zupacademy.giovanna.proposta.carteira.CarteiraResponse;
import br.com.zupacademy.giovanna.proposta.carteira.StatusCarteira;
import feign.FeignException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GerenciadorDeCarteira {

    private final CartaoClient client;

    public GerenciadorDeCarteira(CartaoClient client) {
        this.client = client;
    }

    public Optional<CarteiraResponse> tentaAssociarCarteira(String numeroCartao, CarteiraRequest request){
        try{
            return Optional.of(client.associaACarteira(numeroCartao, request));
        } catch (FeignException.FeignClientException e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
}
