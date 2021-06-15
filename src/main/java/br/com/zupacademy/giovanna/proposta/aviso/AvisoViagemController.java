package br.com.zupacademy.giovanna.proposta.aviso;

import br.com.zupacademy.giovanna.proposta.cartao.Cartao;
import br.com.zupacademy.giovanna.proposta.cartao.CartaoRepository;
import br.com.zupacademy.giovanna.proposta.exceptions.ErrorResponse;
import br.com.zupacademy.giovanna.proposta.servicosExternos.cartao.GerenciadorDeAviso;
import br.com.zupacademy.giovanna.proposta.validations.CartaoValido;
import br.com.zupacademy.giovanna.proposta.validations.ClientePreenchido;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;

@RestController
@Validated
public class AvisoViagemController {

    private final CartaoRepository cartaoRepository;
    private final GerenciadorDeAviso gerenciadorDeAviso;

    public AvisoViagemController(CartaoRepository cartaoRepository,
                                 GerenciadorDeAviso gerenciadorDeAviso) {
        this.cartaoRepository = cartaoRepository;
        this.gerenciadorDeAviso = gerenciadorDeAviso;
    }

    @PostMapping("/cartoes/{id}/avisos")
    public ResponseEntity<?> cadastraAvisoDeViagem(@PathVariable @CartaoValido String id,
                                                   @ClientePreenchido HttpServletRequest servletRequest,
                                                   @RequestBody @Valid AvisoViagemRequest request) {

        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);
        Cartao cartao = cartaoOptional.get();

        boolean conseguiuAvisarSistamExterno = gerenciadorDeAviso.tentaAvisar(cartao.getNumeroCartao(), request);
        if(!conseguiuAvisarSistamExterno){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                    new ErrorResponse(Arrays.asList("Não foi possível enviar o aviso de viagem")));
        }

        String ipCliente = servletRequest.getRemoteAddr();
        String userAgentCliente = servletRequest.getHeader("User-Agent");

        AvisoViagem aviso = request.toModel(ipCliente, userAgentCliente, cartao);
        cartao.adicionaAvisosDeViagem(aviso);
        cartaoRepository.save(cartao);

        return ResponseEntity.ok().body("Aviso de viagem cadastrado com sucesso");
    }
}
