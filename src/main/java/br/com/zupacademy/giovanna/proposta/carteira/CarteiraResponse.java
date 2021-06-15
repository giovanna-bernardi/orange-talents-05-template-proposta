package br.com.zupacademy.giovanna.proposta.carteira;

public class CarteiraResponse {

    private StatusCarteira resultado;
    private String id;

    public CarteiraResponse(StatusCarteira resultado, String id) {
        this.resultado = resultado;
        this.id = id;
    }

    public boolean associadaComSucesso(){
        return this.resultado.equals(StatusCarteira.ASSOCIADA);
    }

    @Override
    public String toString() {
        return "CarteiraResponse{" +
                "resultado=" + resultado +
                ", id='" + id + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public StatusCarteira getStatus() {
        return resultado;
    }
}
