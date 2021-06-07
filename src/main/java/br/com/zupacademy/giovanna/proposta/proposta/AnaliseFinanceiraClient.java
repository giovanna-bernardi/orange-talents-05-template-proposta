package br.com.zupacademy.giovanna.proposta.proposta;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "${proposta.services.analise-financeira.host}", name = "analiseFinanceira")
public interface AnaliseFinanceiraClient {
    @PostMapping("${proposta.services.analise-financeira.solicitacao}")
    AnaliseFinanceiraResponse verificaStatusSolicitante(AnaliseFinanceiraRequest pedidoAvaliacao);
}
