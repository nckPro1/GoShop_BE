package org.backend.repository;

import org.backend.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    // Thêm phương thức tìm kiếm Permission theo tên nếu cần thiết
    Permission findByName(String name);
}