package com.auto.inventory.autoinventory.services.Impl;

import com.auto.inventory.autoinventory.models.entities.Product;
import com.auto.inventory.autoinventory.repositories.IProductRepository;
import com.auto.inventory.autoinventory.services.IProductService;
import com.auto.inventory.autoinventory.util.exceptions.IdNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;

    public ProductServiceImpl(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAllProducts(String orderBy) {
        return productRepository.findAll(orderBy);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        product.orElseThrow(() -> new IdNotFoundException("products"));
        return product;
    }

    @Transactional
    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    @Override
    public Optional<Product> updateProduct(Product product) {
        Optional<Product> productOptional =
                productRepository.findById(product.getId());
        productOptional.orElseThrow(() -> new IdNotFoundException("products"));
        productOptional.ifPresent(productDb -> productRepository.save(product));
        return productOptional;
    }

    @Transactional
    @Override
    public Optional<Product> deleteProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        productOptional.orElseThrow(() -> new IdNotFoundException("products"));
        productOptional.ifPresent(product -> productRepository.deleteById(id));
        return productOptional;
    }

    @Override
    public boolean findProductByUser(Long productId, Long userId) {
        Optional<Product> productOptional =
                productRepository.findByIdAndRegisteredBy(productId,
                userId);
        return productOptional.isPresent();
    }
}
