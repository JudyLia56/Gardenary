package com.gardenary.global.config.security;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        String exception = (String) request.getAttribute("exception");

        // 토큰 없거나 DB에 아이디 없음
        if (exception == null) {
            setResponse(response, "403", authException.getMessage());
            return;
        }

        // 만료된 경우에만 401
        if (exception.contentEquals("ExpiredJwtException")) {
            setResponse(response, "401", "unauthorized");
        } else {
            setResponse(response, "403", exception);
        }
    }

    // 인증 실패 에러
    private void setResponse(HttpServletResponse response, String code, String msg) throws IOException {
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus("401".equals(code) ? HttpServletResponse.SC_UNAUTHORIZED : HttpServletResponse.SC_FORBIDDEN);
        json.put("status", code);
        json.put("result", msg);
        response.getWriter().print(json);
    }
}


