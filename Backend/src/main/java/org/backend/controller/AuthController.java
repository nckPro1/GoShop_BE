package org.backend.controller;

import lombok.RequiredArgsConstructor;
import org.backend.dto.LoginRequest;
import org.backend.dto.LoginResponse; // Nhớ import cái này
import org.backend.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authService;

    // API Đăng nhập
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // 1. Lấy chuỗi JWT từ service
        String jwt = authService.authenticate(request);

        // 2. Gói nó vào đối tượng LoginResponse
        LoginResponse response = new LoginResponse(jwt);

        // 3. Trả về đối tượng JSON { "token": "..." }
        return ResponseEntity.ok(response);
    }
}