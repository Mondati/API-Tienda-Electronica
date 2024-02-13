package com.tiendaelectrodomesticos.products.controller;

import com.tiendaelectrodomesticos.products.dto.ProductDTO;
import com.tiendaelectrodomesticos.products.exception.ResourceNotFoundException;
import com.tiendaelectrodomesticos.products.model.Product;
import com.tiendaelectrodomesticos.products.service.IProductService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/product")
public class ProductController {


    private IProductService productServ;

    @Autowired
    public ProductController(IProductService productServ) {
        this.productServ = productServ;
    }


    @PostMapping("/create")
    public ResponseEntity<String> saveProduct(@Valid @RequestBody Product product) {
        productServ.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Producto creado exitosamente");
    }


    @GetMapping("/{code}")
    public ResponseEntity<ProductDTO> findProductByCode(@PathVariable Integer code) {
        try {
            ProductDTO productDTO = productServ.findProduct(code);
            return ResponseEntity.ok(productDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
