package br.com.zupacademy.giovanna.proposta.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {ClientePreenchidoValidator.class})
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ClientePreenchido {
    String message() default "Informações de cliente faltando";
    Class<?> [] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
