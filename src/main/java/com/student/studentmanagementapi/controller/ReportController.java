package com.student.studentmanagementapi.controller;

import com.student.studentmanagementapi.dto.ProfitReportResponse;
import com.student.studentmanagementapi.dto.PurchaseReportResponse;

import com.student.studentmanagementapi.dto.ReportResponse;
import com.student.studentmanagementapi.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
}