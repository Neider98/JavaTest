package com.auto.inventory.autoinventory.services;

import com.auto.inventory.autoinventory.models.entities.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    List<Product> findAllProducts(String orderBy);
    Optional<Product> findProductById(Long id);

    Product saveProduct(Product product);

    Optional<Product> updateProduct(Product product);

    Optional<Product> deleteProductById(Long id);

    boolean findProductByUser(Long productId, Long userId);
}
