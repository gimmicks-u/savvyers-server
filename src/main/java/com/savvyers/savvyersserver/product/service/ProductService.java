package com.savvyers.savvyersserver.product.service;

import com.savvyers.savvyersserver.product.document.ProductDocument;
import com.savvyers.savvyersserver.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<ProductDocument> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public ProductDocument getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}