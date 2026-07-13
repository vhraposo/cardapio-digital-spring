package com.lanchonete.cardapio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lanchonete.cardapio.entity.Order;
import com.lanchonete.cardapio.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

   

    @Query("SELECT DISTINCT o FROM Order o " +
           "LEFT JOIN FETCH o.itens i " +
           "LEFT JOIN FETCH i.product " +
           "LEFT JOIN FETCH o.user " +
           "WHERE o.user = :user " +
           "ORDER BY o.criadoEm DESC")
    List<Order> findByUserWithItens(@Param("user") User user);

    @Query("SELECT DISTINCT o FROM Order o " +
           "LEFT JOIN FETCH o.itens i " +
           "LEFT JOIN FETCH i.product " +
           "LEFT JOIN FETCH o.user " +
           "ORDER BY o.criadoEm DESC")
    List<Order> findAllWithItens();

    @Query("SELECT DISTINCT o FROM Order o " +
           "LEFT JOIN FETCH o.itens i " +
           "LEFT JOIN FETCH i.product " +
           "LEFT JOIN FETCH o.user " +
           "WHERE o.id = :id")
    Optional<Order> findByIdWithItens(@Param("id") Long id);
}
