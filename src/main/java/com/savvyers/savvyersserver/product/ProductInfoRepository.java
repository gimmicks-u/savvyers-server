package com.savvyers.savvyersserver.product;

import com.savvyers.savvyersserver.product.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfo, Long> {
    
    // Basic CRUD operations are inherited from JpaRepository
    
    // Find by product kind state
    List<ProductInfo> findByPrdkindstate(String productKindState);
    Page<ProductInfo> findByPrdkindstate(String productKindState, Pageable pageable);
    
    // Find by product kind
    List<ProductInfo> findByPrdkind(String productKind);
    Page<ProductInfo> findByPrdkind(String productKind, Pageable pageable);
    
    // Find by product list name (containing search)
    List<ProductInfo> findByPrdlstNmContainingIgnoreCase(String productListName);
    Page<ProductInfo> findByPrdlstNmContainingIgnoreCase(String productListName, Pageable pageable);
    
    // Find by record number
    Optional<ProductInfo> findByRnum(String recordNumber);
    
    // Find by report number
    List<ProductInfo> findByReportNo(String reportNumber);
    Optional<ProductInfo> findByReportNoIgnoreCase(String reportNumber);
    
    // Find by product group
    List<ProductInfo> findByProductGb(String productGroup);
    Page<ProductInfo> findByProductGb(String productGroup, Pageable pageable);
    
    // Find products with images
    List<ProductInfo> findByImgurl1IsNotNull();
    List<ProductInfo> findByImgurl2IsNotNull();
    List<ProductInfo> findByImgurl1IsNotNullOrImgurl2IsNotNull();
    
    // Find products with allergy information
    List<ProductInfo> findByAllergyIsNotNull();
    @Query("SELECT p FROM ProductInfo p WHERE p.allergy LIKE CONCAT('%', :allergyInfo, '%')")
    List<ProductInfo> findByAllergyContainingIgnoreCase(@Param("allergyInfo") String allergyInfo);
    
    // Find by raw material containing specific ingredient
    @Query("SELECT p FROM ProductInfo p WHERE p.rawmtrl LIKE CONCAT('%', :ingredient, '%')")
    List<ProductInfo> findByRawmtrlContainingIgnoreCase(@Param("ingredient") String ingredient);
    
    // Find by manufacture information - disabled due to CLOB mapping issue
    // List<ProductInfo> findByManufactureContainingIgnoreCase(String manufactureInfo);
    
    // Count queries for statistics
    long countByPrdkind(String productKind);
    long countByProductGb(String productGroup);
    long countByAllergyIsNotNull();
}