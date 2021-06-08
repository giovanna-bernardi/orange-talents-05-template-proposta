package br.com.zupacademy.giovanna.proposta.servicosExternos.cartao;

import br.com.zupacademy.giovanna.proposta.proposta.Proposta;
import br.com.zupacademy.giovanna.proposta.proposta.PropostaRepository;
import feign.FeignException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AssociacaoCartaoProposta {

    private final CartaoClient client;
    private final PropostaRepository propostaRepository;

    public AssociacaoCartaoProposta(CartaoClient client,
                                    PropostaRepository propostaRepository) {
        this.client = client;
        this.propostaRepository = propostaRepository;
    }

    @Scheduled(fixedDelayString = "${proposta.services.cartao.periodicidade}")
    private void verificaNovosCartoes() {
        System.out.println("Scheduler - verificando sistema de cartões");
        List<Proposta> propostasElegiveisSemCartao = propostaRepository.findByStatusAndCartaoNull(Proposta.StatusProposta.ELEGIVEL);
        if (propostasElegiveisSemCartao.isEmpty()) {
            return;
        }
        propostasElegiveisSemCartao.forEach(this::associaCartao);
//        propostasElegiveisSemCartao.forEach(System.out::println);
    }

    private void associaCartao(Proposta proposta) {
        try {
            NovoCartaoResponse cartaoResponse = client.pegaCartao(String.valueOf(proposta.getId()));
            proposta.associaCartao(cartaoResponse.toModel(proposta));
            propostaRepository.save(proposta);
        } catch (FeignException e) {
            System.out.println("Tentando novamente em poucos instantes");
        }

    }
}
