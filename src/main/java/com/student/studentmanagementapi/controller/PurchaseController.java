package com.student.studentmanagementapi.controller;

import com.student.studentmanagementapi.dto.PurchaseRequest;
import com.student.studentmanagementapi.dto.PurchaseResponse;
import com.student.studentmanagementapi.service.PurchaseService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {


    @Autowired
    private PurchaseService purchaseService;


    @PostMapping
    public PurchaseResponse addPurchase(
            @Valid @RequestBody PurchaseRequest request) {

        return purchaseService.addPurchase(request);
    }

}