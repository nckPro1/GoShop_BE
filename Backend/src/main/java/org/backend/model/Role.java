package org.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Ví dụ: ROLE_ADMIN, ROLE_HR_STAFF, ROLE_EMPLOYEE
    @Column(unique = true, nullable = false)
    private String name;

    // Mối quan hệ Many-to-Many: Role có nhiều Permissions
    @ManyToMany(fetch = FetchType.EAGER) // Tải Permissions cùng lúc với Role
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;
}