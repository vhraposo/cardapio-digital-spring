package com.lanchonete.cardapio.dto;

public record AuthResponse(
        String token,
        String tipo,
        Long userId,
        String nome,
        String email,
        String role
) {
    public AuthResponse(String token, Long userId, String nome, String email, String role) {
        this(token, "Bearer", userId, nome, email, role);
    }
}
