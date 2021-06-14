package br.com.zupacademy.giovanna.proposta.aviso;

import br.com.zupacademy.giovanna.proposta.cartao.Cartao;
import br.com.zupacademy.giovanna.proposta.cartao.CartaoRepository;
import br.com.zupacademy.giovanna.proposta.exceptions.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;

@RestController
public class AvisoViagemController {

    private final CartaoRepository cartaoRepository;

    public AvisoViagemController(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @PostMapping("/cartoes/{id}/avisos")
    public ResponseEntity<?> cadastraAvisoDeViagem(@PathVariable String id,
                                                   HttpServletRequest servletRequest,
                                                   @RequestBody @Valid AvisoViagemRequest request) {

        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cartão não encontrado"));

        if(cartao.estaBloqueado()) {
            return ResponseEntity.unprocessableEntity().body("O cartão está bloqueado");
        }

        String ipCliente = servletRequest.getRemoteAddr();
        if (!StringUtils.hasText(ipCliente)) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                    .body(new ErrorResponse(Arrays.asList("IP vazio")));
        }
        String userAgentCliente = servletRequest.getHeader("User-Agent");
        if (!StringUtils.hasText(userAgentCliente)) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                    .body(new ErrorResponse(Arrays.asList("Header User-Agent vazio")));
        }

        AvisoViagem aviso = request.toModel(ipCliente, userAgentCliente, cartao);
        cartao.adicionaAvisosDeViagem(aviso);
        cartaoRepository.save(cartao);

        return ResponseEntity.ok().body("Aviso de viagem cadastrado com sucesso");
    }
}
