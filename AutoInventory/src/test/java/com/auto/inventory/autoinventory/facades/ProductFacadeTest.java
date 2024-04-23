package com.auto.inventory.autoinventory.facades;

import com.auto.inventory.autoinventory.facades.ProductFacade;
import com.auto.inventory.autoinventory.mappers.ProductMapper;
import com.auto.inventory.autoinventory.models.dtos.ProductDTO;
import com.auto.inventory.autoinventory.models.entities.Product;
import com.auto.inventory.autoinventory.services.IProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ProductFacadeTest {

    @Mock
    private IProductService productService;

    @Mock
    private ProductMapper productMapper;

    private ProductFacade productFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productFacade = new ProductFacade(productService, productMapper);
    }

    @Test
    void testFindAllProducts() {
        List<Product> products = Collections.singletonList(new Product());
        when(productService.findAllProducts(null)).thenReturn(products);
        when(productMapper.productsToProductsDTO(products)).thenReturn(Collections.singletonList(new ProductDTO()));

        List<ProductDTO> result = productFacade.findAllProducts(null);

        assertEquals(1, result.size());
    }

    @Test
    void testFindProductById() {
        Long productId = 1L;
        Product product = new Product();
        when(productService.findProductById(productId)).thenReturn(Optional.of(product));
        when(productMapper.mapToDtoOptional(Optional.of(product))).thenReturn(Optional.of(new ProductDTO()));

        Optional<ProductDTO> result = productFacade.findProductById(productId);

        assertTrue(result.isPresent());
    }

    @Test
    void testSaveProduct() {
        ProductDTO productDTO = new ProductDTO();
        Product product = new Product();
        when(productMapper.productDTOToProduct(productDTO)).thenReturn(product);
        when(productMapper.productToProductDTO(product)).thenReturn(productDTO);
        when(productService.saveProduct(product)).thenReturn(product);

        ProductDTO result = productFacade.saveProduct(productDTO);

        assertEquals(productDTO, result);
    }

    @Test
    void testUpdateProduct() {
        ProductDTO productDTO = new ProductDTO();
        Product product = new Product();
        when(productMapper.productDTOToProduct(productDTO)).thenReturn(product);
        when(productService.updateProduct(product)).thenReturn(Optional.of(product));
        when(productMapper.mapToDtoOptional(Optional.of(product))).thenReturn(Optional.of(productDTO));

        Optional<ProductDTO> result = productFacade.updateProduct(productDTO);

        assertTrue(result.isPresent());
    }

    @Test
    void testDeleteProductById() {
        Long productId = 1L;
        Product product = new Product();
        when(productService.deleteProductById(productId)).thenReturn(Optional.of(product));
        when(productMapper.mapToDtoOptional(Optional.of(product))).thenReturn(Optional.of(new ProductDTO()));

        Optional<ProductDTO> result = productFacade.deleteProductById(productId);

        assertTrue(result.isPresent());
    }

    @Test
    void testFindProductByUser() {
        Long productId = 1L;
        Long userId = 1L;
        when(productService.findProductByUser(productId, userId)).thenReturn(true);

        boolean result = productFacade.findProductByUser(productId, userId);

        assertTrue(result);
    }
}
