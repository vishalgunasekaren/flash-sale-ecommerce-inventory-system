package com.vishal.flashsale.flash_sale_inventory_system.repository;

import com.vishal.flashsale.flash_sale_inventory_system.entity.FlashSale;
import com.vishal.flashsale.flash_sale_inventory_system.entity.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


    @Modifying
    @Query("""
    UPDATE Product p SET p.stock = p.stock - :qty
    WHERE p.id = :id AND p.stock >= :qty
""")

    int stockUpdate(@Param("id") Long id, @Param("qty") int qty);
}
