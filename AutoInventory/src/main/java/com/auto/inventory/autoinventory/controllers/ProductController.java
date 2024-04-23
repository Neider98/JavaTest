package com.auto.inventory.autoinventory.controllers;

import com.auto.inventory.autoinventory.facades.ProductFacade;
import com.auto.inventory.autoinventory.models.dtos.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {


    private final ProductFacade productFacade;

    @Autowired
    public ProductController(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @GetMapping("/products")
    public List<ProductDTO> getProducts(@RequestParam(required = false) String orderBy) {
        return productFacade.findAllProducts(orderBy);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        Optional<ProductDTO> productOptional = productFacade.findProductById(id);
        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO product, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productFacade.saveProduct(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @Valid @RequestBody ProductDTO product,
            BindingResult result,
            @PathVariable Long id) {

        if (result.hasFieldErrors()) {
            return validation(result);
        }
        Optional<ProductDTO> productOptional = productFacade.updateProduct(product);
        if (productOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long id,
                                                    @RequestParam Long userId) {

        boolean userCanDeleteProduct =
                productFacade.findProductByUser(id, userId);
        if (userCanDeleteProduct) {
            ProductDTO product = new ProductDTO();
            product.setId(id);
            Optional<ProductDTO> productOptional =
                    productFacade.deleteProductById(product.getId());
            if (productOptional.isPresent()) {
                return ResponseEntity.ok(productOptional.orElseThrow());
            }

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> errors.put(error.getField(),
        "El campo: " + error.getField() + " " + error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }
}
