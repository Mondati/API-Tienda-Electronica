package com.tiendaelectrodomesticos.cart.controller;

import com.tiendaelectrodomesticos.cart.exception.ResourceNotFoundException;
import com.tiendaelectrodomesticos.cart.model.Cart;
import com.tiendaelectrodomesticos.cart.service.ICartService;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ICartService cartService;

    private final static Logger LOGGER = Logger.getLogger(String.valueOf(CartController.class));

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCart(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Cart> cart = cartService.findCart(id);
        if (cart.isPresent()) {
            LOGGER.info("Buscando carrito por ID con exito");
            return ResponseEntity.ok(cart.get());
        } else {
            throw new ResourceNotFoundException("No se encontro carrito con id: " + id);
        }
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Cart> cart = cartService.findCart(id);
        if (cart.isPresent()) {
            cartService.deleteCart(id);
            LOGGER.info("Se elimino correctamente");
            return ResponseEntity.ok("Carrito eliminado con exito: " + id);
        } else {
            LOGGER.info("No se pudo eliminar carrito con id: " + id);
            throw new ResourceNotFoundException("No existe el id asociado a un carrito en la base de datos " + id);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> saveCart() {
        Cart cart = new Cart();
        cart.setTotalPrice(0.0);
        cart.setListCodeProducts(new ArrayList<>());
        cartService.createCart(cart);
        LOGGER.info("Guardando carrito con exito " + cart.getId());
        return ResponseEntity.ok().body("Carrito creado con exito " + cart.getId());
    }

    @PostMapping("/{cartId}/add/{productCode}")
    public ResponseEntity<String> addToCart(@PathVariable Long cartId, @PathVariable Integer productCode) {
        try {
            cartService.addToCart(cartId, productCode);
            LOGGER.info("Producto agregado al carrito correctamente.");
            return ResponseEntity.ok("Producto agregado al carrito correctamente. " + productCode);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{cartId}/remove/{productCode}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long cartId, @PathVariable Integer productCode) {
        try {
            cartService.removeFromCart(cartId, productCode);
            return ResponseEntity.ok("Producto eliminado del carrito correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}

