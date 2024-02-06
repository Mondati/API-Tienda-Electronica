package com.tiendaelectrodomesticos.cart.repository;

import com.tiendaelectrodomesticos.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartRepository extends JpaRepository<Cart, Long> {

}
