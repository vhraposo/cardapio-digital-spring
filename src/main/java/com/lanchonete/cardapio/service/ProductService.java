package com.lanchonete.cardapio.service;

import com.lanchonete.cardapio.dto.ProductRequest;
import com.lanchonete.cardapio.entity.Product;
import com.lanchonete.cardapio.exception.BusinessException;
import com.lanchonete.cardapio.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // Visitante e usuario logado usam este metodo: so ve o que esta disponivel
    public List<Product> listarDisponiveis() {
        return productRepository.findByDisponivelTrue();
    }

    // Admin ve o catalogo completo (inclusive indisponiveis) na area de gestao
    public List<Product> listarTodos() {
        return productRepository.findAll();
    }

    public Product buscarPorId(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));
    }

    public Product criar(ProductRequest request) {
        Product product = Product.builder()
                .nome(request.nome())
                .descricao(request.descricao())
                .preco(request.preco())
                .categoria(request.categoria())
                .imagemUrl(request.imagemUrl())
                .disponivel(request.disponivel() == null || request.disponivel())
                .estoque(request.estoque())
                .build();
        return productRepository.save(product);
    }

    public Product atualizar(Long id, ProductRequest request) {
        Product product = buscarPorId(id);
        product.setNome(request.nome());
        product.setDescricao(request.descricao());
        product.setPreco(request.preco());
        product.setCategoria(request.categoria());
        if (request.imagemUrl() != null) {
            product.setImagemUrl(request.imagemUrl());
        }
        if (request.disponivel() != null) {
            product.setDisponivel(request.disponivel());
        }
        product.setEstoque(request.estoque());
        return productRepository.save(product);
    }

    public void remover(Long id) {
        Product product = buscarPorId(id);
        productRepository.delete(product);
    }
}
