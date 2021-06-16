package br.com.zupacademy.giovanna.proposta.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class OfuscadorDeDadosConverter implements AttributeConverter<String, String> {

    @Value("${proposta.secret}")
    private String segredo;

    @Value("${proposta.salt}")
    private String salt;


    @Override
    public String convertToDatabaseColumn(String s) {
        return Encryptors.queryableText(segredo, salt).encrypt(s);
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return Encryptors.queryableText(segredo, salt).decrypt(s);
    }
}
