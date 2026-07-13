package com.lanchonete.cardapio.repository;

import com.lanchonete.cardapio.entity.CategoriaProduto;
import com.lanchonete.cardapio.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByDisponivelTrue();
    List<Product> findByCategoria(CategoriaProduto categoria);
    List<Product> findByNomeContainingIgnoreCase(String nome);
}
