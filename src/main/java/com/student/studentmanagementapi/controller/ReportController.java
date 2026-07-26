package com.student.studentmanagementapi.controller;

import com.student.studentmanagementapi.dto.ProfitReportResponse;
import com.student.studentmanagementapi.dto.PurchaseReportResponse;

import com.student.studentmanagementapi.dto.ReportResponse;
import com.student.studentmanagementapi.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDate;
import com.student.studentmanagementapi.dto.StockReportResponse;
import com.student.studentmanagementapi.dto.MonthlySalesResponse;
import com.student.studentmanagementapi.dto.TopSellingProductResponse;
import com.student.studentmanagementapi.dto.TopCustomerResponse;
import com.student.studentmanagementapi.dto.MonthlyPurchaseResponse;
import com.student.studentmanagementapi.dto.TopSupplierResponse;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/sales")
    public ReportResponse getSalesReport(
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate) {

        return reportService.getSalesReport(fromDate, toDate);
    }

    @GetMapping("/purchases")
    public PurchaseReportResponse getPurchaseReport(
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate) {

        return reportService.getPurchaseReport(fromDate, toDate);
    }

    @GetMapping("/profit")
    public ProfitReportResponse getProfitReport(
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate) {

        return reportService.getProfitReport(fromDate, toDate);
    }
    @GetMapping("/stock")
    public StockReportResponse getStockReport() {

        return reportService.getStockReport();
    }

    @GetMapping("/monthly-sales")
    public List<MonthlySalesResponse> getMonthlySalesReport() {

        return reportService.getMonthlySalesReport();
    }
    @GetMapping("/top-selling-products")
    public List<TopSellingProductResponse> getTopSellingProducts() {

        return reportService.getTopSellingProducts();
    }

    @GetMapping("/top-customers")
    public List<TopCustomerResponse> getTopCustomers() {

        return reportService.getTopCustomers();
    }

    @GetMapping("/monthly-purchases")
    public List<MonthlyPurchaseResponse> getMonthlyPurchaseReport() {

        return reportService.getMonthlyPurchaseReport();
    }

    @GetMapping("/top-suppliers")
    public List<TopSupplierResponse> getTopSuppliers() {

        return reportService.getTopSuppliers();
    }
}