package com.expense.expense.utils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;



@Component
public class JwtUtils {
    
    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    @Value("${security.jwt.time.expires}")
    private long timeExpires;

    public String generateToken(Authentication authentication){
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
        String username = authentication.getPrincipal().toString();
        String authorities = authentication.getAuthorities()
                                .stream()
                                .map(GrantedAuthority -> GrantedAuthority.getAuthority())
                                .collect(Collectors.joining(","))
                                ;
        String jwtToken = JWT.create()
                            .withIssuer(userGenerator)
                            .withSubject(username)
                            .withClaim("authorities", authorities)
                            .withIssuedAt(new Date())
                            .withExpiresAt(new Date(System.currentTimeMillis() + timeExpires))
                            .withJWTId(UUID.randomUUID().toString())
                            .withNotBefore(new Date(System.currentTimeMillis())) //cuando se hace valido el token, si le sumo dos horas va a estar disponible en dos horas
                            .sign(algorithm)
                            ;
        return jwtToken;
    }

    public DecodedJWT validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                                    .withIssuer(userGenerator)
                                    .build()
                                    ;
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT;
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token invalid, not Authorized");
        }
    }

    public String extractUsername(DecodedJWT decodedJWT){
        return decodedJWT.getSubject().toString();
    }

    public Claim getClaim(DecodedJWT decodedJWT, String claimName){
        return decodedJWT.getClaim(claimName);
    }

    public Map<String, Claim> getAllClaims(DecodedJWT decodedJWT){
        return decodedJWT.getClaims();
    }
}
