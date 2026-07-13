package com.lanchonete.cardapio.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String erro,
        String mensagem,
        Map<String, String> detalhes
) {
    public ErrorResponse(int status, String erro, String mensagem) {
        this(LocalDateTime.now(), status, erro, mensagem, null);
    }

    public ErrorResponse(int status, String erro, String mensagem, Map<String, String> detalhes) {
        this(LocalDateTime.now(), status, erro, mensagem, detalhes);
    }
}
