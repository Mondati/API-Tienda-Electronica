package com.tiendaelectrodomesticos.products.service;

import com.tiendaelectrodomesticos.products.dto.ProductDTO;
import com.tiendaelectrodomesticos.products.exception.ResourceNotFoundException;
import com.tiendaelectrodomesticos.products.model.Product;
import com.tiendaelectrodomesticos.products.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class ProductService implements IProductService {

    private IProductRepository productRepo;

    @Autowired
    public ProductService(IProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(ProductService.class));

    @Override
    public void saveProduct(Product product) {
        LOGGER.info("Guardando producto");
        productRepo.save(product);
    }


    @Override
    public ProductDTO findProduct(Integer code) {
        LOGGER.info("Buscando producto por codigo");
        Optional<Product> optionalProduct = productRepo.findByCode(code);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            ProductDTO productDTO = new ProductDTO();
            productDTO.setCode(product.getCode());
            productDTO.setName(product.getName());
            productDTO.setBrand(product.getBrand());
            productDTO.setSinglePrice(product.getSinglePrice());
            LOGGER.info("Producto " + productDTO);
            return productDTO;
        } else {
            LOGGER.info("Producto con codigo: " + code + " no encontrado");
            throw new ResourceNotFoundException("Producto con codigo: " + code + " no encontrado");
        }
    }


    @Override
    public List<Product> findAll() {
        LOGGER.info("Listando todos los productos...");
        List<Product> productList = productRepo.findAll();
        if (productList.isEmpty()) {
            LOGGER.info("Lista vacía sin productos");
            return Collections.emptyList();
        } else {
            return productList;
        }
    }


    @Override
    public void editProduct(Product product, Long id) throws ResourceNotFoundException {
        LOGGER.info("Buscando producto por id");
        Optional<Product> searchedProduct = productRepo.findById(id);

        if (searchedProduct.isPresent()) {
            LOGGER.info("Actualizando producto");
            Product existingProduct = searchedProduct.get();
            existingProduct.setCode(product.getCode());
            existingProduct.setName(product.getName());
            existingProduct.setBrand(product.getBrand());
            existingProduct.setSinglePrice(product.getSinglePrice());
            productRepo.save(existingProduct);
            LOGGER.info("Producto guardado " + existingProduct);
        } else {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + id);
        }
    }

}
