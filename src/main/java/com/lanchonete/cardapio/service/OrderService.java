package com.lanchonete.cardapio.service;

import com.lanchonete.cardapio.dto.OrderItemRequest;
import com.lanchonete.cardapio.dto.OrderRequest;
import com.lanchonete.cardapio.entity.*;
import com.lanchonete.cardapio.exception.BusinessException;
import com.lanchonete.cardapio.repository.OrderRepository;
import com.lanchonete.cardapio.service.payment.PaymentGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final PaymentGateway paymentGateway;

    @Transactional
    public Order criarPedido(User user, OrderRequest request) {
        Order order = Order.builder()
                .user(user)
                .enderecoEntrega(request.enderecoEntrega())
                .observacoes(request.observacoes())
                .paymentMethod(request.paymentMethod())
                .status(OrderStatus.AGUARDANDO_PAGAMENTO)
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemReq : request.itens()) {
            Product product = productService.buscarPorId(itemReq.productId());

            if (!product.isDisponivel()) {
                throw new BusinessException(HttpStatus.BAD_REQUEST,
                        "Produto '" + product.getNome() + "' nao esta disponivel no momento");
            }
            if (product.getEstoque() != null && product.getEstoque() < itemReq.quantidade()) {
                throw new BusinessException(HttpStatus.BAD_REQUEST,
                        "Estoque insuficiente para '" + product.getNome() + "'");
            }

            OrderItem item = OrderItem.builder()
                    .product(product)
                    .quantidade(itemReq.quantidade())
                    .precoUnitario(product.getPreco())
                    .observacao(itemReq.observacao())
                    .build();

            order.addItem(item);
            total = total.add(item.getSubtotal());

            if (product.getEstoque() != null) {
                product.setEstoque(product.getEstoque() - itemReq.quantidade());
            }
        }

        order.setTotal(total);
        Order salvo = orderRepository.save(order);

        // Processa o pagamento logo apos criar o pedido
        PaymentGateway.PaymentResult resultado = paymentGateway.charge(
                total, request.paymentMethod(), "PEDIDO-" + salvo.getId()
        );

        if (resultado.aprovado()) {
            salvo.setStatus(OrderStatus.PAGO);
            salvo.setPaymentReference(resultado.transacaoId());
        } else {
            salvo.setStatus(OrderStatus.CANCELADO);
        }

        return orderRepository.save(salvo);
    }

    public List<Order> listarPedidosDoUsuario(User user) {
        return orderRepository.findByUserWithItens(user);
    }

    public Order buscarPorId(Long id) {
        return orderRepository.findByIdWithItens(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Pedido nao encontrado"));
    }

    // Garante que um usuario comum so acesse os proprios pedidos
    public Order buscarPorIdDoUsuario(Long id, User user) {
        Order order = buscarPorId(id);
        if (!order.getUser().getId().equals(user.getId())) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "Voce nao tem permissao para ver este pedido");
        }
        return order;
    }

    // ===== Operacoes de ADMIN =====

    public List<Order> listarTodosPedidos() {
        return orderRepository.findAllWithItens();
    }

    @Transactional
    public Order atualizarStatus(Long id, OrderStatus novoStatus) {
        Order order = buscarPorId(id);
        order.setStatus(novoStatus);
        return orderRepository.save(order);
    }
}
