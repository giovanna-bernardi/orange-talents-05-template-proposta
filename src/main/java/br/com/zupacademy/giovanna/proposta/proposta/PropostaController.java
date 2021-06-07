package br.com.zupacademy.giovanna.proposta.proposta;

import br.com.zupacademy.giovanna.proposta.exceptions.ErrorResponse;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private final PropostaRepository propostaRepository;
    private final AnaliseFinanceiraClient client;

    public PropostaController(PropostaRepository propostaRepository, AnaliseFinanceiraClient client) {
        this.propostaRepository = propostaRepository;
        this.client = client;
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
        atualizaStatusDaProposta(pedidoAvaliacao, proposta);
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

    private void atualizaStatusDaProposta(AnaliseFinanceiraRequest pedidoAvaliacao, Proposta proposta) {
        try {
            AnaliseFinanceiraResponse resultadoAvaliacao = client.verificaStatusSolicitante(pedidoAvaliacao);
            proposta.setStatus(resultadoAvaliacao.getResultadoSolicitacao());
        } catch (FeignException e) {
            if (e.status() == HttpStatus.UNPROCESSABLE_ENTITY.value()) {
                String resultadoSolicitacao = Arrays.stream(e.getMessage().split(","))
                        .filter(m -> m.startsWith("\"resultadoSolicitacao\""))
                        .reduce("", (p, m) -> {
                            int startIndex = m.indexOf(":") + 2; // tira as aspas
                            int endIndex = m.length() - 1; // tira as aspas
                            return m.substring(startIndex, endIndex);
                        });
                StatusAnaliseFinanceira status = StatusAnaliseFinanceira.valueOf(resultadoSolicitacao);
                proposta.setStatus(status.toStatusProposta());
            }
        }
    }

}
