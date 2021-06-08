package br.com.zupacademy.giovanna.proposta.proposta;

import br.com.zupacademy.giovanna.proposta.exceptions.ErrorResponse;
import br.com.zupacademy.giovanna.proposta.servicosExternos.analiseFinanceira.AnalisaProposta;
import br.com.zupacademy.giovanna.proposta.servicosExternos.analiseFinanceira.AnaliseFinanceiraRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import static br.com.zupacademy.giovanna.proposta.proposta.Proposta.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private final PropostaRepository propostaRepository;
    private final AnalisaProposta analisaProposta;

    public PropostaController(PropostaRepository propostaRepository,
                              AnalisaProposta analisaProposta) {
        this.propostaRepository = propostaRepository;
        this.analisaProposta = analisaProposta;
    }

    @PostMapping
    public ResponseEntity<?> cadastra(@RequestBody @Valid PropostaRequest request,
                                      UriComponentsBuilder uriComponentsBuilder) {

        if (documentoJaExiste(request.getDocumento())) {
            ErrorResponse errorResponse = new ErrorResponse(Arrays.asList("Não é permitido mais de uma proposta para um mesmo solicitante"));
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
        }

        Proposta proposta = request.toModel();
        salvaProposta(proposta);

        AnaliseFinanceiraRequest pedidoAvaliacao = new AnaliseFinanceiraRequest(request.getDocumento(), request.getNome(), String.valueOf(proposta.getId()));
        StatusProposta resultadoAvaliacao = analisaProposta.verifica(pedidoAvaliacao);
        proposta.atualizaStatus(resultadoAvaliacao);
        salvaProposta(proposta);

        URI uri = uriComponentsBuilder.path("/propostas/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    private boolean documentoJaExiste(String documento) {
        return propostaRepository.findByDocumento(documento).isPresent();
    }

    @Transactional
    private void salvaProposta(Proposta proposta) {
        propostaRepository.save(proposta);
    }

}
