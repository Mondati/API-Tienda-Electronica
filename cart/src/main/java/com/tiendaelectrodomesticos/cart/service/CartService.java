package com.tiendaelectrodomesticos.cart.service;

import com.tiendaelectrodomesticos.cart.dto.ProductDTO;
import com.tiendaelectrodomesticos.cart.exception.ProductNotFoundException;
import com.tiendaelectrodomesticos.cart.exception.ResourceNotFoundException;
import com.tiendaelectrodomesticos.cart.model.Cart;
import com.tiendaelectrodomesticos.cart.repository.ICartRepository;
import com.tiendaelectrodomesticos.cart.repository.IProductAPI;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.logging.Logger;


@Service
public class CartService implements ICartService {

    private final ICartRepository cartRepository;

    private final IProductAPI productAPI;

    @Autowired
    public CartService(ICartRepository cartRepository, IProductAPI productAPI) {
        this.cartRepository = cartRepository;
        this.productAPI = productAPI;
    }


    private static final Logger LOGGER = Logger.getLogger(String.valueOf(CartService.class));


    @Override
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
        LOGGER.info("Creando carrito "
                + cart.getId()
                + " "
                + cart.getListCodeProducts()
                + " "
                + cart.getTotalPrice());
    }


    @Override
    public Optional<Cart> getCart(Long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        if (cart.isPresent()) {
            LOGGER.info("Buscando carrito por id: " + id);
            return cart;
        } else {
            throw new ResourceNotFoundException("El carrito con id: " + id + " no fue encontrado.");
        }
    }


    @Override
    public void deleteCart(Long id) {
        Optional<Cart> cart = getCart(id);
        if (cart.isPresent()) {
            LOGGER.info("Borrando carrito con id: " + id);
            cartRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("El carrito no se pudo eliminar");
        }
    }


    @Override
    public void addToCart(Long cartId, Integer productCode) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cart.getListCodeProducts().add(productCode);
            updateTotalPrice(cart);
            LOGGER.info("guardando producto con codigo " + productCode);
            cartRepository.save(cart);
        } else {
            throw new ResourceNotFoundException("No se pudo agregar correctamente");
        }

    }


    @Override
    public void removeFromCart(Long cartId, Integer productCode) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            LOGGER.info("Buscando producto a eliminar: " + productCode);
            Cart cart = optionalCart.get();
            if (cart.getListCodeProducts().contains(productCode)) {
                cart.getListCodeProducts().remove(productCode);
                LOGGER.info("Producto " + productCode + " eliminado del carrito");
            } else {
                throw new ProductNotFoundException("El producto " + productCode + " no está presente en el carrito.");
            }
            updateTotalPrice(cart);
            cartRepository.save(cart);
        } else {
            throw new ResourceNotFoundException("No se pudo encontrar el carrito con ID: " + cartId);
        }
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
            throw new ResourceNotFoundException("Product not found");
        }
    }


    public ProductDTO fallbackGetProductPriceByCode(Throwable throwable) {
        return new ProductDTO(null, "fallido", null);
    }

}


