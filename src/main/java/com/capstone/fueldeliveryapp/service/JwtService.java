package com.capstone.fueldeliveryapp.service;

import com.capstone.fueldeliveryapp.entity.JwtRequest;
import com.capstone.fueldeliveryapp.entity.JwtResponse;
import com.capstone.fueldeliveryapp.entity.User;
import com.capstone.fueldeliveryapp.entity.UserAuth;
import com.capstone.fueldeliveryapp.repository.UserAuthRepository;
import com.capstone.fueldeliveryapp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {
    @Autowired
    private UserAuthRepository userAuthRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception{
        String userName = jwtRequest.getUserName();
        String userPassword = jwtRequest.getUserPassword();
        authenticate(userName, userPassword);

        final UserDetails userDetails = loadUserByUsername(userName);
        final String newGeneratedToken = jwtUtil.generateToken(userDetails);
        System.out.println("Token generated!");
        UserAuth userAuth = userAuthRepository.findById(userName).get();
        return new JwtResponse(userAuth, newGeneratedToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuth userAuth = userAuthRepository.findById(username).get();
        if(userAuth!=null){
            return new org.springframework.security.core.userdetails.User(
                    userAuth.getUsername(),
                    userAuth.getUserPassword(),
                    getAuthorites(userAuth)
            );
        } else{
            throw new UsernameNotFoundException("Username is not valid.");
        }
    }

    private Set getAuthorites(UserAuth userAuth){
        Set userAuthorities = new HashSet();
        userAuth.getRole().forEach(role -> {
            userAuthorities
                    .add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        });
        return userAuthorities;
    }

    private void authenticate(String userName, String userPassword) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        }catch(BadCredentialsException e){
            throw new Exception("Bad credentials by user.");
        }catch(DisabledException e){
            throw new Exception("User is disabled.");
        }
    }
}
