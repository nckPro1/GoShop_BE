package org.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "permissions")
@Data
@NoArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Ví dụ: EMPLOYEE_READ, SALARY_UPDATE, ROLE_CREATE
    @Column(unique = true, nullable = false)
    private String name;
}