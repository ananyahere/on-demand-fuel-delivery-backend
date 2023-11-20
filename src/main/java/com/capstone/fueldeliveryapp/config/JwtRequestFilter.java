package com.capstone.fueldeliveryapp.config;

import com.capstone.fueldeliveryapp.service.JwtService;
import com.capstone.fueldeliveryapp.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Lazy
    @Autowired
    private JwtUtil jwtUtil;

    @Lazy
    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().contains("/authenticate") || request.getServletPath().contains("/register") || request.getServletPath().contains("/catchphase") || request.getServletPath().contains("/city")){
            System.out.println("Request at /register or /authenticate or /catchphase or /city");
            filterChain.doFilter(request, response);
            return;
        }
        // get header from request. JWT token is in header
        final String header = request.getHeader("Authorization");
        String jwtToken = null;
        String username = null;
        if(header != null && header.startsWith("Bearer ")){
            jwtToken = header.substring(7); // length('Bearer ') == 7
            try{
                username = jwtUtil.getUserNameFromToken(jwtToken);
                System.out.println("username: "+username);
                System.out.println("jwtToken:"+jwtToken);
            }catch(IllegalArgumentException e){
                System.out.println("Unable to get JWT Token");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unable to get JWT Token");
            }catch(ExpiredJwtException e){
                System.out.println("JWT Token is expired.");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token has expired");
            }catch(Exception e){
                System.out.println("Invalid JWT Token");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
            }
        }else{
            System.out.println("Jwt token doesn't start with Bearer.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token is missing or does not start with Bearer");
        }
        if(
                username != null &&
                        SecurityContextHolder.getContext().getAuthentication() == null   //  checks if the user details are not already present in the Spring Security context
        ){
            UserDetails userDetails = jwtService.loadUserByUsername(username);  // extracting the UserDetails object from the JWT
            if(jwtUtil.validateToken(jwtToken, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(    // creates a new UsernamePasswordAuthenticationToken object to hold the user's authentication information.
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken); //  Authentication object is set in the Spring Security context
            }
            filterChain.doFilter(request, response); // allowing the request to pass through to the next filter (if any) in the chain.
        }

    }
}
