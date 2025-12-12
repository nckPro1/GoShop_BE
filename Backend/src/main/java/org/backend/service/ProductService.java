package org.backend.service;

import lombok.RequiredArgsConstructor;
import org.backend.model.Product;
import org.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product create(Product p) {
        return productRepository.save(p);
    }

    public Product update(Long id, Product p) {
        Product old = productRepository.findById(id).orElseThrow();
        old.setName(p.getName());
        old.setQuantity(p.getQuantity());
        old.setPrice(p.getPrice());
        return productRepository.save(old);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
