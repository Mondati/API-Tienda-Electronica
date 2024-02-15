package com.tiendaelectrodomesticos.cart.controller;

import com.tiendaelectrodomesticos.cart.exception.ProductNotFoundException;
import com.tiendaelectrodomesticos.cart.exception.ResourceNotFoundException;
import com.tiendaelectrodomesticos.cart.model.Cart;
import com.tiendaelectrodomesticos.cart.service.ICartService;

import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Logger;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cart")
public class CartController {


    private final ICartService cartService;

    @Autowired
    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(CartController.class));

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<String> saveCart() {
        Cart cart = new Cart();
        cart.setTotalPrice(0.0);
        cart.setListCodeProducts(new ArrayList<>());
        cartService.saveCart(cart);
        LOGGER.info("Guardando carrito con exito " + cart.getId());
        return ResponseEntity.ok().body("Carrito creado con exito " + cart.getId());
    }


    @PostMapping("/{cartId}/add/{productCode}")
    @Transactional
    public ResponseEntity<String> addToCart(@PathVariable Long cartId, @PathVariable Integer productCode) {
        try {
            cartService.addToCart(cartId, productCode);
            LOGGER.info("Producto agregado al carrito correctamente.");
            return ResponseEntity.ok("Producto agregado al carrito correctamente. " + productCode);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo agregar el producto al carrito");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Cart> findCart(@PathVariable Long id) {
        try {
            Optional<Cart> optionalCart = cartService.getCart(id);
            LOGGER.info("Buscando carrito por ID con exito");
            return ResponseEntity.ok().body(optionalCart.get());
        } catch (ResourceNotFoundException e) {
            LOGGER.warning("Error al buscar el carrito por ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<String> deleteCart(@PathVariable Long id) {
        try {
            cartService.deleteCart(id);
            LOGGER.info("Se elimino correctamente");
            return ResponseEntity.ok("Carrito eliminado con exito: " + id);
        } catch (ResourceNotFoundException e) {
            LOGGER.info("No se pudo eliminar carrito con id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo eliminar carrito con id: " + id);
        }
    }


    @DeleteMapping("/{cartId}/remove/{productCode}")
    @Transactional
    public ResponseEntity<String> removeFromCart(@PathVariable Long cartId, @PathVariable Integer productCode) {
        try {
            cartService.removeFromCart(cartId, productCode);
            return ResponseEntity.ok("Producto eliminado del carrito correctamente.");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo eliminar del carrito: " + cartId + " el producto: " + productCode + ". El producto no está presente en el carrito.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo encontrar el carrito con ID: " + cartId);
        }
    }


}

