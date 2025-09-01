package com.savvyers.savvyersserver.product;

import com.savvyers.savvyersserver.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Basic CRUD operations are inherited from JpaRepository
    
    // Find by food classification
    List<Product> findByFoodLv3Cd(String foodLv3Cd);
    List<Product> findByFoodLv4Cd(String foodLv4Cd);
    List<Product> findByFoodLv5Cd(String foodLv5Cd);
    
    // Find by food name (case insensitive, containing)
    List<Product> findByFoodNmContainingIgnoreCase(String foodName);
    Page<Product> findByFoodNmContainingIgnoreCase(String foodName, Pageable pageable);
    
    // Find by food code
    Optional<Product> findByFoodCd(String foodCd);
    
    // Find by nutritional ranges
    List<Product> findByEnercBetween(BigDecimal minEnergy, BigDecimal maxEnergy);
    List<Product> findByProtBetween(BigDecimal minProtein, BigDecimal maxProtein);
    
    // Find by food origin
    List<Product> findByFoodOriginCd(String foodOriginCd);
    
    // Note: For reports related to a product, use ItemManufactureReportNumberRepository.findByProduct(product)
    
    // Find products by manufacturer name
    List<Product> findByMfrNmContainingIgnoreCase(String manufacturerName);
    
    // Find by country of origin
    List<Product> findByCooCd(String countryCode);
    List<Product> findByCooNm(String countryName);
    
    // Find by import status
    List<Product> findByImptYn(String importStatus);
    
    // Paginated queries for large datasets
    Page<Product> findByFoodLv3Cd(String foodLv3Cd, Pageable pageable);
    Page<Product> findByFoodLv4Cd(String foodLv4Cd, Pageable pageable);
    
    // Count queries for statistics
    long countByFoodLv3Cd(String foodLv3Cd);
    long countByImptYn(String importStatus);
}