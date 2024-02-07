package com.tiendaelectrodomesticos.sales.service;

import com.tiendaelectrodomesticos.sales.dto.SaleDTO;
import com.tiendaelectrodomesticos.sales.model.Sale;

public interface ISaleService {

    public void saveSale(Sale sale);

    public SaleDTO getSale(Long id);

    public void deleteSale(Long id);

    public void editSale(Long id, Sale sale);


}
