package org.backend.controller;

import lombok.RequiredArgsConstructor;
import org.backend.dto.LoginRequest;
import org.backend.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth") // Đã cho phép public trong SecurityConfig
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authService;

    // API Đăng nhập
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String jwt = authService.authenticate(request);
        return ResponseEntity.ok(jwt); // Trả về JWT
    }

    // API Đăng ký (nếu cần)
    // @PostMapping("/register")
    // public ResponseEntity<User> register(@RequestBody User user) {
    //     User registeredUser = authService.register(user);
    //     return ResponseEntity.ok(registeredUser);
    // }
}