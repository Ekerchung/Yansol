package com.msystem.filter;

import com.msystem.utils.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.security.auth.message.AuthException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @Description: JwtAuthorization的過濾器
 * @author: Eker
 * @date: 2023/5/8 下午 11:41
 * @version: V1.0
 */
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    // 將 /login 加入白名單，即不需要進行 token 權限驗證
    private static final String[] WHITE_LIST = {
            "/login",
            "/home"
    };
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 處理瀏覽器的預請求
        if (request.getMethod().equals("OPTIONS")) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,OPTIONS,DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type,Accept,Authorization,token");
            return;
        }

        String authToken = request.getHeader("Authorization");
        String uri = request.getRequestURI();
        boolean isWhiteList = Arrays.stream(WHITE_LIST)
                .anyMatch(uri::endsWith);

        // 如果訪問的是白名單中的 uri，則不進行 token 驗證
        if (!isWhiteList) {
            if (authToken == null || authToken.isEmpty() || !authToken.startsWith("Bearer")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Jwt token is required");
                return;
            }
            String token = authToken.substring("Bearer".length()).trim();
            // 驗證 token 是否有效，將 token 中包含的用戶信息存入 HttpServletRequest 中
            try {
                Claims claims = JwtTokenUtils.validateToken(token);
                request.setAttribute("claims", claims);
            } catch (AuthException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            }
        }
        chain.doFilter(request, response);
    }


}
