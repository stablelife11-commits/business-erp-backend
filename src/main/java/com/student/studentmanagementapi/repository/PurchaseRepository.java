package com.student.studentmanagementapi.repository;

import com.student.studentmanagementapi.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.time.LocalDate;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    Optional<Purchase> findByPurchaseNumber(String purchaseNumber);

    boolean existsByPurchaseNumber(String purchaseNumber);

    @Query("SELECT COALESCE(SUM(p.totalAmount), 0) FROM Purchase p")
    BigDecimal getTotalPurchaseAmount();

    @Query("SELECT COALESCE(SUM(p.totalAmount), 0) FROM Purchase p WHERE p.purchaseDate = :purchaseDate")
    Double getTodayPurchaseAmount(LocalDate purchaseDate);
}