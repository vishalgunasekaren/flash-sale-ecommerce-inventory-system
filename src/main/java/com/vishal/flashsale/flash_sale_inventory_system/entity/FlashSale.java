package com.vishal.flashsale.flash_sale_inventory_system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "flash_sales")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FlashSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "discount_price", nullable = false)
    private BigDecimal discountPrice;

    @Column(name ="start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "stock_limit", nullable = false)
    private int stockLimit;

    @Column(nullable = false)
    private int perUserLimit;


}
