package com.tiendaelectrodomesticos.cart.repository;

import com.tiendaelectrodomesticos.cart.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient(name = "products-service")
public interface IProductAPI {
    @GetMapping("/product/{code}")
    ProductDTO getProduct(@PathVariable Integer code);
}
