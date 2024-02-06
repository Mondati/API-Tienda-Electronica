package com.tiendaelectrodomesticos.cart.service;

import com.tiendaelectrodomesticos.cart.dto.ProductDTO;
import com.tiendaelectrodomesticos.cart.model.Cart;
import com.tiendaelectrodomesticos.cart.repository.ICartRepository;
import com.tiendaelectrodomesticos.cart.repository.IProductAPI;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.logging.Logger;


@Service
public class CartService implements ICartService {

    @Autowired
    private ICartRepository cartRepository;

    @Autowired
    private IProductAPI productAPI;

    private final static Logger LOGGER = Logger.getLogger(String.valueOf(CartService.class));

    @Override
    public void createCart(Cart cart) {
        try {
            cartRepository.save(cart);
            LOGGER.info("Creando carrito " + cart.getId() + " " + cart.getListCodeProducts() + " " + cart.getTotalPrice());
        } catch (Exception ex) {
            throw new RuntimeException("No se pudo crear un carrito", ex);
        }
    }

    @Override
    public Optional<Cart> findCart(Long id) {
        LOGGER.info("Buscando carrito por id: " + id);
        return cartRepository.findById(id);
    }

    @Override
    public void deleteCart(Long id) {
        LOGGER.info("Eliminando carrito con id: " + id);
        try {
            cartRepository.deleteById(id);
        } catch (Exception ex) {
            LOGGER.warning("Error al eliminar el carrito con ID: " + id);
            throw new BadRequestException("Error al eliminar el carrito con ID: " + id, ex);
        }
    }

    @Override
    public void addToCart(Long cartId, Integer productCode) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getListCodeProducts().add(productCode);
        LOGGER.info("guardando codigo");
        updateTotalPrice(cart);
        cartRepository.save(cart);
    }

    @Override
    public void removeFromCart(Long cartId, Integer productCode) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getListCodeProducts().remove(productCode);
        updateTotalPrice(cart);
        cartRepository.save(cart);
    }

    private void updateTotalPrice(Cart cart) {
        Double totalPrice = 0.0;
        for (Integer code : cart.getListCodeProducts()) {
            totalPrice += getProductPriceByCode(code);
        }
        cart.setTotalPrice(totalPrice);
    }

    @CircuitBreaker(name = "products-service", fallbackMethod = "fallbackGetProductPriceByCode")
    @Retry(name = "products-service")
    private Double getProductPriceByCode(Integer productCode) {
        ProductDTO productDTO = productAPI.getProduct(productCode);
        if (productDTO != null) {
            return productDTO.getSinglePrice();
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    public ProductDTO fallbackGetProductPriceByCode(Throwable throwable) {
        return new ProductDTO(null, "fallido", null);
    }

}


