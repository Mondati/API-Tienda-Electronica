package com.tiendaelectrodomesticos.sales.repository;

import com.tiendaelectrodomesticos.sales.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISaleRepository extends JpaRepository<Sale, Long> {
}
