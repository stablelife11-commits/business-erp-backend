package com.student.studentmanagementapi.service;

import com.student.studentmanagementapi.entity.Product;
import com.student.studentmanagementapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Add Product
    public Product addProduct(Product product) {

        if (productRepository.existsByProductCode(product.getProductCode())) {
            throw new RuntimeException("Product code already exists");
        }

        return productRepository.save(product);
    }

    // Get All Products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get Product By ID
    public Product getProductById(Long id) {

        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // Update Product
    public Product updateProduct(Long id, Product updatedProduct) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check duplicate product code
        if (!product.getProductCode().equals(updatedProduct.getProductCode())
                && productRepository.existsByProductCode(updatedProduct.getProductCode())) {
            throw new RuntimeException("Product code already exists");
        }

        product.setProductName(updatedProduct.getProductName());
        product.setProductCode(updatedProduct.getProductCode());
        product.setBrand(updatedProduct.getBrand());
        product.setCategory(updatedProduct.getCategory());
        product.setSize(updatedProduct.getSize());
        product.setColor(updatedProduct.getColor());
        product.setPurchasePrice(updatedProduct.getPurchasePrice());
        product.setSalePrice(updatedProduct.getSalePrice());
        product.setOpeningStock(updatedProduct.getOpeningStock());
        product.setCurrentStock(updatedProduct.getCurrentStock());
        product.setStatus(updatedProduct.getStatus());

        return productRepository.save(product);
    }

    // Delete Product
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.delete(product);
    }

    // Search By Product Code
    public Product getProductByCode(String productCode) {

        return productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // Search By Product Name
    public List<Product> searchByProductName(String productName) {

        return productRepository.findByProductNameContainingIgnoreCase(productName);
    }

    // Search By Brand
    public List<Product> searchByBrand(String brand) {

        return productRepository.findByBrandContainingIgnoreCase(brand);
    }
}