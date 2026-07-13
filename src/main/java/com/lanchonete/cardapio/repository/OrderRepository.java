package com.lanchonete.cardapio.repository;

import com.lanchonete.cardapio.entity.Order;
import com.lanchonete.cardapio.entity.OrderStatus;
import com.lanchonete.cardapio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByCriadoEmDesc(User user);
    List<Order> findAllByOrderByCriadoEmDesc();
    List<Order> findByStatusOrderByCriadoEmDesc(OrderStatus status);
}
