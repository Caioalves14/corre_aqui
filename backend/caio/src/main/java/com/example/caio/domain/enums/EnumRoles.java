package com.example.caio.domain.enums;

import com.example.caio.infra.exceptions.notFound.RoleNotFoundException;

public enum EnumRoles {

  ROLE_ADMIN("ROLE_ADMIN"),
  ROLE_USER("ROLE_USER");

  private String role;

  EnumRoles(String role) {
      this.role = role;
  }
  public String getRole() {
      return role;
  }

  public static EnumRoles fromString(String role) {
    System.out.println("Attempting to find role: '" + role + "'");
    for (EnumRoles r : EnumRoles.values()) {
        if (r.role.equalsIgnoreCase(role)) { 
            return r;
        }
    }
    throw new RoleNotFoundException("Error: Role '" + role + "' not Found.");
}


}