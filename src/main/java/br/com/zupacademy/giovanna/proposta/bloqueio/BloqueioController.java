package br.com.zupacademy.giovanna.proposta.bloqueio;

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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;

@RestController
public class BloqueioController {

    private final CartaoRepository cartaoRepository;

    public BloqueioController(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @PostMapping("/cartoes/{id}/bloqueios")
    public ResponseEntity<?> pedidoBloqueio(@PathVariable String id,
                                            HttpServletRequest servletRequest){
        Optional<Cartao> cartaoOptional = cartaoRepository.findByNumeroCartao(id);
        if(cartaoOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(Arrays.asList("Cartão não encontrado")));
        }

        Cartao cartao = cartaoOptional.get();

        if(cartao.estaBloqueado()){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new ErrorResponse(Arrays.asList("O cartão já está bloqueado")));
        }

        String ipCliente = servletRequest.getRemoteAddr();
        if(!StringUtils.hasText(ipCliente)){
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                    .body(new ErrorResponse(Arrays.asList("IP vazio")));
        }
        String userAgentCliente = servletRequest.getHeader("User-Agent");
        if(!StringUtils.hasText(userAgentCliente)){
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                    .body(new ErrorResponse(Arrays.asList("Header User-Agent vazio")));
        }

        cartao.bloqueia(ipCliente, userAgentCliente);
        cartaoRepository.save(cartao);

        return ResponseEntity.ok().body("Cartão bloqueado com sucesso");
    }
}
