package br.com.zupacademy.giovanna.proposta.biometria;

import br.com.zupacademy.giovanna.proposta.cartao.Cartao;
import br.com.zupacademy.giovanna.proposta.validations.Base64;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BiometriaRequest {

    @Base64
    @NotEmpty @NotNull
    private List<String> fingerprintsEmBase64;

    @JsonCreator
    public BiometriaRequest(List<String> fingerprintsEmBase64) {
        this.fingerprintsEmBase64 = fingerprintsEmBase64;
    }

    public List<Biometria> toModelList(Cartao cartao) {
        List<Biometria> biometrias = new ArrayList<>();
        biometrias.addAll(fingerprintsEmBase64.stream()
                .map(f -> new Biometria(f,cartao))
                .collect(Collectors.toList()));
        return biometrias;
    }

    public List<String> getFingerprintsEmBase64() {
        return fingerprintsEmBase64;
    }
}
