package com.lanchonete.cardapio.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Nome e obrigatorio") String nome,
        @NotBlank @Email(message = "Email invalido") String email,
        @NotBlank @Size(min = 6, message = "Senha deve ter no minimo 6 caracteres") String senha,
        String telefone
) {}
