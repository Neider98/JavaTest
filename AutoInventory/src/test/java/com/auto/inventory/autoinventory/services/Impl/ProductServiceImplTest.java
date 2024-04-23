package com.auto.inventory.autoinventory.services.Impl;
import com.auto.inventory.autoinventory.models.entities.Product;
import com.auto.inventory.autoinventory.repositories.IProductRepository;
import com.auto.inventory.autoinventory.services.Impl.ProductServiceImpl;
import com.auto.inventory.autoinventory.util.exceptions.IdNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private IProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllProducts() {
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productRepository.findAll((String) null)).thenReturn(products);

        // Act
        List<Product> result = productService.findAllProducts(null);

        // Assert
        assertEquals(products.size(), result.size());
        verify(productRepository, times(1)).findAll((String) null);
    }

    @Test
    void testFindProductByIdFound() {
        long productId = 1L;
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.findProductById(productId);

        assertTrue(result.isPresent());
        assertEquals(product, result.get());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testFindProductByIdNotFound() {
        long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> productService.findProductById(productId));
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testSaveProduct() {
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.saveProduct(product);

        assertEquals(product, result);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testUpdateProductFound() {
        long productId = 1L;
        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(updatedProduct));
        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

        Optional<Product> result = productService.updateProduct(updatedProduct);

        assertTrue(result.isPresent());
        assertEquals(updatedProduct, result.get());
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(updatedProduct);
    }

    @Test
    void testUpdateProductNotFound() {
        long productId = 1L;
        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> productService.updateProduct(updatedProduct));
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(updatedProduct);
    }

    @Test
    void testDeleteProductByIdFound() {
        long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.deleteProductById(productId);

        assertTrue(result.isPresent());
        assertEquals(product, result.get());
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testDeleteProductByIdNotFound() {
        long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(IdNotFoundException.class, () -> productService.deleteProductById(productId));
    }


    @Test
    void testFindProductByUser() {
        long productId = 1L;
        long userId = 1L;
        Product product = new Product();
        when(productRepository.findByIdAndRegisteredBy(productId, userId)).thenReturn(Optional.of(product));

        boolean result = productService.findProductByUser(productId, userId);

        assertTrue(result);
        verify(productRepository, times(1)).findByIdAndRegisteredBy(productId, userId);
    }

}
