package com.lanchonete.cardapio.config;

import com.lanchonete.cardapio.entity.CategoriaProduto;
import com.lanchonete.cardapio.entity.Product;
import com.lanchonete.cardapio.entity.Role;
import com.lanchonete.cardapio.entity.User;
import com.lanchonete.cardapio.repository.ProductRepository;
import com.lanchonete.cardapio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Popula o banco H2 com dados iniciais para facilitar os testes:
 * - 1 usuario ADMIN
 * - 1 usuario comum (USER)
 * - produtos reais de lanchonete, com imagens autenticas (Unsplash)
 *
 * Login admin: admin@lanchonete.com / admin123
 * Login user:  cliente@lanchonete.com / cliente123
 */
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            seedUsers();
        }
        if (productRepository.count() == 0) {
            seedProducts();
        }
    }

    private void seedUsers() {
        User admin = User.builder()
                .nome("Administrador")
                .email("admin@lanchonete.com")
                .senha(passwordEncoder.encode("admin123"))
                .telefone("(43) 99999-0000")
                .role(Role.ADMIN)
                .ativo(true)
                .build();

        User cliente = User.builder()
                .nome("Cliente Teste")
                .email("cliente@lanchonete.com")
                .senha(passwordEncoder.encode("cliente123"))
                .telefone("(43) 98888-1111")
                .role(Role.USER)
                .ativo(true)
                .build();

        userRepository.save(admin);
        userRepository.save(cliente);
    }

    private void seedProducts() {
        productRepository.save(Product.builder()
                .nome("Cheeseburger Artesanal")
                .descricao("Pao brioche, blend 180g, queijo cheddar, alface, tomate e molho especial da casa")
                .preco(new BigDecimal("24.90"))
                .categoria(CategoriaProduto.LANCHE)
                .imagemUrl("https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=800&q=80")
                .disponivel(true)
                .estoque(50)
                .build());

        productRepository.save(Product.builder()
                .nome("Bacon Burger Duplo")
                .descricao("Dois blends de 120g, queijo prato, bacon crocante e cebola caramelizada")
                .preco(new BigDecimal("29.90"))
                .categoria(CategoriaProduto.LANCHE)
                .imagemUrl("https://images.unsplash.com/photo-1553979459-d2229ba7433b?w=800&q=80")
                .disponivel(true)
                .estoque(40)
                .build());

        productRepository.save(Product.builder()
                .nome("X-Salada Classico")
                .descricao("Hamburguer, presunto, queijo, alface, tomate, milho e maionese da casa")
                .preco(new BigDecimal("19.90"))
                .categoria(CategoriaProduto.LANCHE)
                .imagemUrl("https://images.unsplash.com/photo-1520072959219-c595dc870360?w=800&q=80")
                .disponivel(true)
                .estoque(60)
                .build());

        productRepository.save(Product.builder()
                .nome("Hot Dog Especial")
                .descricao("Pao, salsicha artesanal, purê, milho, batata palha e queijo ralado")
                .preco(new BigDecimal("16.90"))
                .categoria(CategoriaProduto.LANCHE)
                .imagemUrl("https://images.unsplash.com/photo-1612392062631-94dd858cba88?w=800&q=80")
                .disponivel(true)
                .estoque(45)
                .build());

        productRepository.save(Product.builder()
                .nome("Batata Frita Crocante")
                .descricao("Porcao generosa de batatas fritas temperadas com sal e ervas")
                .preco(new BigDecimal("14.90"))
                .categoria(CategoriaProduto.ACOMPANHAMENTO)
                .imagemUrl("https://images.unsplash.com/photo-1573080496219-bb080dd4f877?w=800&q=80")
                .disponivel(true)
                .estoque(80)
                .build());

        productRepository.save(Product.builder()
                .nome("Onion Rings")
                .descricao("Aneis de cebola empanados e fritos, acompanha molho barbecue")
                .preco(new BigDecimal("15.90"))
                .categoria(CategoriaProduto.ACOMPANHAMENTO)
                .imagemUrl("https://images.unsplash.com/photo-1639024471283-03518883512d?w=800&q=80")
                .disponivel(true)
                .estoque(35)
                .build());

        productRepository.save(Product.builder()
                .nome("Refrigerante Lata 350ml")
                .descricao("Coca-Cola, Guarana ou Fanta - gelado")
                .preco(new BigDecimal("6.50"))
                .categoria(CategoriaProduto.BEBIDA)
                .imagemUrl("https://images.unsplash.com/photo-1554866585-cd94860890b7?w=800&q=80")
                .disponivel(true)
                .estoque(100)
                .build());

        productRepository.save(Product.builder()
                .nome("Milkshake de Chocolate")
                .descricao("Milkshake cremoso de chocolate belga com chantilly")
                .preco(new BigDecimal("17.90"))
                .categoria(CategoriaProduto.BEBIDA)
                .imagemUrl("https://images.unsplash.com/photo-1572490122747-3968b75cc699?w=800&q=80")
                .disponivel(true)
                .estoque(30)
                .build());

        productRepository.save(Product.builder()
                .nome("Suco Natural de Laranja")
                .descricao("Suco de laranja natural, feito na hora, 500ml")
                .preco(new BigDecimal("9.90"))
                .categoria(CategoriaProduto.BEBIDA)
                .imagemUrl("https://images.unsplash.com/photo-1613478223719-2ab802602423?w=800&q=80")
                .disponivel(true)
                .estoque(40)
                .build());

        productRepository.save(Product.builder()
                .nome("Brownie com Sorvete")
                .descricao("Brownie de chocolate quente com bola de sorvete de creme")
                .preco(new BigDecimal("16.90"))
                .categoria(CategoriaProduto.SOBREMESA)
                .imagemUrl("https://images.unsplash.com/photo-1606313564200-e75d5e30476c?w=800&q=80")
                .disponivel(true)
                .estoque(25)
                .build());

        productRepository.save(Product.builder()
                .nome("Combo Duplo Bacon + Batata + Refri")
                .descricao("Bacon Burger Duplo, batata frita media e refrigerante lata")
                .preco(new BigDecimal("44.90"))
                .categoria(CategoriaProduto.COMBO)
                .imagemUrl("https://images.unsplash.com/photo-1626078436625-8ae8163b0247?w=800&q=80")
                .disponivel(true)
                .estoque(30)
                .build());

        productRepository.save(Product.builder()
                .nome("Combo Classico X-Salada + Batata + Refri")
                .descricao("X-Salada Classico, batata frita media e refrigerante lata")
                .preco(new BigDecimal("36.90"))
                .categoria(CategoriaProduto.COMBO)
                .imagemUrl("https://images.unsplash.com/photo-1550547660-d9450f859349?w=800&q=80")
                .disponivel(true)
                .estoque(30)
                .build());
    }
}
