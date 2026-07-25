package com.student.studentmanagementapi.service;

import com.student.studentmanagementapi.entity.Product;
import com.student.studentmanagementapi.repository.ProductRepository;
import com.student.studentmanagementapi.entity.Sale;
import com.student.studentmanagementapi.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private ProductRepository productRepository;

    public Sale addSale(Sale sale) {

        // Generate Sale Number
        Optional<Sale> lastSale = saleRepository.findTopByOrderByIdDesc();

        if (lastSale.isPresent()) {
            String lastNumber = lastSale.get().getSaleNumber();
            int number = Integer.parseInt(lastNumber.substring(4));
            sale.setSaleNumber(String.format("SAL-%06d", number + 1));
        } else {
            sale.setSaleNumber("SAL-000001");
        }

        // Calculate Total Amount
        BigDecimal total = sale.getPrice().multiply(BigDecimal.valueOf(sale.getQuantity()));
        sale.setTotalAmount(total);

        // Find Product
        Product product = productRepository.findByProductNameContainingIgnoreCase(
                        sale.getProductName()
                ).stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found"));

// Update Stock (Negative Allowed)
        product.setCurrentStock(
                product.getCurrentStock() - sale.getQuantity()
        );

// Update Latest Sale Price
        product.setSalePrice(sale.getPrice());

        productRepository.save(product);

        return saleRepository.save(sale);
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Optional<Sale> getSaleById(Long id) {
        return saleRepository.findById(id);
    }

    public Optional<Sale> getSaleBySaleNumber(String saleNumber) {
        return saleRepository.findBySaleNumber(saleNumber);
    }

    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }
}