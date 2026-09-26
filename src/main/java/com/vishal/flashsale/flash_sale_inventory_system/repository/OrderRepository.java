package com.vishal.flashsale.flash_sale_inventory_system.repository;

import com.vishal.flashsale.flash_sale_inventory_system.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
