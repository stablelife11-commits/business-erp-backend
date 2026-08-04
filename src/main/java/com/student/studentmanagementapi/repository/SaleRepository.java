package com.student.studentmanagementapi.repository;

import com.student.studentmanagementapi.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    Optional<Sale> findTopByOrderByIdDesc();

    Optional<Sale> findBySaleNumber(String saleNumber);
    @Query("SELECT COALESCE(SUM(s.totalAmount), 0) FROM Sale s")
    BigDecimal getTotalSaleAmount();

    long countBySaleDate(LocalDate saleDate);

    List<Sale> findBySaleDateBetween(LocalDate fromDate, LocalDate toDate);

    @Query("SELECT COALESCE(SUM(s.totalAmount), 0) FROM Sale s WHERE s.saleDate = :saleDate")
    BigDecimal getTodaySaleAmount(LocalDate saleDate);



    @Query("""
SELECT MONTH(s.saleDate), COALESCE(SUM(s.totalAmount), 0)
FROM Sale s
GROUP BY MONTH(s.saleDate)
ORDER BY MONTH(s.saleDate)
""")
    List<Object[]> getMonthlySales();

    @Query("""
SELECT s.productName, SUM(s.quantity)
FROM Sale s
GROUP BY s.productName
ORDER BY SUM(s.quantity) DESC
""")
    List<Object[]> getTopSellingProducts();

    @Query("""
SELECT s.customerName, SUM(s.totalAmount)
FROM Sale s
GROUP BY s.customerName
ORDER BY SUM(s.totalAmount) DESC
""")
    List<Object[]> getTopCustomers();

    @Query("""
SELECT s.customerName,
       s.customerMobile,
       COUNT(s.id),
       COALESCE(SUM(s.quantity), 0),
       COALESCE(SUM(s.totalAmount), 0)
FROM Sale s
GROUP BY s.customerName, s.customerMobile
ORDER BY SUM(s.totalAmount) DESC
""")
    List<Object[]> getCustomerReport();
}