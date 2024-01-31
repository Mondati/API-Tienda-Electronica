package com.tiendaelectrodomesticos.products.service;

import com.tiendaelectrodomesticos.products.dto.ProductDTO;
import com.tiendaelectrodomesticos.products.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    public void saveProduct(Product product);

    public Optional<Product> findProduct(Integer code);

    public List<Product> findAll();

    public void editProduct(Product product, Long id);
}
