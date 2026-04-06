package com.shikhilrane.shikhil.SecurityApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/posts/getAllPosts").permitAll()
                        .requestMatchers("/posts/createPost").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers("/posts/*").hasRole("USER") // * → just one level (e.g./posts/create) and ** → unlimited levels (e.g./posts/create/getAll)
                        .anyRequest().authenticated())
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig -> sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    UserDetailsService myInMemeoryUserDetailsService(){
        UserDetails manager = User
                .withUsername("Shikhil")
                .password(passwordEncoder().encode("Skr"))
                .roles("MANAGER")
                .build();

        UserDetails user = User
                .withUsername("abc")
                .password(passwordEncoder().encode("abc"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(manager, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
