package com.tiendaelectrodomesticos.sales.controller;

import com.tiendaelectrodomesticos.sales.dto.SaleDTO;
import com.tiendaelectrodomesticos.sales.exception.SaleNotFoundException;
import com.tiendaelectrodomesticos.sales.model.Sale;
import com.tiendaelectrodomesticos.sales.service.ISaleService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("sale")
public class SaleController {


    private final ISaleService saleService;

    @Autowired
    public SaleController(ISaleService saleService) {
        this.saleService = saleService;
    }

    private static final Logger LOGGER = Logger.getLogger(String.valueOf(SaleController.class));


    @PostMapping("/create")
    @Transactional
    public ResponseEntity<String> saveSale(@RequestBody @Valid Sale sale) {
        saleService.saveSale(sale);
        return ResponseEntity.ok().body("Sale creada con exito: " + sale);
    }

    @PutMapping("/edit/{id}")
    @Transactional
    public ResponseEntity<String> editSale(@PathVariable Long id, @RequestBody Sale sale) {
        try {
            saleService.editSale(id, sale);
            return ResponseEntity.ok().body("Venta editada con éxito");
        } catch (SaleNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> getSale(@PathVariable Long id) {
        Optional<SaleDTO> saleDTO = saleService.getSaleDTO(id);
        if (saleDTO.isPresent()) {
            LOGGER.info("buscando venta dto con exitto: " + id);
            return ResponseEntity.ok(saleDTO.get());
        } else {
            LOGGER.info("No se encontro venta por ID: " + id);
            throw new SaleNotFoundException(("No se pudo encontrar la venta con id: " + id));
        }
    }


    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<String> deleteSale(@PathVariable Long id) {
        try {
            saleService.deleteSale(id);
            LOGGER.info("Venta eliminada con éxito con ID: " + id);
            return ResponseEntity.ok("Venta eliminada con éxito con ID: " + id);
        } catch (SaleNotFoundException e) {
            LOGGER.warning("No se pudo eliminar la venta con ID: " + id + ". Motivo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la venta con ID: " + id);
        }
    }


}
