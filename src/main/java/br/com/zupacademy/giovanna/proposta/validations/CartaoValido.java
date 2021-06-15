package br.com.zupacademy.giovanna.proposta.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {CartaoValidoValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CartaoValido {
    String message() default "Cartão não encontrado";
    Class<?> [] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
