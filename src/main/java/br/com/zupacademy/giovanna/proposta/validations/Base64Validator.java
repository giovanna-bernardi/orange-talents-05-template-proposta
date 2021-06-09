package br.com.zupacademy.giovanna.proposta.validations;

import br.com.zupacademy.giovanna.proposta.exceptions.ErrorResponse;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Base64Validator implements ConstraintValidator<Base64, List<String>> {

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {

        List<String> mensagensDeErro = new ArrayList<>();

        for (String s : value) {
            try {
                java.util.Base64.getDecoder().decode(s);
            } catch(Exception e) {
                mensagensDeErro.add(s + " -> não está em Base64");
            }
        }

        if(mensagensDeErro.isEmpty()){
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(mensagensDeErro.stream().collect(Collectors.joining("\n", "\n", "\n")))
                .addConstraintViolation();

        return false;
    }
}
