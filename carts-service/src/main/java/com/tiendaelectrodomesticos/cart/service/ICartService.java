package com.tiendaelectrodomesticos.cart.service;

import com.tiendaelectrodomesticos.cart.model.Cart;

import java.util.Optional;


public interface ICartService {

    public void saveCart(Cart cart);

    public Optional<Cart> getCart(Long id);

    public void deleteCart(Long id);

    public void addToCart(Long cartId, Integer productCode);

    public void removeFromCart(Long cartId, Integer code);


}
