package br.com.zupacademy.giovanna.proposta.proposta;

public class AnaliseFinanceiraRequest {

    private String documento;
    private String nome;
    private String idProposta;

    public AnaliseFinanceiraRequest(String documento, String nome, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
