package org.backend.repository;

import org.backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    // Thêm phương thức tìm kiếm Role theo tên nếu cần thiết (ví dụ: để gán Role khi Đăng ký)
    Role findByName(String name);
}