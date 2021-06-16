package br.com.zupacademy.giovanna.proposta.servicosExternos.health_check;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.Socket;

@Component(value = "analiseFinanceira")
public class AnaliseFinanceiraHealthCheck implements HealthIndicator {

    @Value("${proposta.services.analise-financeira.host}")
    private String urlServico;

    @Override
    public Health health() {
        try (Socket socket = new Socket(new java.net.URL(urlServico.substring(0,urlServico.lastIndexOf(":"))).getHost(), Integer.valueOf(urlServico.substring(urlServico.lastIndexOf(":")+1)))) {
        } catch (Exception e) {
            return Health.down().withDetail("error", e.getMessage()).build();
        }
        return Health.up().build();
    }
}
