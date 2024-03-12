package org.azerciard.productservice.repository;

import jakarta.transaction.Transactional;
import org.azerciard.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product>, PagingAndSortingRepository<Product, String> {
    Boolean existsByName(String name);
    List<Product> findByStockGreaterThan(BigDecimal stock);
}