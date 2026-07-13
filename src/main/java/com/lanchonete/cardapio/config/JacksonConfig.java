package com.lanchonete.cardapio.config;

import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Sem isso, o Jackson quebra ao tentar serializar entidades com
 * associacoes LAZY (ex: Order.user), porque tenta serializar o proxy
 * interno do Hibernate (ByteBuddyInterceptor) em vez do objeto real.
 *
 * Erro tipico sem essa configuracao:
 * "Type definition error: [simple type, class
 *  org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor]"
 *
 * O Hibernate6Module resolve isso automaticamente: se a associacao ja foi
 * carregada (como acontece aqui, graças ao Open-Session-In-View), ele
 * serializa o objeto real; se nao foi carregada, serializa como null em
 * vez de forcar um select adicional (FORCE_LAZY_LOADING fica desligado
 * por padrao, o que evita consultas extras desnecessarias).
 */
@Configuration
public class JacksonConfig {

    @Bean
    public Hibernate6Module hibernate6Module() {
        return new Hibernate6Module();
    }
}
