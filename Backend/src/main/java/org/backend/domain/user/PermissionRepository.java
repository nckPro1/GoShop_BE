package org.backend.domain.user;

import java.util.Optional;

public interface PermissionRepository {

    Permission save(Permission permission);

    Optional<Permission> findById(Long id);

    Optional<Permission> findByName(String name);

    void delete(Permission permission);
}
