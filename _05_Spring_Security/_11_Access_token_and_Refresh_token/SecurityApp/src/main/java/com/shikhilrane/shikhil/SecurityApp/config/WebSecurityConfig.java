package com.shikhilrane.shikhil.SecurityApp.config;

import com.shikhilrane.shikhil.SecurityApp.filters.JwtRequestFilter;
import com.shikhilrane.shikhil.SecurityApp.filters.LoggingFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final LoggingFilter loggingFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/posts/getAllPosts", "auth/**").permitAll()
                        .anyRequest().authenticated())
                .csrf(csrfConfig -> csrfConfig.disable())
                .addFilterBefore(loggingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(sessionConfig -> sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
//                .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

//    @Bean
//    UserDetailsService myInMemeoryUserDetailsService(){
//        UserDetails manager = User
//                .withUsername("Shikhil")
//                .password(passwordEncoder().encode("Skr"))
//                .roles("MANAGER")
//                .build();
//
//        UserDetails user = User
//                .withUsername("abc")
//                .password(passwordEncoder().encode("abc"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(manager, user);
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
