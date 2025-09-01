package com.savvyers.savvyersserver.product;

import com.savvyers.savvyersserver.product.entity.ItemManufactureReportNumber;
import com.savvyers.savvyersserver.product.entity.Product;
import com.savvyers.savvyersserver.product.entity.ProductInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // ===== Product Endpoints =====
    
    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        
        Sort sort = sortDirection.equalsIgnoreCase("desc") 
            ? Sort.by(sortBy).descending() 
            : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> products = productService.getAllProducts(pageable);
        
        log.info("Retrieved {} products (page {}, size {})", products.getTotalElements(), page, size);
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        log.info("Fetching product with id: {}", id);
        Optional<Product> product = productService.getProductById(id);
        
        return product.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/products/search")
    public ResponseEntity<Page<Product>> searchProducts(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productService.searchProductsByName(query, pageable);
        
        log.info("Search for '{}' returned {} results", query, products.getTotalElements());
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/products/food-code/{foodCode}")
    public ResponseEntity<Product> getProductByFoodCode(@PathVariable String foodCode) {
        log.info("Fetching product with food code: {}", foodCode);
        Optional<Product> product = productService.getProductByFoodCode(foodCode);
        
        return product.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/products/classification/{level}/{code}")
    public ResponseEntity<List<Product>> getProductsByClassification(
            @PathVariable String level,
            @PathVariable String code) {
        
        log.info("Fetching products by classification {} - {}", level, code);
        try {
            List<Product> products = productService.getProductsByFoodClassification(level, code);
            return ResponseEntity.ok(products);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid classification level: {}", level);
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/products/nutrition/{nutrient}")
    public ResponseEntity<List<Product>> getProductsByNutritionalRange(
            @PathVariable String nutrient,
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        
        log.info("Fetching products by {} range: {} to {}", nutrient, min, max);
        try {
            List<Product> products = productService.getProductsByNutritionalRange(nutrient, min, max);
            return ResponseEntity.ok(products);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid nutrient type: {}", nutrient);
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/products/country/{countryCode}")
    public ResponseEntity<List<Product>> getProductsByCountry(@PathVariable String countryCode) {
        log.info("Fetching products by country code: {}", countryCode);
        List<Product> products = productService.getProductsByCountry(countryCode);
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/products/imported")
    public ResponseEntity<List<Product>> getImportedProducts() {
        log.info("Fetching imported products");
        List<Product> products = productService.getImportedProducts();
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/products/domestic")
    public ResponseEntity<List<Product>> getDomesticProducts() {
        log.info("Fetching domestic products");
        List<Product> products = productService.getDomesticProducts();
        return ResponseEntity.ok(products);
    }
    
    // ===== ProductInfo Endpoints =====
    
    @GetMapping("/product-info")
    public ResponseEntity<Page<ProductInfo>> getAllProductInfo(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductInfo> productInfo = productService.getAllProductInfo(pageable);
        
        log.info("Retrieved {} product info entries", productInfo.getTotalElements());
        return ResponseEntity.ok(productInfo);
    }
    
    @GetMapping("/product-info/{id}")
    public ResponseEntity<ProductInfo> getProductInfoById(@PathVariable Long id) {
        log.info("Fetching product info with id: {}", id);
        Optional<ProductInfo> productInfo = productService.getProductInfoById(id);
        
        return productInfo.map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/product-info/search")
    public ResponseEntity<List<ProductInfo>> searchProductInfo(@RequestParam String query) {
        log.info("Searching product info with query: {}", query);
        List<ProductInfo> productInfo = productService.searchProductInfoByName(query);
        return ResponseEntity.ok(productInfo);
    }
    
    @GetMapping("/product-info/kind/{productKind}")
    public ResponseEntity<List<ProductInfo>> getProductInfoByKind(@PathVariable String productKind) {
        log.info("Fetching product info by kind: {}", productKind);
        List<ProductInfo> productInfo = productService.getProductInfoByKind(productKind);
        return ResponseEntity.ok(productInfo);
    }
    
    @GetMapping("/product-info/with-images")
    public ResponseEntity<List<ProductInfo>> getProductInfoWithImages() {
        log.info("Fetching product info with images");
        List<ProductInfo> productInfo = productService.getProductInfoWithImages();
        return ResponseEntity.ok(productInfo);
    }
    
    @GetMapping("/product-info/allergy")
    public ResponseEntity<List<ProductInfo>> searchByAllergy(@RequestParam String allergyInfo) {
        log.info("Searching products by allergy: {}", allergyInfo);
        List<ProductInfo> productInfo = productService.searchByAllergy(allergyInfo);
        return ResponseEntity.ok(productInfo);
    }
    
    @GetMapping("/product-info/ingredient")
    public ResponseEntity<List<ProductInfo>> searchByIngredient(@RequestParam String ingredient) {
        log.info("Searching products by ingredient: {}", ingredient);
        List<ProductInfo> productInfo = productService.searchByIngredient(ingredient);
        return ResponseEntity.ok(productInfo);
    }
    
    // ===== ItemManufactureReportNumber Endpoints =====
    
    @GetMapping("/reports")
    public ResponseEntity<Page<ItemManufactureReportNumber>> getAllReports(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<ItemManufactureReportNumber> reports = productService.getAllReports(pageable);
        
        log.info("Retrieved {} manufacture reports", reports.getTotalElements());
        return ResponseEntity.ok(reports);
    }
    
    @GetMapping("/reports/product/{productId}")
    public ResponseEntity<List<ItemManufactureReportNumber>> getReportsByProductId(@PathVariable Long productId) {
        log.info("Fetching reports for product id: {}", productId);
        List<ItemManufactureReportNumber> reports = productService.getReportsByProductId(productId);
        return ResponseEntity.ok(reports);
    }
    
    @GetMapping("/reports/number/{reportNumber}")
    public ResponseEntity<List<ItemManufactureReportNumber>> getReportsByNumber(@PathVariable String reportNumber) {
        log.info("Fetching reports by number: {}", reportNumber);
        List<ItemManufactureReportNumber> reports = productService.getReportsByReportNumber(reportNumber);
        return ResponseEntity.ok(reports);
    }
    
    @GetMapping("/reports/search")
    public ResponseEntity<List<ItemManufactureReportNumber>> searchReports(@RequestParam String query) {
        log.info("Searching reports with query: {}", query);
        List<ItemManufactureReportNumber> reports = productService.searchReportNumbers(query);
        return ResponseEntity.ok(reports);
    }
    
    @GetMapping("/reports/with-products")
    public ResponseEntity<Page<ItemManufactureReportNumber>> getAllReportsWithProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<ItemManufactureReportNumber> reports = productService.getAllReportsWithProducts(pageable);
        
        log.info("Retrieved {} reports with product details", reports.getTotalElements());
        return ResponseEntity.ok(reports);
    }
    
    // ===== Statistics Endpoints =====
    
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        log.info("Fetching system statistics");
        
        Map<String, Object> stats = Map.of(
            "totalProducts", productService.getProductCount(),
            "totalProductInfo", productService.getProductInfoCount(),
            "totalReports", productService.getReportCount(),
            "importedProducts", productService.getImportedProductCount(),
            "domesticProducts", productService.getDomesticProductCount()
        );
        
        return ResponseEntity.ok(stats);
    }
}
