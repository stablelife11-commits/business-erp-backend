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

import com.student.studentmanagementapi.dto.StockReportResponse;
import com.student.studentmanagementapi.repository.ProductRepository;
import com.student.studentmanagementapi.entity.Product;

import com.student.studentmanagementapi.dto.MonthlySalesResponse;
import java.util.ArrayList;
import com.student.studentmanagementapi.dto.TopSellingProductResponse;
import com.student.studentmanagementapi.dto.TopCustomerResponse;
import com.student.studentmanagementapi.dto.MonthlyPurchaseResponse;
import com.student.studentmanagementapi.dto.TopSupplierResponse;

@Service
public class ReportService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ProductRepository productRepository;

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

    public StockReportResponse getStockReport() {

        StockReportResponse response = new StockReportResponse();

        response.setTotalProducts(productRepository.count());

        response.setTotalStockQuantity(
                productRepository.getTotalStockQuantity()
        );

        response.setTotalStockValue(
                productRepository.getTotalStockValue()
        );

        return response;
    }

    public List<MonthlySalesResponse> getMonthlySalesReport() {

        List<Object[]> data = saleRepository.getMonthlySales();

        List<MonthlySalesResponse> response = new ArrayList<>();

        for (Object[] row : data) {

            MonthlySalesResponse item = new MonthlySalesResponse();

            item.setMonth(((Number) row[0]).intValue());
            item.setTotalSales((BigDecimal) row[1]);

            response.add(item);
        }

        return response;
    }
    public List<TopSellingProductResponse> getTopSellingProducts() {

        List<Object[]> data = saleRepository.getTopSellingProducts();

        List<TopSellingProductResponse> response = new ArrayList<>();

        for (Object[] row : data) {

            TopSellingProductResponse item = new TopSellingProductResponse();

            item.setProductName((String) row[0]);
            item.setTotalQuantity(((Number) row[1]).longValue());

            response.add(item);
        }

        return response;
    }
    public List<TopCustomerResponse> getTopCustomers() {

        List<Object[]> data = saleRepository.getTopCustomers();

        List<TopCustomerResponse> response = new ArrayList<>();

        for (Object[] row : data) {

            TopCustomerResponse item = new TopCustomerResponse();

            item.setCustomerName((String) row[0]);
            item.setTotalAmount((BigDecimal) row[1]);

            response.add(item);
        }

        return response;
    }
    public List<MonthlyPurchaseResponse> getMonthlyPurchaseReport() {

        List<Object[]> data = purchaseRepository.getMonthlyPurchases();

        List<MonthlyPurchaseResponse> response = new ArrayList<>();

        for (Object[] row : data) {

            MonthlyPurchaseResponse item = new MonthlyPurchaseResponse();

            item.setMonth(((Number) row[0]).intValue());
            item.setTotalPurchase(((Number) row[1]).doubleValue());

            response.add(item);
        }

        return response;
    }
    public List<TopSupplierResponse> getTopSuppliers() {

        List<Object[]> data = purchaseRepository.getTopSuppliers();

        List<TopSupplierResponse> response = new ArrayList<>();

        for (Object[] row : data) {

            TopSupplierResponse item = new TopSupplierResponse();

            item.setSupplierName((String) row[0]);
            item.setTotalPurchase(((Number) row[1]).doubleValue());

            response.add(item);
        }

        return response;
    }

}