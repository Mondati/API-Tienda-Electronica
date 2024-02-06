package com.tiendaelectrodomesticos.products.controller;

import com.tiendaelectrodomesticos.products.dto.ProductDTO;
import com.tiendaelectrodomesticos.products.exception.ResourceNotFoundException;
import com.tiendaelectrodomesticos.products.model.Product;
import com.tiendaelectrodomesticos.products.service.IProductService;
import jakarta.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productServ;

    private static final Logger LOGGER = Logger.getLogger(ProductController.class);

    @PostMapping("/create")
    public ResponseEntity<String> saveProduct(@Valid @RequestBody Product product) {
        productServ.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Producto creado exitosamente");
    }

    @GetMapping("/{code}")
    public ResponseEntity<Optional<ProductDTO>> findProductByCode(@PathVariable Integer code) {
        Optional<ProductDTO> productDTO = productServ.findProduct(code);
        if (productDTO.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(productDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @GetMapping("/all-products")
    public ResponseEntity<List<Product>> findAllProducts() {
        List<Product> productList = productServ.findAll();
        if (!productList.isEmpty()) {
            return ResponseEntity.ok(productList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            productServ.editProduct(product, id);
            return ResponseEntity.ok("Producto editado correctamente");
        } catch (ResourceNotFoundException rnfe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rnfe.getMessage());
        }
    }


}
