package com.lanchonete.cardapio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank @Email(message = "Email invalido") String email,
        @NotBlank String senha
) {}
