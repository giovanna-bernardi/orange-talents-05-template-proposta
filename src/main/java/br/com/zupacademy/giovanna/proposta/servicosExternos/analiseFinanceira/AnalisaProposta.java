package br.com.zupacademy.giovanna.proposta.servicosExternos.analiseFinanceira;

import br.com.zupacademy.giovanna.proposta.proposta.Proposta;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class AnalisaProposta {

    private AnaliseFinanceiraClient client;

    public AnalisaProposta(AnaliseFinanceiraClient client) {
        this.client = client;
    }

    public Proposta.StatusProposta verifica(AnaliseFinanceiraRequest pedidoAvaliacao) {
        try {
            AnaliseFinanceiraResponse resultadoAvaliacao = client.verificaStatusSolicitante(pedidoAvaliacao);
            return resultadoAvaliacao.getResultadoSolicitacao();
        } catch (FeignException e) {
            if (e.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()) {
                String resultadoSolicitacao = Arrays.stream(e.getMessage().split(","))
                        .filter(m -> m.startsWith("\"resultadoSolicitacao\""))
                        .reduce("", (p, m) -> {
                            int startIndex = m.indexOf(":") + 2; // tira as aspas
                            int endIndex = m.length() - 1; // tira as aspas
                            return m.substring(startIndex, endIndex);
                        });
                StatusAnaliseFinanceira status = StatusAnaliseFinanceira.valueOf(resultadoSolicitacao);
                return status.toStatusProposta();
            }
            return StatusAnaliseFinanceira.COM_RESTRICAO.toStatusProposta();
        }
    }
}
