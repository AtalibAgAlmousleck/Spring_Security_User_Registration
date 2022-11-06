package com.almousleck.userregistration.service;

import com.almousleck.userregistration.model.Role;
import com.almousleck.userregistration.model.Users;
import com.almousleck.userregistration.repository.UserRepository;
import com.almousleck.userregistration.web.dto.UserRegistrationDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       Users users = userRepository.findByEmail(email);
       if(users == null) {
           throw new UsernameNotFoundException("Invalid username or password");
       }
        return new User(users.getEmail(),
                users.getPassword(),
                mapRolesToAuthorities(users.getRoles())
                );
    }

    @Override
    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Users save(UserRegistrationDto registrationDto) {
        Users users = new Users();
        users.setFirstName(registrationDto.getFirstName());
        users.setLastName(registrationDto.getLastName());
        users.setEmail(registrationDto.getEmail());
        users.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        users.setRoles(Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(users);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
