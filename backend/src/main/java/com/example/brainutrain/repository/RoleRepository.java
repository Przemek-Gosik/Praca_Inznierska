package com.example.brainutrain.repository;

import com.example.brainutrain.constants.RoleName;
import com.example.brainutrain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByRoleName(RoleName roleName);
}
