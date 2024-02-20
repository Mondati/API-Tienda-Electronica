package com.tiendaelectrodomesticos.sales.service;

import com.tiendaelectrodomesticos.sales.dto.CartDTO;
import com.tiendaelectrodomesticos.sales.dto.SaleDTO;
import com.tiendaelectrodomesticos.sales.exception.SaleNotFoundException;
import com.tiendaelectrodomesticos.sales.model.Sale;
import com.tiendaelectrodomesticos.sales.repository.ICartAPI;
import com.tiendaelectrodomesticos.sales.repository.ISaleRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.logging.Logger;

@Service
public class SaleService implements ISaleService {

    private final ISaleRepository saleRepo;
    private final ICartAPI cartAPI;

    @Autowired
    public SaleService(ISaleRepository saleRepo, ICartAPI cartAPI) {
        this.saleRepo = saleRepo;
        this.cartAPI = cartAPI;
    }

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(SaleService.class));

    @Override
    public void saveSale(Sale sale) {
        LOGGER.info("Creando venta");
        saleRepo.save(sale);
    }

    @Override
    public void deleteSale(Long id) {
        Optional<Sale> sale = getSale(id);
        if (sale.isPresent()) {
            LOGGER.info("Borrando venta");
            saleRepo.deleteById(id);
        } else {
            throw new SaleNotFoundException("No se encontro la venta con el id: " + id + " a borrar");
        }
    }

    @Override
    public void editSale(Long id, Sale sale) {
        LOGGER.info("Buscando la venta con ID: " + id);
        Optional<Sale> existingSaleOptional = this.getSale(id);

        if (existingSaleOptional.isPresent()) {
            Sale existingSale = existingSaleOptional.get();
            LOGGER.info("Venta encontrada: " + existingSale);

            // Actualizar la venta existente con los datos proporcionados en la venta recibida
            existingSale.setSaleDate(sale.getSaleDate());
            existingSale.setCartId(sale.getCartId());

            // Guardar los cambios en la base de datos
            saleRepo.save(existingSale);

            LOGGER.info("Venta editada con éxito: " + existingSale);
        } else {
            LOGGER.warning("No se encontró ninguna venta con el ID especificado: " + id);
            throw new SaleNotFoundException("No se encontró ninguna venta con el ID especificado: " + id);
        }
    }

    @Override
    @CircuitBreaker(name = "carts-service", fallbackMethod = "fallbackGetSale")
    @Retry(name = "carts-service")
    public Optional<SaleDTO> getSaleDTO(Long id) {
        Optional<Sale> sale = getSale(id);
        SaleDTO saleDTO = new SaleDTO();

        if (sale.isPresent()) {
            LOGGER.info("estoy en el service dentro del if");
            CartDTO cartDTO = cartAPI.getCart(sale.get().getCartId());
            saleDTO.setSaleDate(sale.get().getSaleDate());
            saleDTO.setCodeProduct(cartDTO.getListCodeProducts());
            saleDTO.setTotalPrice(cartDTO.getTotalPrice());
            return Optional.of(saleDTO);
        }
        return Optional.empty();
    }

    public Optional<Sale> getSale(Long id) {
        return saleRepo.findById(id);
    }

    public SaleDTO fallbackGetSale(Throwable throwable) {
        return new SaleDTO(null, null, null);
    }


}
