package com.example.caio.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.caio.domain.enums.EnumRoles;
import com.example.caio.model.Role;

@Repository
public interface IRolesRepository extends JpaRepository<Role,UUID> {
    Optional<Role> findByEnumRoles(EnumRoles name);
}
