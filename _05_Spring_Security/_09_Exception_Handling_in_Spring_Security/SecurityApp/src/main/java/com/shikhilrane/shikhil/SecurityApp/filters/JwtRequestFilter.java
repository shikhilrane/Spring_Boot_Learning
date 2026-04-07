package com.shikhilrane.shikhil.SecurityApp.filters;

import com.shikhilrane.shikhil.SecurityApp.entities.User;
import com.shikhilrane.shikhil.SecurityApp.services.Implementation.MyUserDetailsServiceImpl;
import com.shikhilrane.shikhil.SecurityApp.services.JwtService;
import com.shikhilrane.shikhil.SecurityApp.services.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final MyUserDetailsServiceImpl myUserDetailsService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         try {
            final String requestTokenHeader = request.getHeader("Authorization");       // a. Get Authorization header from the incoming request
            if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer ")) { // Check if header is missing or does not start with "Bearer "
                filterChain.doFilter(request, response);                                    // Skip JWT check and pass request to the next filter
                return;
            }

            String token = requestTokenHeader.substring(7).trim();              // Remove "Bearer " from header and extract the actual JWT token
            Long userIdFromToken = jwtService.getUserIdFromToken(token);                   // b. Extract userId from the JWT token using JwtService
            if (userIdFromToken != null && SecurityContextHolder.getContext().getAuthentication() == null) { // Check if userId exists and no user is set in SecurityContext yet
                User userById = myUserDetailsService.getUserById(userIdFromToken);         // c. Fetch user details from database using the extracted userId
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userById, null, null); // Create authentication object for this user
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)         // Attach request details like IP and session info
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken); // d. Set authenticated user in Spring Security context
            }

            filterChain.doFilter(request, response);                                        // e.
        } catch (Exception e){
             handlerExceptionResolver.resolveException(request,response,null,e);
             return;
         }
    }
}

/*
    1. .getHeader() from incoming request
    2. If header from incoming request is null or doesn't startsWith "Bearer ", then send request to next filter (i.e. to UsernamePasswordAuthenticationFilter)
    3. If got header, remove first 7 characters (i.e. "Bearer ") from the token
    4. Using jwtService.getUserIdFromToken(token), get the userIdFromToken from token
    5. if, userIdFromToken exist AND no user is set in SecurityContextHolder yet
    6. if, userIdFromToken exist AND no user is set in SecurityContextHolder yet then, fetch the user details from DB using extracted userIdFromToken
    7. Create authentication object for the userById
    8. Set details for created authentication object
    9. then set authenticated user in SecurityContextHolder
    10. Pass request to next filter using .doFilter()

    -> request
    -> header
    -> trim the header
    -> id of given token using jwtService
    -> if id exist and not set in SecurityContext
    -> fetch details for id from db
    -> create authentication object for extracted id from DB
    -> set details for token
    -> push it in SpringSecurityContext
*/