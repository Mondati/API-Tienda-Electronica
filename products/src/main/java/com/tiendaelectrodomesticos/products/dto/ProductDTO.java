package com.tiendaelectrodomesticos.products.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private Integer code;
    private String name;
    private String brand;
    private Double singlePrice;
}
