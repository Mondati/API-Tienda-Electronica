package com.tiendaelectrodomesticos.products.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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
    @Size(max = 25, min = 1, message = "El nombre no puede contener mas de 25 caracteres")
    private String name;

    @NotBlank(message = "El campo brand no puede estar vacío")
    @Size(max = 25, min = 1, message = "La marca no puede contener mas de 25 caracteres")
    private String brand;

    @NotBlank(message = "El campo singlePrice no puede estar vacío")
    @PositiveOrZero
    private Double singlePrice;

}
