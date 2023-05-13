package com.msystem.utils;

import com.msystem.entity.Account;
import io.jsonwebtoken.*;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: JwtToken的工具類
 * @author: Eker
 * @date: 2023/5/3 下午 04:50
 * @version: V1.0
 */
@Component
public class JwtTokenUtils implements Serializable {
    //設定過期時間
    private static final long EXPIRATION_TIME = 120 * 60 * 1000; //60分鐘
    /**
     * JWT SECRET KEY
     */
    private static final String SECRET = "i want to play a game";

    /**
     * 簽發JWT
     */
    public static String generateToken(Account userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", userDetails.getUsername());
        claims.put("role", userDetails.getRole());
        //使用builder設置claims,Expiration，sign建立token，並使用compact()輸出成String
        return Jwts.builder()
                .setClaims( claims )
                .setExpiration( new Date( Instant.now().toEpochMilli() + EXPIRATION_TIME) )
                .signWith( SignatureAlgorithm.HS512, SECRET )
                .compact();
    }

    /**
     * 驗證JWT
     */
    public static Claims validateToken(String token) throws AuthException {
        try {
            return Jwts.parser()
                    .setSigningKey( SECRET )
                    .parseClaimsJws( token )
                    .getBody();
        } catch (SignatureException e) {
            throw new AuthException("Invalid JWT signature.");
        }
        catch (MalformedJwtException e) {
            throw new AuthException("Invalid JWT token.");
        }
        catch (ExpiredJwtException e) {
            throw new AuthException("Expired JWT token");
        }
        catch (UnsupportedJwtException e) {
            throw new AuthException("Unsupported JWT token");
        }
        catch (IllegalArgumentException e) {
            throw new AuthException("JWT token compact of handler are invalid");
        }
    }

    /**
     * 獲取JWT
     */
    public static String getToken(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
