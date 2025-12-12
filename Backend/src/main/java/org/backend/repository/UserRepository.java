package org.backend.repository;

import org.backend.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Phương thức tùy chỉnh để tìm kiếm User bằng username (email/mã nhân viên)
    Optional<User> findByUsername(String username);
}