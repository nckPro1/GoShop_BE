package org.backend.service;

import org.backend.model.User;
import org.backend.model.UserPrincipal;
import org.backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Tìm kiếm User từ Database
        return userRepository.findByUsername(username)
                .map(UserPrincipal::new) // 2. Ánh xạ sang đối tượng UserPrincipal
                .orElseThrow(() ->
                        // 3. Ném ngoại lệ nếu không tìm thấy
                        new UsernameNotFoundException("User not found with username: " + username)
                );
    }
}