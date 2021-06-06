package br.com.zupacademy.giovanna.proposta.proposta;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private final PropostaRepository propostaRepository;

    public PropostaController(PropostaRepository propostaRepository) {
        this.propostaRepository = propostaRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastra(@RequestBody @Valid PropostaRequest request,
                                      UriComponentsBuilder uriComponentsBuilder) {

        Proposta proposta = request.toModel();
        propostaRepository.save(proposta);

        URI uri = uriComponentsBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
