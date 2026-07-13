package com.lanchonete.cardapio.service.payment;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Implementacao SIMULADA de pagamento, usada por padrao (payment.gateway.provider=SIMULADO).
 *
 * Isso existe porque este ambiente de geracao de codigo nao tem acesso a
 * chaves reais de gateways de pagamento (Stripe/Mercado Pago/PagSeguro).
 *
 * Para integrar de verdade:
 * 1. Crie uma classe implementando PaymentGateway (ex: StripePaymentGateway,
 *    MercadoPagoPaymentGateway) usando o SDK oficial do provedor.
 * 2. Anote com @Component e @ConditionalOnProperty ou use @Primary,
 *    trocando payment.gateway.provider no application.properties.
 * 3. O restante do sistema (PaymentService, OrderService) nao precisa mudar,
 *    pois dependem apenas da interface PaymentGateway.
 */
@Component
public class SimulatedPaymentGateway implements PaymentGateway {

    @Override
    public PaymentResult charge(BigDecimal valor, String metodoPagamento, String referenciaPedido) {
        // Simula aprovacao automatica (fluxo feliz) para fins de demonstracao/desenvolvimento
        String transacaoId = "SIM-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();
        return new PaymentResult(
                true,
                transacaoId,
                "Pagamento simulado aprovado via " + metodoPagamento + " para o pedido " + referenciaPedido
        );
    }
}
