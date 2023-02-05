package com.medical.program.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medical.program.model.entity.Employee;
import com.medical.program.service.AuthService;
import com.medical.program.utils.JWTTokenCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String phone = request.getParameter("phone"); //change "username" to "phone"
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phone, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        Employee employee = authService.getEmployeeByPhone(user.getUsername());

        JWTTokenCreator tokenCreator = new JWTTokenCreator(employee);
        try {
            String access_token = tokenCreator.createToken(JWTTokenCreator.TokenType.ACCESS_TOKEN);
            String refresh_token = tokenCreator.createToken(JWTTokenCreator.TokenType.REFRESH_TOKEN);

            Map<String,Object> tokens = new HashMap<>();
            tokens.put("name", employee.getFullName());
            tokens.put("userId",employee.getId());
            tokens.put("phone",employee.getPhoneNumber());
            tokens.put("avatar", employee.getAvatar());
            tokens.put("loginDate", new Date());
            tokens.put("accessToken", access_token);
            tokens.put("refreshToken", refresh_token);
            response.setContentType(APPLICATION_JSON_VALUE);

            new ObjectMapper().writeValue(response.getOutputStream(),tokens);
        }catch (Exception e){
            response.setHeader("error", e.getMessage());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            Map<String,String> error = new HashMap<>();
            error.put("error_message",e.getMessage());
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(),error);
        }
    }
}
