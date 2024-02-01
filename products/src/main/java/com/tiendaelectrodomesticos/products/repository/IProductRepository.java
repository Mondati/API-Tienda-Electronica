package com.tiendaelectrodomesticos.products.repository;

import com.tiendaelectrodomesticos.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByCode(int code);
}
