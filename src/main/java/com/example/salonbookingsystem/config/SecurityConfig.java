package com.example.salonbookingsystem.config;

import com.example.salonbookingsystem.model.enums.RolesEnum;
import com.example.salonbookingsystem.repositories.UserRepository;
import com.example.salonbookingsystem.services.impl.SbUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Map;

@Configuration
public class SecurityConfig{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeRequests(

                authorizeRequests-> authorizeRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(new RequestMatcher[]{
                                new AntPathRequestMatcher("/"),
                                new AntPathRequestMatcher("/login"),
                                new AntPathRequestMatcher("/register"),
                                new AntPathRequestMatcher("/login-error")
                        }).permitAll()
                        .requestMatchers(new RequestMatcher[] {
                            new AntPathRequestMatcher("/make-admin"),
                                new AntPathRequestMatcher("/post-news")
                        }).hasRole(RolesEnum.ADMIN.name())
                        .anyRequest().authenticated()

        ).formLogin(

                formLogin -> {
                    formLogin.loginPage("/login")
                            .usernameParameter("email")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/")
                            .failureForwardUrl("/login");
                }

        ).logout(

                logout -> {

                    logout.logoutUrl("/logout")
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true);

                }

        );

        return  http.build();

    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new SbUserDetailsService(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new DelegatingPasswordEncoder("bcrypt",
                Map.of("bcrypt",new BCryptPasswordEncoder()));
    }

}
