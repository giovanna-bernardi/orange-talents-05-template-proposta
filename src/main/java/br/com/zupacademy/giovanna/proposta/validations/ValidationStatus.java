package br.com.zupacademy.giovanna.proposta.validations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public enum ValidationStatus {
    INEXISTENTE{
        @Override
        public HttpStatus getStatus() {
            return HttpStatus.NOT_FOUND;
        }
    },
    BLOQUEADO {
        @Override
        public HttpStatus getStatus() {
            return HttpStatus.UNPROCESSABLE_ENTITY;
        }
    },
    IP_VAZIO{
        @Override
        public HttpStatus getStatus() {
            return HttpStatus.PRECONDITION_FAILED;
        }
    },
    USER_AGENT_VAZIO{
        @Override
        public HttpStatus getStatus() {
            return HttpStatus.PRECONDITION_FAILED;
        }
    };

    public abstract HttpStatus getStatus();
}
