package org.backend.service;

import lombok.RequiredArgsConstructor;
import org.backend.dto.LoginRequest;
import org.backend.model.Permission;
import org.backend.model.Role;
import org.backend.model.User;
import org.backend.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService; // Để lấy UserDetails sau khi xác thực

    // Hàm Đăng ký (Register) - Tùy chọn, thêm vào để tạo user ban đầu
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Gán Role mặc định khi đăng ký nếu cần
        return userRepository.save(user);
    }

    // Hàm Đăng nhập (Authenticate)
    public String authenticate(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        User user = userRepository.findByUsername(request.getUsername()).get();

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("roles", user.getRoles().stream().map(Role::getName).toList());
        extraClaims.put("permissions", user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(Permission::getName)
                .toList()
        );

        return jwtService.generateToken(extraClaims, userDetails);
    }

}