package com.vishal.flashsale.flash_sale_inventory_system.service;

import com.vishal.flashsale.flash_sale_inventory_system.dto.UserRequest;
import com.vishal.flashsale.flash_sale_inventory_system.dto.UserResponse;
import com.vishal.flashsale.flash_sale_inventory_system.entity.User;
import com.vishal.flashsale.flash_sale_inventory_system.exception.DuplicateEmailException;
import com.vishal.flashsale.flash_sale_inventory_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse createUser(UserRequest request){

        String normalizedEmail = request.email().trim().toLowerCase();

        if(userRepository.existByEmail(normalizedEmail)){
            throw new DuplicateEmailException("Email already registered: " + normalizedEmail);
        }

        User user = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(normalizedEmail)
                .password(request.passward())
                .role("CUSTOMER")
                .build();

        User saved  = userRepository.save(user);
        return toResponse(saved);
    }

    private UserResponse toResponse(User u){
        return new UserResponse(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getRole(), u.getCreatedAt());
    }

}
