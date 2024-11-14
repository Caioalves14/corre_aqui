package com.example.caio.domain.dto.role;

import com.example.caio.domain.enums.EnumRoles;
import com.example.caio.model.Role;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRoleDTO {

    @NotBlank
    private EnumRoles roleName;

    public UserRoleDTO(Role role) {
        this.roleName = role.getEnumRoles();
    }
}