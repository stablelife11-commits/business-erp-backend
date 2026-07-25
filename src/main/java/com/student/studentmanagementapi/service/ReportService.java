package com.student.studentmanagementapi.service;


import com.student.studentmanagementapi.dto.ReportResponse;
import com.student.studentmanagementapi.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.student.studentmanagementapi.entity.Sale;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.student.studentmanagementapi.dto.PurchaseReportResponse;
import com.student.studentmanagementapi.entity.Purchase;
import com.student.studentmanagementapi.repository.PurchaseRepository;

import com.student.studentmanagementapi.dto.ProfitReportResponse;

@Service
public class ReportService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    public ReportResponse getSalesReport(LocalDate fromDate, LocalDate toDate) {

        List<Sale> sales = saleRepository.findBySaleDateBetween(fromDate, toDate);

        ReportResponse response = new ReportResponse();

        response.setSales(sales);

        response.setTotalBills(sales.size());

        int totalQuantity = 0;
        BigDecimal totalSales = BigDecimal.ZERO;

        for (Sale sale : sales) {

            totalQuantity += sale.getQuantity();

            totalSales = totalSales.add(sale.getTotalAmount());
        }

        response.setTotalQuantity(totalQuantity);

        response.setTotalSales(totalSales);

        return response;
    }

    public PurchaseReportResponse getPurchaseReport(LocalDate fromDate, LocalDate toDate) {

        List<Purchase> purchases =
                purchaseRepository.findByPurchaseDateBetween(fromDate, toDate);

        PurchaseReportResponse response = new PurchaseReportResponse();

        response.setPurchases(purchases);

        response.setTotalBills(purchases.size());

        int totalQuantity = 0;
        Double totalPurchase = 0.0;

        for (Purchase purchase : purchases) {

            totalPurchase += purchase.getTotalAmount();

            purchase.getItems().forEach(item ->
                    response.setTotalQuantity(response.getTotalQuantity() + item.getQuantity()));
        }

        response.setTotalPurchase(totalPurchase);

        return response;
    }
    public ProfitReportResponse getProfitReport(LocalDate fromDate, LocalDate toDate) {

        List<Sale> sales = saleRepository.findBySaleDateBetween(fromDate, toDate);
        List<Purchase> purchases = purchaseRepository.findByPurchaseDateBetween(fromDate, toDate);

        BigDecimal totalSales = BigDecimal.ZERO;
        BigDecimal totalPurchaseCost = BigDecimal.ZERO;

        for (Sale sale : sales) {
            totalSales = totalSales.add(sale.getTotalAmount());
        }

        for (Purchase purchase : purchases) {
            totalPurchaseCost = totalPurchaseCost.add(
                    BigDecimal.valueOf(purchase.getTotalAmount())
            );
        }

        BigDecimal grossProfit = totalSales.subtract(totalPurchaseCost);

        Double profitPercentage = 0.0;

        if (totalPurchaseCost.compareTo(BigDecimal.ZERO) > 0) {
            profitPercentage = grossProfit
                    .multiply(BigDecimal.valueOf(100))
                    .divide(totalPurchaseCost, 2, java.math.RoundingMode.HALF_UP)
                    .doubleValue();
        }

        ProfitReportResponse response = new ProfitReportResponse();

        response.setTotalSales(totalSales);
        response.setTotalPurchaseCost(totalPurchaseCost);
        response.setGrossProfit(grossProfit);
        response.setProfitPercentage(profitPercentage);

        return response;
    }

}