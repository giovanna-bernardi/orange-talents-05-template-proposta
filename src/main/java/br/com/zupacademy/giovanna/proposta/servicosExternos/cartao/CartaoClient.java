package br.com.zupacademy.giovanna.proposta.servicosExternos.cartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${proposta.services.cartao.host}", name = "cartao")
public interface CartaoClient {

    @GetMapping("${proposta.services.cartao.cartoes}")
    NovoCartaoResponse pegaCartao(@RequestParam String idProposta);
}
