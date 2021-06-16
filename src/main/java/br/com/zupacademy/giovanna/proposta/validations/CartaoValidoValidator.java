package br.com.zupacademy.giovanna.proposta.validations;

import br.com.zupacademy.giovanna.proposta.cartao.Cartao;
import br.com.zupacademy.giovanna.proposta.cartao.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class CartaoValidoValidator implements ConstraintValidator<CartaoValido, String> {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(value);

        if (cartaoOptional.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Cartão não encontrado")
                    .addPropertyNode(ValidationStatus.INEXISTENTE.name())
                    .addConstraintViolation();
            return false;
        }

        if(cartaoOptional.get().estaBloqueado()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O cartão está bloqueado")
                    .addPropertyNode(ValidationStatus.BLOQUEADO.name())
                    .addConstraintViolation();
            return false;
        }


        return true;
    }
}
