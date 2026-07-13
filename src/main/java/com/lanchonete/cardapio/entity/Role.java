package com.lanchonete.cardapio.entity;

/**
 * Perfis de acesso do sistema.
 * VISITANTE nao existe como registro no banco: e o estado padrao
 * de qualquer requisicao sem token JWT valido (ver SecurityConfig).
 */
public enum Role {
    USER,
    ADMIN
}
