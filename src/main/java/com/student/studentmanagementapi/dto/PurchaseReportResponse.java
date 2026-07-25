package com.student.studentmanagementapi.dto;

import com.student.studentmanagementapi.entity.Purchase;

import java.util.List;

public class PurchaseReportResponse {

    private long totalBills;
    private int totalQuantity;
    private Double totalPurchase;
    private List<Purchase> purchases;

    public PurchaseReportResponse() {
    }

    public long getTotalBills() {
        return totalBills;
    }

    public void setTotalBills(long totalBills) {
        this.totalBills = totalBills;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Double getTotalPurchase() {
        return totalPurchase;
    }

    public void setTotalPurchase(Double totalPurchase) {
        this.totalPurchase = totalPurchase;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }
}