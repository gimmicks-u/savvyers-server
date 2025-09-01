package com.savvyers.savvyersserver.product;

import com.savvyers.savvyersserver.product.entity.ItemManufactureReportNumber;
import com.savvyers.savvyersserver.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemManufactureReportNumberRepository extends JpaRepository<ItemManufactureReportNumber, Integer> {
    
    // Basic CRUD operations are inherited from JpaRepository
    
    // Find by report number
    List<ItemManufactureReportNumber> findByReportNo(String reportNumber);
    Optional<ItemManufactureReportNumber> findByReportNoIgnoreCase(String reportNumber);
    List<ItemManufactureReportNumber> findByReportNoContainingIgnoreCase(String reportNumber);
    
    // Find by product (using the relationship)
    List<ItemManufactureReportNumber> findByProduct(Product product);
    List<ItemManufactureReportNumber> findByProductId(Long productId);
    Page<ItemManufactureReportNumber> findByProductId(Long productId, Pageable pageable);
    
    // Find reports for multiple products
    List<ItemManufactureReportNumber> findByProductIdIn(List<Long> productIds);
    
    // Custom query to join with Product information
    @Query("SELECT imr FROM ItemManufactureReportNumber imr JOIN FETCH imr.product WHERE imr.reportNo = :reportNo")
    List<ItemManufactureReportNumber> findByReportNoWithProduct(@Param("reportNo") String reportNumber);
    
    @Query("SELECT imr FROM ItemManufactureReportNumber imr JOIN FETCH imr.product WHERE imr.product.id = :productId")
    List<ItemManufactureReportNumber> findByProductIdWithProduct(@Param("productId") Long productId);

    // Find all reports with their associated products (be careful with large datasets)
    @Query("SELECT imr FROM ItemManufactureReportNumber imr JOIN FETCH imr.product")
    List<ItemManufactureReportNumber> findAllWithProducts();
    
    @Query("SELECT imr FROM ItemManufactureReportNumber imr JOIN FETCH imr.product")
    Page<ItemManufactureReportNumber> findAllWithProducts(Pageable pageable);
    
    // Find reports for products from specific food classification
    @Query("SELECT imr FROM ItemManufactureReportNumber imr JOIN imr.product p WHERE p.foodLv3Cd = :foodLv3Cd")
    List<ItemManufactureReportNumber> findByProductFoodLv3Cd(@Param("foodLv3Cd") String foodLv3Cd);
    
    @Query("SELECT imr FROM ItemManufactureReportNumber imr JOIN imr.product p WHERE p.foodLv4Cd = :foodLv4Cd")
    List<ItemManufactureReportNumber> findByProductFoodLv4Cd(@Param("foodLv4Cd") String foodLv4Cd);
    
    // Count queries for statistics
    long countByProduct(Product product);
    long countByProductId(Long productId);
    
    @Query("SELECT COUNT(imr) FROM ItemManufactureReportNumber imr JOIN imr.product p WHERE p.foodLv3Cd = :foodLv3Cd")
    long countByProductFoodLv3Cd(@Param("foodLv3Cd") String foodLv3Cd);
    
    // Check if a product has reports
    boolean existsByProduct(Product product);
    boolean existsByProductId(Long productId);
    
    // Delete by product (cascade alternative)
    void deleteByProduct(Product product);
    void deleteByProductId(Long productId);
}