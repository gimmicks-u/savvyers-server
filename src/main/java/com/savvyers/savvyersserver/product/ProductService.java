package com.savvyers.savvyersserver.product;

import com.savvyers.savvyersserver.product.entity.ItemManufactureReportNumber;
import com.savvyers.savvyersserver.product.entity.Product;
import com.savvyers.savvyersserver.product.entity.ProductInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    
    private final ProductRepository productRepository;
    private final ProductInfoRepository productInfoRepository;
    private final ItemManufactureReportNumberRepository reportRepository;
    
    // ===== Product Operations =====
    
    public List<Product> getAllProducts() {
        log.info("Fetching all products");
        return productRepository.findAll();
    }
    
    public Page<Product> getAllProducts(Pageable pageable) {
        log.info("Fetching products with pagination: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return productRepository.findAll(pageable);
    }
    
    public Optional<Product> getProductById(Long id) {
        log.info("Fetching product by id: {}", id);
        return productRepository.findById(id);
    }
    
    public Optional<Product> getProductByFoodCode(String foodCode) {
        log.info("Fetching product by food code: {}", foodCode);
        return productRepository.findByFoodCd(foodCode);
    }
    
    public List<Product> searchProductsByName(String name) {
        log.info("Searching products by name containing: {}", name);
        return productRepository.findByFoodNmContainingIgnoreCase(name);
    }
    
    public Page<Product> searchProductsByName(String name, Pageable pageable) {
        log.info("Searching products by name containing: {} with pagination", name);
        return productRepository.findByFoodNmContainingIgnoreCase(name, pageable);
    }
    
    public List<Product> getProductsByFoodClassification(String level, String code) {
        log.info("Fetching products by food classification level {} with code: {}", level, code);
        return switch (level.toLowerCase()) {
            case "lv3", "level3" -> productRepository.findByFoodLv3Cd(code);
            case "lv4", "level4" -> productRepository.findByFoodLv4Cd(code);
            case "lv5", "level5" -> productRepository.findByFoodLv5Cd(code);
            default -> throw new IllegalArgumentException("Unsupported classification level: " + level);
        };
    }
    
    public List<Product> getProductsByNutritionalRange(String nutrient, BigDecimal min, BigDecimal max) {
        log.info("Fetching products by nutritional range - {}: {} to {}", nutrient, min, max);
        return switch (nutrient.toLowerCase()) {
            case "energy", "enerc" -> productRepository.findByEnercBetween(min, max);
            case "protein", "prot" -> productRepository.findByProtBetween(min, max);
            default -> throw new IllegalArgumentException("Unsupported nutrient type: " + nutrient);
        };
    }
    
    public List<Product> getProductsByCountry(String countryCode) {
        log.info("Fetching products by country code: {}", countryCode);
        return productRepository.findByCooCd(countryCode);
    }
    
    public List<Product> getImportedProducts() {
        log.info("Fetching imported products");
        return productRepository.findByImptYn("Y");
    }
    
    public List<Product> getDomesticProducts() {
        log.info("Fetching domestic products");
        return productRepository.findByImptYn("N");
    }
    
    // ===== ProductInfo Operations =====
    
    public List<ProductInfo> getAllProductInfo() {
        log.info("Fetching all product info");
        return productInfoRepository.findAll();
    }
    
    public Page<ProductInfo> getAllProductInfo(Pageable pageable) {
        log.info("Fetching product info with pagination");
        return productInfoRepository.findAll(pageable);
    }
    
    public Optional<ProductInfo> getProductInfoById(Long id) {
        log.info("Fetching product info by id: {}", id);
        return productInfoRepository.findById(id);
    }
    
    public List<ProductInfo> searchProductInfoByName(String name) {
        log.info("Searching product info by name: {}", name);
        return productInfoRepository.findByPrdlstNmContainingIgnoreCase(name);
    }
    
    public List<ProductInfo> getProductInfoByKind(String productKind) {
        log.info("Fetching product info by kind: {}", productKind);
        return productInfoRepository.findByPrdkind(productKind);
    }
    
    public List<ProductInfo> getProductInfoWithImages() {
        log.info("Fetching product info with images");
        return productInfoRepository.findByImgurl1IsNotNullOrImgurl2IsNotNull();
    }
    
    public List<ProductInfo> searchByAllergy(String allergyInfo) {
        log.info("Searching products by allergy info: {}", allergyInfo);
        return productInfoRepository.findByAllergyContainingIgnoreCase(allergyInfo);
    }
    
    public List<ProductInfo> searchByIngredient(String ingredient) {
        log.info("Searching products by ingredient: {}", ingredient);
        return productInfoRepository.findByRawmtrlContainingIgnoreCase(ingredient);
    }
    
    // ===== ItemManufactureReportNumber Operations =====
    
    public List<ItemManufactureReportNumber> getAllReports() {
        log.info("Fetching all manufacture report numbers");
        return reportRepository.findAll();
    }
    
    public Page<ItemManufactureReportNumber> getAllReports(Pageable pageable) {
        log.info("Fetching manufacture report numbers with pagination");
        return reportRepository.findAll(pageable);
    }
    
    public List<ItemManufactureReportNumber> getReportsByProductId(Long productId) {
        log.info("Fetching reports for product id: {}", productId);
        return reportRepository.findByProductId(productId);
    }
    
    public List<ItemManufactureReportNumber> getReportsByReportNumber(String reportNumber) {
        log.info("Fetching reports by report number: {}", reportNumber);
        return reportRepository.findByReportNo(reportNumber);
    }
    
    public List<ItemManufactureReportNumber> searchReportNumbers(String reportNumber) {
        log.info("Searching report numbers containing: {}", reportNumber);
        return reportRepository.findByReportNoContainingIgnoreCase(reportNumber);
    }
    
    public Page<ItemManufactureReportNumber> getAllReportsWithProducts(Pageable pageable) {
        log.info("Fetching all reports with product details");
        return reportRepository.findAllWithProducts(pageable);
    }
    
    // ===== Statistics Operations =====
    
    public long getProductCount() {
        return productRepository.count();
    }
    
    public long getProductInfoCount() {
        return productInfoRepository.count();
    }
    
    public long getReportCount() {
        return reportRepository.count();
    }
    
    public long getProductCountByClassification(String level, String code) {
        return switch (level.toLowerCase()) {
            case "lv3", "level3" -> productRepository.countByFoodLv3Cd(code);
            default -> 0L;
        };
    }
    
    public long getImportedProductCount() {
        return productRepository.countByImptYn("Y");
    }
    
    public long getDomesticProductCount() {
        return productRepository.countByImptYn("N");
    }
}
