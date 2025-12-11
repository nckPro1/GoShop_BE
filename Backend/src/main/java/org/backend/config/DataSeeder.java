package org.backend.config;

import lombok.RequiredArgsConstructor;
import org.backend.model.Permission;
import org.backend.model.Role;
import org.backend.model.User;
import org.backend.repository.PermissionRepository;
import org.backend.repository.RoleRepository;
import org.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    // Lưu ý: Cần tạo RoleRepository và PermissionRepository trống để Bean này hoạt động

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            // 1. Tạo Permissions
            Permission readEmployee = permissionRepository.save(createPermission("EMPLOYEE_READ"));
            Permission createEmployee = permissionRepository.save(createPermission("EMPLOYEE_CREATE"));
            Permission updateSalary = permissionRepository.save(createPermission("SALARY_UPDATE"));

            // 2. Tạo Roles
            Role adminRole = createRole("ADMIN", Set.of(readEmployee, createEmployee, updateSalary));
            roleRepository.save(adminRole);

            Role hrRole = createRole("HR_STAFF", Set.of(readEmployee, createEmployee));
            roleRepository.save(hrRole);

            // 3. Tạo User (User: admin@test.com, Pass: 123456)
            User adminUser = new User();
            adminUser.setUsername("admin@test.com");
            adminUser.setPassword(passwordEncoder.encode("123456"));
            adminUser.setFullName("System Admin");
            adminUser.setRoles(Set.of(adminRole));
            userRepository.save(adminUser);

            System.out.println(">>> Data Seeding Hoàn thành. User test: admin@test.com / 123456");
        };
    }

    // Hàm tiện ích
    private Permission createPermission(String name) {
        Permission p = new Permission();
        p.setName(name);
        return p;
    }

    private Role createRole(String name, Set<Permission> permissions) {
        Role r = new Role();
        r.setName(name);
        r.setPermissions(permissions);
        return r;
    }
}