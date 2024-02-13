package com.tiendaelectrodomesticos.products.service;

import com.tiendaelectrodomesticos.products.dto.ProductDTO;
import com.tiendaelectrodomesticos.products.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    public void saveProduct(Product product);

    public ProductDTO getProductDTO(Integer code);

    public List<Product> findAll();

    public void editProduct(Product product, Long id);

    public void deleteProduct(Long id);
}
