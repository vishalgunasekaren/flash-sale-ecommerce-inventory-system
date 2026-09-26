package com.vishal.flashsale.flash_sale_inventory_system.repository;

import com.vishal.flashsale.flash_sale_inventory_system.dto.OrderRequest;
import com.vishal.flashsale.flash_sale_inventory_system.entity.FlashSale;
import com.vishal.flashsale.flash_sale_inventory_system.entity.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface FlashSaleRepository extends JpaRepository<FlashSale, Long> {
    @Query("""
    SELECT f
    FROM FlashSale f
    WHERE f.product.id = :productId
      AND f.startTime <= :now
      AND f.endTime >= :now
""")
    Optional<FlashSale> findActiveSale(@Param("productId") Long productId,
                                       @Param("now") LocalDateTime now
    );

    @Modifying
    @Query("""
    UPDATE FlashSale f
    SET f.stockLimit = f.stockLimit - :quantity
    WHERE f.id = :flashSaleId
        AND f.stockLimit >= :quantity
""")

    int stockUpdate(@Param("flashSaleId") Long flashSaleId,
                    @Param("quantity") int quantity);


}
