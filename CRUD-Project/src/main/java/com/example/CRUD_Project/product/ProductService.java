package com.example.CRUD_Project.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//
//

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

//



    public Product createProduct(Product product) {
        if (product.getId() != null && productRepository.existsById(product.getId())) {
            throw new RuntimeException("Product with this ID already exists");
        }
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = getProductById(id);
        if (product != null) {
            if (productDetails.getName() != null)
                product.setName(productDetails.getName());
            if (productDetails.getDescription() != null)
                product.setDescription(productDetails.getDescription());
            if (productDetails.getPrice() != null)
                product.setPrice(productDetails.getPrice());
            if (productDetails.getQuantity() != null)
                product.setQuantity(productDetails.getQuantity());
            return productRepository.save(product);
        }
        throw new RuntimeException("Product not found");
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
