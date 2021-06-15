package br.com.zupacademy.giovanna.proposta.carteira;

import br.com.zupacademy.giovanna.proposta.cartao.Cartao;
import br.com.zupacademy.giovanna.proposta.cartao.CartaoRepository;
import br.com.zupacademy.giovanna.proposta.exceptions.ErrorResponse;
import br.com.zupacademy.giovanna.proposta.servicosExternos.cartao.GerenciadorDeCarteira;
import br.com.zupacademy.giovanna.proposta.validations.CartaoValido;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.Optional;

@RestController
@Validated
public class CarteiraController {

    private final CartaoRepository cartaoRepository;
    private final GerenciadorDeCarteira gerenciadorDeCarteira;

    public CarteiraController(CartaoRepository cartaoRepository,
                              GerenciadorDeCarteira gerenciadorDeCarteira) {
        this.cartaoRepository = cartaoRepository;
        this.gerenciadorDeCarteira = gerenciadorDeCarteira;
    }

    @PostMapping("/cartoes/{id}/carteiras")
    public ResponseEntity<?> associaACarteira(@PathVariable @CartaoValido String id,
                                              @RequestBody @Valid CarteiraRequest request,
                                              UriComponentsBuilder uriComponentsBuilder) {
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);
        Cartao cartao = cartaoOptional.get();

        if(cartao.jaPossuiACarteira(request.getCarteira())){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorResponse(Arrays.asList("O cartão já possui a carteira '" + request.getCarteira() + "' associada a ele")));
        }

        Optional<CarteiraResponse> carteiraResponse = gerenciadorDeCarteira.tentaAssociarCarteira(cartao.getNumeroCartao(), request);

        if (carteiraResponse.isPresent() && carteiraResponse.get().associadaComSucesso()) {
            CarteiraResponse response = carteiraResponse.get();
            Carteira carteira = new Carteira(response.getId(), request.getCarteira(), cartao);
            cartao.adicionaCarteira(carteira);
            cartaoRepository.save(cartao);
            URI uri = uriComponentsBuilder.path("/cartoes/{idCartao}/carteiras/{idCarteira}").buildAndExpand(cartao.getId(), carteira.getId()).toUri();
            return ResponseEntity.created(uri).build();
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorResponse(Arrays.asList("Não foi possível associar a carteira " + request.getCarteira() + " ao cartão")));

    }
}
