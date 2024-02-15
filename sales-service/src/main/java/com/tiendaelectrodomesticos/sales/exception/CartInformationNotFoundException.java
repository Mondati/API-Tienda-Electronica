package com.tiendaelectrodomesticos.sales.exception;

public class CartInformationNotFoundException extends Exception{
    public CartInformationNotFoundException(Long saleId) {
        super("No se pudo obtener la información del carrito de compras para la venta con ID: " + saleId);
    }
}
