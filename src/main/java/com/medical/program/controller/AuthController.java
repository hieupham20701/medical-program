package com.medical.program.controller;

import com.medical.program.dto.request.EmployeeRequest;
import com.medical.program.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class AuthController {

    private final AuthService authService;
    @PostMapping(value = "/register", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> register(@RequestBody EmployeeRequest employeeRequest){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/auth/register").toUriString());
        try {
            return ResponseEntity.created(uri).body(authService.register(employeeRequest));
        }catch (Exception e){
            Map<String,String> error = new HashMap<>();
            error.put("error_message",e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }


}
