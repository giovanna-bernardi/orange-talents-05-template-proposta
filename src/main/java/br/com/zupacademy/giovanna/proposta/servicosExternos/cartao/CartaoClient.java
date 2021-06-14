package br.com.zupacademy.giovanna.proposta.servicosExternos.cartao;

import br.com.zupacademy.giovanna.proposta.aviso.AvisoViagemRequest;
import br.com.zupacademy.giovanna.proposta.aviso.AvisoViagemResponse;
import br.com.zupacademy.giovanna.proposta.bloqueio.BloqueioInfo;
import br.com.zupacademy.giovanna.proposta.bloqueio.BloqueioRequest;
import br.com.zupacademy.giovanna.proposta.bloqueio.BloqueioResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(url = "${proposta.services.cartao.host}", name = "cartao")
public interface CartaoClient {

    @GetMapping("${proposta.services.cartao.cartoes}")
    NovoCartaoResponse pegaCartao(@RequestParam String idProposta);

    @GetMapping("${proposta.services.cartao.cartoes}/{id}")
    BloqueioInfo verificaBloqueioAtivo(@PathVariable("id") String idCartao);

    @PostMapping("${proposta.services.cartao.cartoes}/{id}/${proposta.services.cartao.bloqueios}")
    BloqueioResponse bloqueiaCartao(@PathVariable String id, @RequestBody BloqueioRequest request);

    @PostMapping("${proposta.services.cartao.cartoes}/{id}/${proposta.services.cartao.avisos}")
    AvisoViagemResponse atualizaAviso(@PathVariable String id, @RequestBody AvisoViagemRequest request);
}
