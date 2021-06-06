package br.com.zupacademy.giovanna.proposta.validations;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;

@Documented
@ConstraintComposition(CompositionType.OR)
@CPF
@CNPJ
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface CPFouCNPJ {
    String message() default "O campo deve ter o formato de um CPF ou de um CNPJ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
