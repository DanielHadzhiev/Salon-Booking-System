package com.example.salonbookingsystem.tests.utils;

import com.example.salonbookingsystem.utils.CustomAuthentication;
import com.example.salonbookingsystem.utils.CustomUserDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class CustomAuthenticationTest {

    @Test
    void testGetAuthorities() {

        CustomUserDetails userDetails = new CustomUserDetails("username",
                "password",
                Collections.emptyList(),
                "test@gmail.com");
        Collection<? extends GrantedAuthority> authorities = Arrays.asList(() -> "ROLE_USER", () -> "ROLE_ADMIN");
        CustomAuthentication authentication = new CustomAuthentication(userDetails, "credentials", authorities);


        Collection<? extends GrantedAuthority> result = authentication.getAuthorities();


        Assertions.assertSame(result, authorities);
    }

    @Test
    void testGetCredentials() {

        CustomUserDetails userDetails = new CustomUserDetails("username", "password", Collections.emptyList(),"test@gmail.com");
        CustomAuthentication authentication = new CustomAuthentication(userDetails, "credentials", Collections.emptyList());


        Object result = authentication.getCredentials();


        Assertions.assertSame("credentials", result);
    }

    @Test
    void testGetDetails() {

        CustomAuthentication authentication = new CustomAuthentication(null, null, Collections.emptyList());


        Object result = authentication.getDetails();


        Assertions.assertNull(result);
    }

    @Test
    void testGetPrincipal() {

        CustomUserDetails userDetails = new CustomUserDetails("username",
                "password",
                Collections.emptyList(),
                "test@gmail.com");
        CustomAuthentication authentication = new CustomAuthentication(userDetails, null, Collections.emptyList());


        Object result = authentication.getPrincipal();


        Assertions.assertSame(userDetails, result);
    }

    @Test
    void testIsAuthenticated() {

        CustomUserDetails userDetails = new CustomUserDetails("username",
                "password",
                Collections.emptyList(),
                "test@gmail.com");
        CustomAuthentication authentication = new CustomAuthentication(userDetails, null, Collections.emptyList());


        boolean result = authentication.isAuthenticated();


        Assertions.assertTrue(result);
    }

    @Test
    void testSetAuthenticated() {

        CustomUserDetails userDetails = new CustomUserDetails("username",
                "password",
                Collections.emptyList(),
                "test@gmail.com");
        CustomAuthentication authentication = new CustomAuthentication(userDetails, null, Collections.emptyList());


        authentication.setAuthenticated(false);


        Assertions.assertFalse(authentication.isAuthenticated());
    }

    @Test
    void testGetName() {

        CustomUserDetails userDetails = new CustomUserDetails("username",
                "password",
                Collections.emptyList(),
                "test@gmail.com");
        CustomAuthentication authentication = new CustomAuthentication(userDetails, null, Collections.emptyList());


        String result = authentication.getName();

        Assertions.assertSame("username", result);
    }
}
