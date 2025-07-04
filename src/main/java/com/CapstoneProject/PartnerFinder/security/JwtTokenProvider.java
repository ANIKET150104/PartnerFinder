package com.CapstoneProject.PartnerFinder.security;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
	
	private static final String SECRET = Base64.getEncoder().encodeToString("jMnOs39Zdlgh1BzI2ROXpF0QnUZowHvA8KhN+I6kxXA=".getBytes());
    private long ExpirationTime = 1000 * 60 * 60 * 24;
    
    public String generateToken(Authentication authentication) {

        String username = authentication.getName();

        Date currentDate = new Date(System.currentTimeMillis());

        Date expireDate = new Date(currentDate.getTime() + ExpirationTime);

        List<String> roles = authentication.getAuthorities().stream()
        		.map(GrantedAuthority::getAuthority)
        		.collect(Collectors.toList());
        		
        String token = Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(key())
                .compact();

        return token;
    }
    
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }
    
    // get username from JWT token
    public String getUsername(String token) {

        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }


    // validate JWT token
    public boolean validateToken(String token) throws Exception {
        try {
            Jwts.parser()
              .verifyWith((SecretKey) key())
              .build()
              .parse(token);
            return true;
        } catch (MalformedJwtException malformedJwtException) {
            throw new Exception("Invalid JWT Token");
        } catch (ExpiredJwtException expiredJwtException) {
            throw new Exception("Expired JWT token");
        } catch (UnsupportedJwtException unsupportedJwtException) {
            throw new Exception("Unsupported JWT token");
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new Exception("Jwt claims string is null or empty");
        }
    }
}
