package com.example.caio.domain.enums;

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
    for (EnumRoles r : EnumRoles.values()) {
        if (r.role.equals(role)) {
            return r;
        }
    }
    throw new IllegalArgumentException("Unknown role: " + role);
}
}