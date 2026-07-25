package com.student.studentmanagementapi.repository;

import com.student.studentmanagementapi.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    Optional<Sale> findTopByOrderByIdDesc();

    Optional<Sale> findBySaleNumber(String saleNumber);
}