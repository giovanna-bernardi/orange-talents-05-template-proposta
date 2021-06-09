package br.com.zupacademy.giovanna.proposta.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {Base64Validator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Base64 {
    String message() default "Formato de Base64 inválido";
    Class<?> [] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
