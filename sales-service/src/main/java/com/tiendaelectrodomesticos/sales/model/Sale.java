package com.tiendaelectrodomesticos.sales.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private LocalDate saleDate;
    @NotNull
    @Positive
    private Long cartId;

    @Override
    public String toString() {
        return "id=" + id +
                ", fecha de venta=" + saleDate +
                ", carrito asociado con id=" + cartId +
                '}';
    }
}
