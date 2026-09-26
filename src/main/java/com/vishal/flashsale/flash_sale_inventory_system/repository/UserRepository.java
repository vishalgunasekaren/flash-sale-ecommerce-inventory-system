package com.vishal.flashsale.flash_sale_inventory_system.repository;

import com.vishal.flashsale.flash_sale_inventory_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    boolean existByEmail(String email);
}
