package com.emtech.bookinventory.config;

import com.emtech.bookinventory.entities.UserEntity;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {

    private static final String SECRET_KEY="3373367639792F423F4528482B4D6251655468576D5A7134743777217A254326";
    public String extractUsername(String token) {
        return extractClaim(token, claims -> claims.getSubject());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid( String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername())  && !isTokenExpired(token) ;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());

    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(UserEntity userEntity){
        return generateToken(new HashMap<>(), userEntity);
    }

    public String generateToken(Map<String, Object> extraClaims, UserEntity userEntity){
        Gson gson = new Gson();
        log.info(gson.toJson(userEntity));
        Map<String,Object> claims = new HashMap<>();
        claims.put("username",userEntity.getEmail());
        claims.put("email",userEntity.getEmail());
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userEntity.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(SignatureAlgorithm.HS256, getSigningKey())
                .compact();
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(getSigningKey() )
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }
}
