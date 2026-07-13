package com.lanchonete.cardapio.service.payment;

import java.math.BigDecimal;

/**
 * Abstracao do gateway de pagamento. Troque a implementacao
 * SimulatedPaymentGateway por uma integracao real (Stripe, Mercado Pago,
 * PagSeguro etc.) sem precisar alterar o restante da aplicacao.
 */
public interface PaymentGateway {

    PaymentResult charge(BigDecimal valor, String metodoPagamento, String referenciaPedido);

    record PaymentResult(boolean aprovado, String transacaoId, String mensagem) {}
}
