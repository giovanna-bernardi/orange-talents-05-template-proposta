package br.com.zupacademy.giovanna.proposta.bloqueio;

import br.com.zupacademy.giovanna.proposta.cartao.Cartao;
import br.com.zupacademy.giovanna.proposta.cartao.CartaoRepository;
import br.com.zupacademy.giovanna.proposta.exceptions.ErrorResponse;
import br.com.zupacademy.giovanna.proposta.servicosExternos.cartao.GerenciadorDeBloqueio;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

@RestController
public class BloqueioController {

    private final MeterRegistry meterRegistry;
    private final CartaoRepository cartaoRepository;
    private final GerenciadorDeBloqueio gerenciadorDeBloqueio;

    public BloqueioController(MeterRegistry meterRegistry,
                              CartaoRepository cartaoRepository,
                              GerenciadorDeBloqueio gerenciadorDeBloqueio) {
        this.meterRegistry = meterRegistry;
        this.cartaoRepository = cartaoRepository;
        this.gerenciadorDeBloqueio = gerenciadorDeBloqueio;
    }

    @PostMapping("/cartoes/{id}/bloqueios")
    public ResponseEntity<?> pedidoBloqueio(@PathVariable String id,
                                            HttpServletRequest servletRequest) {
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);
        if (cartaoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(Arrays.asList("Cartão não encontrado")));
        }

        Cartao cartao = cartaoOptional.get();

        Counter pedidoSucesso = meterRegistry.counter("pedido_bloqueio_sucesso");
        Counter pedidoFalha = meterRegistry.counter("pedido_bloqueio_falha");


        boolean estaBloqueadoNoSistemaExterno = gerenciadorDeBloqueio.verificaCartaoBloqueadoSistemaExterno(cartao.getNumeroCartao());
        if(!cartao.estaBloqueado() && estaBloqueadoNoSistemaExterno){
            System.out.println("Cartão não estava bloqueado na nossa aplicação, mas já estava no sistema externo");
            cartao.setStatus(Cartao.StatusCartao.BLOQUEADO);
            cartaoRepository.save(cartao);
        }

        if (cartao.estaBloqueado()) {
            pedidoSucesso.increment();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new ErrorResponse(Arrays.asList("O cartão já está bloqueado")));
        }

        String ipCliente = servletRequest.getRemoteAddr();
        if (!StringUtils.hasText(ipCliente)) {
            pedidoFalha.increment();
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                    .body(new ErrorResponse(Arrays.asList("IP vazio")));
        }
        String userAgentCliente = servletRequest.getHeader("User-Agent");
        if (!StringUtils.hasText(userAgentCliente)) {
            pedidoFalha.increment();
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                    .body(new ErrorResponse(Arrays.asList("Header User-Agent vazio")));
        }

        StatusBloqueio statusBloqueio = gerenciadorDeBloqueio.tentaBloquear(cartao.getNumeroCartao(), "propostas");
        if (statusBloqueio.equals(StatusBloqueio.FALHA)) {
            pedidoFalha.increment();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorResponse(Arrays.asList("Não foi possível realizar o bloqueio do cartão")));
        }

        cartao.bloqueia(ipCliente, userAgentCliente);
        cartaoRepository.save(cartao);
        pedidoSucesso.increment();
        return ResponseEntity.ok().body("Cartão bloqueado com sucesso");

    }
}
