package br.com.zupacademy.giovanna.proposta.bloqueio;

import com.fasterxml.jackson.annotation.JsonCreator;

public class BloqueioResponse {
    private String resultado;

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
