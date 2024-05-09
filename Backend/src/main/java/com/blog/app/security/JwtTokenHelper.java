package com.blog.app.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenHelper {

    public static final long JWT_TOKEN_VALIDITY = 24 * 60 * 60;

   private String stringKey = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";
    SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(stringKey));

    // Retrieve user name from jwt token
    public String getUsernameFromJwtToken(String token) {
//    	System.out.println("token jwthelper: "+token);
        return getClaimFromToken(token, Claims::getSubject);
        
    }

    // Retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    // For retrieving any information from token we need secretKey
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().verifyWith(secret).build().parseSignedClaims(token).getPayload();
    }

    // Check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        final Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    // While creating the token -
    // 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
    // 2. Sign the JWT using the HS512 algorithm and secret key.
    // 3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    // compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) {
    	return Jwts.builder()
    		    .claims().empty().add(claims).and() // Set the claims for the JWT
    		    .subject(subject) // Set the subject of the JWT (usually the username)
    		    .issuedAt(new Date(System.currentTimeMillis())) // Set the issuance time of the JWT
    		    .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)) // Set the expiration time of the JWT
    		    .signWith(secret) // Sign the JWT with the secret key
    		    .compact(); // Compact the JWT into its final form

    }
//    private String doGenerateToken(Map<String, Object> claims, String subject) {
//        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
//                .signWith(secret).compact();
//    }

    // Validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromJwtToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
