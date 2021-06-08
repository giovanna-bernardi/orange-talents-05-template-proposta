package br.com.zupacademy.giovanna.proposta.servicosExternos.analiseFinanceira;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "${proposta.services.analise-financeira.host}", name = "analiseFinanceira")
public interface AnaliseFinanceiraClient {
    @PostMapping("${proposta.services.analise-financeira.solicitacao}")
    AnaliseFinanceiraResponse verificaStatusSolicitante(@RequestBody AnaliseFinanceiraRequest pedidoAvaliacao);
}
