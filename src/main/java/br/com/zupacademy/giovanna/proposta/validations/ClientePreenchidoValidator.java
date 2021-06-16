package br.com.zupacademy.giovanna.proposta.validations;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ClientePreenchidoValidator implements ConstraintValidator<ClientePreenchido, HttpServletRequest> {
    @Override
    public boolean isValid(HttpServletRequest value, ConstraintValidatorContext context) {

        context.disableDefaultConstraintViolation();

        String ipCliente = value.getRemoteAddr();
        if (!StringUtils.hasText(ipCliente)) {
            context.buildConstraintViolationWithTemplate("IP vazio")
                    .addPropertyNode(ValidationStatus.IP_VAZIO.name())
                    .addConstraintViolation();
            return false;
        }

        String userAgentCliente = value.getHeader("User-Agent");
        if (!StringUtils.hasText(userAgentCliente)) {
            context.buildConstraintViolationWithTemplate("Header User-Agent vazio")
                    .addPropertyNode(ValidationStatus.USER_AGENT_VAZIO.name())
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
