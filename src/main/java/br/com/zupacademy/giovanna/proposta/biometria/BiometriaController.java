package br.com.zupacademy.giovanna.proposta.biometria;

import br.com.zupacademy.giovanna.proposta.cartao.Cartao;
import br.com.zupacademy.giovanna.proposta.cartao.CartaoRepository;
import br.com.zupacademy.giovanna.proposta.validations.CartaoValido;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
public class BiometriaController {

    private final CartaoRepository cartaoRepository;

    public BiometriaController(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    @PostMapping("/cartoes/{id}/biometria")
    @Transactional
    public ResponseEntity<?> cadastraBiometria(@RequestBody @Valid BiometriaRequest request,
                                               @PathVariable @CartaoValido String id,
                                               UriComponentsBuilder uriComponentsBuilder) {

        Optional<Cartao> cartao = cartaoRepository.findById(id);

        List<Biometria> biometrias = request.toModelList(cartao.get());
        cartao.get().associaBiometrias(biometrias);
        cartaoRepository.save(cartao.get());

        URI uri = uriComponentsBuilder.path("/cartoes/{id}/biometria").buildAndExpand(cartao.get().getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
