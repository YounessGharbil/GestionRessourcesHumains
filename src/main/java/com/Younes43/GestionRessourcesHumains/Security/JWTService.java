package com.Younes43.GestionRessourcesHumains.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
    final String SECRET_KEY="Zr4u7xADGJaNdRgUkXp2s5v8yBEHMbPeShVmYq3t6w9zCFJ789BBNcRfT";

    private Key getSignInKey() {

        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();

    }
    public <T> T extractClaim(String token, Function<Claims,T> claimResolver) {
        final Claims claims=extractAllClaims( token);
        return claimResolver.apply(claims);
    }

    public String extractAppUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }


    public String generateToken(
            UserDetails userdetails
    ) {

        return generateToken(new HashMap<>(),userdetails);
    }


    public String generateToken(
            Map<String,Object> extraClaims,
            UserDetails userdetails
    ) {



        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userdetails
                        .getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public boolean isTokenValid(String token,UserDetails userDetails) {
        final String username=extractAppUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }


    private boolean isTokenExpired(String token) {
        // TODO Auto-generated method stub
        return extractExpiration(token).before(new Date());
    }


    private Date extractExpiration(String token) {
        // TODO Auto-generated method stub
        return extractClaim(token, Claims::getExpiration);
    }
}
