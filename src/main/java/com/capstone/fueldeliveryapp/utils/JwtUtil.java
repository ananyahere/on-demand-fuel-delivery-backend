package com.capstone.fueldeliveryapp.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    // returns the username associated with the provided JWT token.
    private static final String SECRET_KEY="spring_security_jwt_demo";
    private static final int TOKEN_VALIDITY =  3600 * 5;

    public String getUserNameFromToken(String token){
        return getClaimFromToken(                token,
                // shorthand way to define a lambda expression that calls the "getSubject" method on the "Claims" object.
                Claims::getSubject); // "subject" claim is a reserved claim in JWT that is typically used to represent the user or the entity associated with the token.)
    }

    // to extract a specific claim from a JWT token using the "getAllClaimsFromToken" method defined earlier and then the "claimResolver" function to extract the specific claim.
    private<T> T getClaimFromToken(
            String token,
            Function<Claims, T> claimResolver   // function object that takes a "Claims" object and returns an object of type "T".
    ){
        final Claims claims = getAllClaimsFromToken(token); // to get all the claims from the provided JWT token
        return claimResolver.apply(claims); // to extract the specific claim from the "claims" object.
    }

    // to extract the claims (i.e., data elements) from a JWT (JSON Web Token) using the "Secret Key" that was used to sign the JWT.
    private Claims getAllClaimsFromToken(String token){
        // JWS - JSON Web Signature
        return Jwts.parser()
                .setSigningKey(SECRET_KEY) // set the "Secret Key" that was used to sign the JWT.
                .parseClaimsJws(token) //returns a "Jws" (JSON Web Signature) object.
                .getBody();  // returns the "Claims" object containing all the claims from the JWT.
    }

    public boolean validateToken(String token, UserDetails userDetails){
        String username = getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

}
