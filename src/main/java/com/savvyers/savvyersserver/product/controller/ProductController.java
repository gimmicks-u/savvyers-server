package com.savvyers.savvyersserver.product.controller;

import com.savvyers.savvyersserver.product.document.ProductDocument;
import com.savvyers.savvyersserver.product.dto.request.ProductSearchRequest;
import com.savvyers.savvyersserver.product.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDocument>> searchProducts(
            @Valid @ModelAttribute ProductSearchRequest request
        ) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<ProductDocument> products = productService.searchProducts(request.getKeyword(), pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDocument> getProductById(
            @PathVariable @Positive(message = "상품 ID는 양수여야 합니다") Long id
        ) {
        ProductDocument product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }
}
