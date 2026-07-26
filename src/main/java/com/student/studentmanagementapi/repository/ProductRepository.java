package com.student.studentmanagementapi.repository;

import com.student.studentmanagementapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByProductCode(String productCode);

    Optional<Product> findByProductCode(String productCode);

    List<Product> findByProductNameContainingIgnoreCase(String productName);

    List<Product> findByBrandContainingIgnoreCase(String brand);

    List<Product> findByCurrentStockLessThanEqual(Integer stock);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.currentStock <= 10")
    long getLowStockProducts();

    @Query("SELECT COALESCE(SUM(p.currentStock), 0) FROM Product p")
    Integer getTotalStockQuantity();

    @Query("SELECT COALESCE(SUM(p.currentStock * p.purchasePrice), 0) FROM Product p")
    java.math.BigDecimal getTotalStockValue();
}