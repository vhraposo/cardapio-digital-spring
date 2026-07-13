package com.lanchonete.cardapio.dto;

import com.lanchonete.cardapio.entity.CategoriaProduto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank String nome,
        String descricao,
        @NotNull @PositiveOrZero BigDecimal preco,
        @NotNull CategoriaProduto categoria,
        String imagemUrl,
        Boolean disponivel,
        @PositiveOrZero Integer estoque
) {}
