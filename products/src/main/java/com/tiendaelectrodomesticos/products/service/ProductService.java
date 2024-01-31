package com.tiendaelectrodomesticos.products.service;

import com.tiendaelectrodomesticos.products.model.Product;
import com.tiendaelectrodomesticos.products.repository.IProductRepository;
import com.tiendaelectrodomesticos.products.validation.IValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository productRepo;

    @Autowired
    private IValidate<String> stringValidate;

    @Autowired
    private IValidate<Integer> integerValidate;

    private final static Logger LOGGER = Logger.getLogger(String.valueOf(ProductService.class));

    @Override
    public void saveProduct(Product product) {
        stringValidate.validate(product.getName());
        LOGGER.info("Guardando producto");
        productRepo.save(product);
    }

    @Override
    public Optional<Product> findProduct(Integer code) {
        integerValidate.validate(code);
        LOGGER.info("Buscando producto por codigo");
        return productRepo.findByCode(code);
    }

    @Override
    public List<Product> findAll() {
        LOGGER.info("Listando todos los productos...");
        List<Product> productList = productRepo.findAll();
        if (productList.isEmpty()) {
            LOGGER.info("Lista vacia sin productos");
            return Collections.emptyList();
        }
        return productList;
    }

    @Override
    public void editProduct(Product product, Long id) {
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
            throw new NoSuchElementException("Producto no encontrado con ID: " + id);
        }
    }

}
