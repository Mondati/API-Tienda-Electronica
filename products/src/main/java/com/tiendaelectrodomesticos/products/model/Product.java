package com.tiendaelectrodomesticos.products.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)

    private Long id;
    @Column(unique = true)
    @NotBlank(message = "El campo code no puede estar vacío")
    private Integer code;
    @NotBlank(message = "El campo name no puede estar vacío")
    private String name;
    @NotBlank(message = "El campo brand no puede estar vacío")
    private String brand;
    @NotBlank(message = "El campo singlePrice no puede estar vacío")
    private Double singlePrice;

}
