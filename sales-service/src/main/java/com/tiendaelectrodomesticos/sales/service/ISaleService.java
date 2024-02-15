package com.tiendaelectrodomesticos.sales.service;

import com.tiendaelectrodomesticos.sales.dto.SaleDTO;
import com.tiendaelectrodomesticos.sales.exception.SaleNotFoundException;
import com.tiendaelectrodomesticos.sales.model.Sale;

import java.util.Optional;

public interface ISaleService {

    public void saveSale(Sale sale);

    public Optional<Sale> getSale(Long id);

    public Optional<SaleDTO> getSaleDTO(Long id);

    public void deleteSale(Long id);

    public void editSale(Long id, Sale sale) throws SaleNotFoundException;


}
