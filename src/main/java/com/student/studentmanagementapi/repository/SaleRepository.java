package com.student.studentmanagementapi.repository;

import com.student.studentmanagementapi.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    Optional<Sale> findTopByOrderByIdDesc();

    Optional<Sale> findBySaleNumber(String saleNumber);
    @Query("SELECT COALESCE(SUM(s.totalAmount), 0) FROM Sale s")
    BigDecimal getTotalSaleAmount();

    @Query("SELECT COALESCE(SUM(s.totalAmount), 0) FROM Sale s WHERE s.saleDate = :saleDate")
    BigDecimal getTodaySaleAmount(LocalDate saleDate);
}