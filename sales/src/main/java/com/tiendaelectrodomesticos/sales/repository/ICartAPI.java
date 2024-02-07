package com.tiendaelectrodomesticos.sales.repository;

import com.tiendaelectrodomesticos.sales.dto.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "carts-service")
public interface ICartAPI {
    @GetMapping("/cart/{id}")
    public CartDTO getCart(@PathVariable Long id);
}
