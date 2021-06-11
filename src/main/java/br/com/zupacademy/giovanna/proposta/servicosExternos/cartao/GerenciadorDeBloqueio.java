package br.com.zupacademy.giovanna.proposta.servicosExternos.cartao;

import br.com.zupacademy.giovanna.proposta.bloqueio.*;
import feign.FeignException;
import org.springframework.stereotype.Component;


@Component
public class GerenciadorDeBloqueio {

    private final CartaoClient client;

    public GerenciadorDeBloqueio(CartaoClient client) {
        this.client = client;
    }

    public boolean verificaCartaoBloqueadoSistemaExterno(String idCartao){
        try{
            BloqueioInfo bloqueioInfo = client.verificaBloqueioAtivo(idCartao);
            if(bloqueioInfo.getBloqueios().isEmpty()) return false;
            boolean ativo = bloqueioInfo.getBloqueios().stream().anyMatch(b -> b.isAtivo());
            return ativo;
        } catch (FeignException e){
            System.out.println(e);
            return false;
        }
    }

    public StatusBloqueio tentaBloquear(String idCartao, String sistemaResponsavel) {
        try {
            BloqueioResponse status = client.bloqueiaCartao(idCartao, new BloqueioRequest(sistemaResponsavel));
            return status.getResultado().equals(StatusBloqueio.BLOQUEADO.name()) ? StatusBloqueio.BLOQUEADO : StatusBloqueio.FALHA;
        } catch (FeignException e) {
            System.out.println(e.getMessage());
            return StatusBloqueio.FALHA;
        }
    }
}
