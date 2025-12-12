package org.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Thông tin Xác thực
    @Column(unique = true, nullable = false)
    private String username; // Dùng làm email hoặc mã nhân viên

    @Column(nullable = false)
    private String password;

    // Thông tin HR (có thể tách ra Entity Employee riêng)
    private String fullName;
    private String employeeCode;
    private boolean isAccountNonLocked = true;

    // Mối quan hệ Many-to-Many: User có nhiều Roles
    @ManyToMany(fetch = FetchType.EAGER) // Tải Roles cùng lúc với User
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
}