package com.auto.inventory.autoinventory.facades;

import com.auto.inventory.autoinventory.mappers.ProductMapper;
import com.auto.inventory.autoinventory.models.dtos.ProductDTO;
import com.auto.inventory.autoinventory.models.entities.Product;
import com.auto.inventory.autoinventory.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductFacade {

    private final IProductService productService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductFacade(IProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    public List<ProductDTO> findAllProducts(String orderBy) {
        return productMapper.productsToProductsDTO(productService.findAllProducts(orderBy));
    }

    public Optional<ProductDTO> findProductById(Long id) {
        return productMapper.mapToDtoOptional(productService.findProductById(id));
    }

    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = productMapper.productDTOToProduct(productDTO);
        return productMapper.productToProductDTO(productService.saveProduct(product));
    }

    public Optional<ProductDTO> updateProduct(ProductDTO productDTO){
        Product product = productMapper.productDTOToProduct(productDTO);
        return productMapper.mapToDtoOptional(productService.updateProduct(product));
    }

    public Optional<ProductDTO> deleteProductById(Long id) {
        return productMapper.mapToDtoOptional(productService.deleteProductById(id));
    }

    public boolean findProductByUser(Long productId, Long userId) {
        return productService.findProductByUser(productId, userId);
    }

}
