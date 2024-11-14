package com.example.caio.infra.exceptions.notFound;

public class RoleNotFoundException extends RuntimeException {
  public RoleNotFoundException(String message) {
      super(message);
  }
}