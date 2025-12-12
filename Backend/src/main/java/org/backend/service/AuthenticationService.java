package org.backend.service;

import lombok.RequiredArgsConstructor;
import org.backend.dto.LoginRequest;
import org.backend.model.User;
import org.backend.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

        // 1. Xác thực thông tin đăng nhập
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // 2. Nếu xác thực thành công, tải UserDetails
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        // 3. Tạo JWT (dùng username và Authorities/Permissions)
        // Lưu ý: Các Authorities (Permissions) đã được tải trong UserPrincipal (Bước 5)
        return jwtService.generateToken(userDetails);
    }
}