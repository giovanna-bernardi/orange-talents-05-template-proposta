package br.com.zupacademy.giovanna.proposta.biometria;

import br.com.zupacademy.giovanna.proposta.cartao.Cartao;
import br.com.zupacademy.giovanna.proposta.cartao.CartaoRepository;
import br.com.zupacademy.giovanna.proposta.exceptions.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class BiometriaController {

    private final CartaoRepository cartaoRepository;

    public BiometriaController(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @PostMapping("/cartoes/{id}/biometria")
    @Transactional
    public ResponseEntity<?> cadastraBiometria(@RequestBody @Valid BiometriaRequest request,
                                               @PathVariable String id,
                                               UriComponentsBuilder uriComponentsBuilder) {

        Optional<Cartao> cartao = cartaoRepository.findByNumeroCartao(id);
        if(cartao.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(Arrays.asList("Cartão não encontrado")));
        }

        List<Biometria> biometrias = request.toModelList(cartao.get());
        cartao.get().associaBiometrias(biometrias);
        cartaoRepository.save(cartao.get());

        URI uri = uriComponentsBuilder.path("/cartoes/{id}/biometria").buildAndExpand(cartao.get().getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
