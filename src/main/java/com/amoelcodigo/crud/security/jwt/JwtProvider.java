package com.amoelcodigo.crud.security.jwt;

import com.amoelcodigo.crud.security.entity.UsuarioMain;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Clase que genera el token y valida que este bien fornamdo y no este expirado
 */
@Component
public class JwtProvider {

    // Implementamos un logger para ver cual metodo da error en caso de falla
    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    //Valores que tenemos en el aplicattion.properties
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication){
        UsuarioMain usuarioMain = (UsuarioMain) authentication.getPrincipal();
        return Jwts.builder().setSubject(usuarioMain.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    //subject --> Nombre del usuario
    public String getNombreUsuarioFromToken(String token){
           return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public Boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException e){
            logger.error("Token mal formado");
        }catch (UnsupportedJwtException e){
            logger.error("Token no soportado");
        }catch (ExpiredJwtException e){
            logger.error("Token expirado");
        }catch (IllegalArgumentException e){
            logger.error("Token vacio");
        }catch (SignatureException e){
            logger.error("Fallo con la firma");
        }
        return false;
    }


}
