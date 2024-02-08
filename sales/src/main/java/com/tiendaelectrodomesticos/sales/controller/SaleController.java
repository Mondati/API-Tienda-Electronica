package com.tiendaelectrodomesticos.sales.controller;

import com.tiendaelectrodomesticos.sales.dto.SaleDTO;
import com.tiendaelectrodomesticos.sales.exception.ResourceNotFoundException;
import com.tiendaelectrodomesticos.sales.model.Sale;
import com.tiendaelectrodomesticos.sales.service.ISaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping(name = "/sale")
public class SaleController {

    @Autowired
    private ISaleService saleService;

    private final static Logger LOGGER = Logger.getLogger(String.valueOf(SaleController.class));

    @PostMapping("/create")
    public ResponseEntity<String> saveSale(@RequestBody Sale sale) {
        saleService.saveSale(sale);
        return ResponseEntity.ok().body("Sale creada con exito");
    }

    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> getSale(@PathVariable Long id) throws ResourceNotFoundException {
        SaleDTO saleDTO = saleService.getSale(id);
        if (saleDTO != null) {
            LOGGER.info("Buscando venta por ID con exito " + saleDTO.getCodeProduct());
            return ResponseEntity.ok(saleDTO);
        } else {
            throw new ResourceNotFoundException("Venta con id: " + id + " no existe");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSale(@PathVariable Long id) throws ResourceNotFoundException {
        SaleDTO sale = saleService.getSale(id);
        if (sale != null) {
            LOGGER.info("Se elimino correctamente venta con id: " + id);
            saleService.deleteSale(id);
            return ResponseEntity.ok("Se elimino correctamente venta con id: " + id);
        } else {
            LOGGER.info("No se pudo eliminar venta con id: " + id);
            throw new ResourceNotFoundException("No se pudo eliminar venta con id: " + id);
        }
    }

}
