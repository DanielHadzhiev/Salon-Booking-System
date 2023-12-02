package com.example.salonbookingsystem.services.impl;

import com.example.salonbookingsystem.model.entity.Role;
import com.example.salonbookingsystem.model.entity.UserEntity;
import com.example.salonbookingsystem.repositories.UserRepository;
import com.example.salonbookingsystem.utils.CustomUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;



public class SbUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public SbUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

       return userRepository
                .findByEmail(email)
                .map(this::map)
                .orElseThrow(()->new UsernameNotFoundException("User not found."));

    }

    private UserDetails map (UserEntity user){
       return new CustomUserDetails(user.getName(),
               user.getPassword(),
               user.getRoles().stream().map(SbUserDetailsService::map).toList(),
               user.getEmail());
    }

    private static GrantedAuthority map(Role role){

return new SimpleGrantedAuthority
        ("ROLE_"+role.getRole().name());

    }
}
