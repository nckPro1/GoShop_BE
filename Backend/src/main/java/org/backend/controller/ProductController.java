package org.backend.controller;

import lombok.RequiredArgsConstructor;
import org.backend.model.Product;
import org.backend.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // ai cũng được đọc
    @GetMapping
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public List<Product> getAll() {
        return productService.getAll();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PRODUCT_CREATE')")
    public Product create(@RequestBody Product p) {
        return productService.create(p);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_UPDATE')")
    public Product update(@PathVariable Long id, @RequestBody Product p) {
        return productService.update(id, p);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_DELETE')")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
