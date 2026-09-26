package com.vishal.flashsale.flash_sale_inventory_system.repository;

import com.vishal.flashsale.flash_sale_inventory_system.entity.OrderItem;
import com.vishal.flashsale.flash_sale_inventory_system.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("""
    SELECT COALESCE(SUM(oi.quantity), 0) FROM OrderItem oi 
    WHERE oi.order.user.id = :userId 
    AND oi.flashSale.id = :saleId 
    AND oi.order.status <> :cancelled   
     
""")
    Long countBoughtByUser(@Param("userId") Long userId,
                           @Param("saleId") Long saleId,
                           @Param("cancelled") OrderStatus cancelled);

}
