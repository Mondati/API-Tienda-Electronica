package com.tiendaelectrodomesticos.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaleDTO {
    private LocalDate saleDate;
    private Double totalPrice;
    private List<Integer> codeProduct;
}
