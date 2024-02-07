package com.tiendaelectrodomesticos.sales.service;

import com.tiendaelectrodomesticos.sales.dto.CartDTO;
import com.tiendaelectrodomesticos.sales.dto.SaleDTO;
import com.tiendaelectrodomesticos.sales.model.Sale;
import com.tiendaelectrodomesticos.sales.repository.ICartAPI;
import com.tiendaelectrodomesticos.sales.repository.ISaleRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleService implements ISaleService {

    @Autowired
    private ISaleRepository saleRepo;

    @Autowired
    private ICartAPI cartAPI;

    @Override
    public void saveSale(Sale sale) {
        saleRepo.save(sale);
    }

    @Override
    @CircuitBreaker(name = "carts-service", fallbackMethod = "fallbackGetSale")
    @Retry(name = "carts-service")
    public SaleDTO getSale(Long id) {
        Sale sale = saleRepo.findById(id).orElse(null);
        SaleDTO saleDTO = new SaleDTO();
        CartDTO cartDTO = cartAPI.getCart(sale.getCartId());

        saleDTO.setSaleDate(sale.getSaleDate());
        saleDTO.setCodeProduct(cartDTO.getCodeProduct());
        saleDTO.setTotalPrice(cartDTO.getTotalPrice());

        return saleDTO;
    }

    public SaleDTO fallbackGetSale(Throwable throwable) {
        return new SaleDTO(null, null, null);
    }

    @Override
    public void deleteSale(Long id) {

    }

    @Override
    public void editSale(Long id, Sale sale) {

    }

}
