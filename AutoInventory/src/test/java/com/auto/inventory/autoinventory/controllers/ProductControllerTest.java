package com.auto.inventory.autoinventory.controllers;

import com.auto.inventory.autoinventory.facades.ProductFacade;
import com.auto.inventory.autoinventory.models.dtos.ProductDTO;
import jakarta.validation.Valid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductFacade productFacade;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProducts() {

        List<ProductDTO> mockProducts = Arrays.asList(
                new ProductDTO("Product 1", 10, new Date()),
                new ProductDTO("Product 2", 20, new Date())
        );
        when(productFacade.findAllProducts(null)).thenReturn(mockProducts);


        List<ProductDTO> response = productController.getProducts(null);

        assertEquals(mockProducts, response);
        verify(productFacade, times(1)).findAllProducts(null);
    }

    @Test
    void testGetProduct() {
        long productId = 1L;
        ProductDTO mockProduct = new ProductDTO("Product 1", 10, new Date());
                when(productFacade.findProductById(productId)).thenReturn(Optional.of(mockProduct));

        ResponseEntity<ProductDTO> response = productController.getProduct(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProduct, response.getBody());
        verify(productFacade, times(1)).findProductById(productId);
    }

    @Test
    void testCreateProduct() {
        ProductDTO newProduct = new ProductDTO("New Product", 10, new Date());
        when(productFacade.saveProduct(newProduct)).thenReturn(newProduct);

        BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "product");
        ResponseEntity<?> response = productController.createProduct(newProduct, bindingResult);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody() instanceof ProductDTO);
        assertEquals(newProduct, response.getBody());
        verify(productFacade, times(1)).saveProduct(newProduct);
    }


    @Test
    void testUpdateProduct() {
        long productId = 1L;
        ProductDTO updatedProduct = new ProductDTO("Updated Product", 20, new Date());
        when(productFacade.updateProduct(updatedProduct)).thenReturn(Optional.of(updatedProduct));

        BindingResult bindingResult = new MapBindingResult(new HashMap<>(), "product");

        ResponseEntity<?> response = productController.updateProduct(updatedProduct, bindingResult, productId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody() instanceof ProductDTO);
        assertEquals(updatedProduct, response.getBody());
        verify(productFacade, times(1)).updateProduct(updatedProduct);
    }


    @Test
    void testDeleteProduct() {
        long productId = 1L;
        long userId = 1L;
        ProductDTO mockProduct = new ProductDTO("Product 1", 10, new Date());
        when(productFacade.findProductByUser(productId, userId)).thenReturn(true);
        when(productFacade.deleteProductById(productId)).thenReturn(Optional.of(mockProduct));

        ResponseEntity<ProductDTO> response = productController.deleteProduct(productId, userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockProduct, response.getBody());
        verify(productFacade, times(1)).findProductByUser(productId, userId);
        verify(productFacade, times(1)).deleteProductById(productId);
    }

    @Test
    void testDeleteProductUnauthorized() {

        long productId = 1L;
        long userId = 1L;
        when(productFacade.findProductByUser(productId, userId)).thenReturn(false);

        ResponseEntity<?> response = productController.deleteProduct(productId, userId);
        response.getStatusCode();
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        verify(productFacade, times(1)).findProductByUser(productId, userId);
        verify(productFacade, never()).deleteProductById(productId);
    }


    @Test
    void testValidation() {

        BindingResult mockBindingResult = mock(BindingResult.class);
        when(mockBindingResult.hasFieldErrors()).thenReturn(true);

        ResponseEntity<?> response = productController.validation(mockBindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
