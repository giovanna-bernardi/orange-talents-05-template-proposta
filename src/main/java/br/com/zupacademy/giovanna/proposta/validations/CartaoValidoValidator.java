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
            return false;
        }

        if(cartaoOptional.get().estaBloqueado()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O cartão está bloqueado")
                    .addPropertyNode("BLOQUEADO")
                    .addConstraintViolation();
            return false;
        }


        return true;
    }
}