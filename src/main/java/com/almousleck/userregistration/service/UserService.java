package com.almousleck.userregistration.service;

import com.almousleck.userregistration.model.Users;
import com.almousleck.userregistration.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    Users findByEmail(String email);
    Users save(UserRegistrationDto registrationDto);
}
