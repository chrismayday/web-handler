package com.bluexiii.jwh.repository;

import com.bluexiii.jwh.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by bluexiii on 16/8/25.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByRoleName(String roleName);
}
