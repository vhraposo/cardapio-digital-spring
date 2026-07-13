package com.lanchonete.cardapio.exception;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final HttpStatus status;

    public BusinessException(HttpStatus status, String mensagem) {
        super(mensagem);
        this.status = status;
    }
}
