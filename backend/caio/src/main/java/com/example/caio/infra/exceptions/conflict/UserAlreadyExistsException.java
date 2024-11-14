package com.example.caio.infra.exceptions.conflict;

public class UserAlreadyExistsException extends RuntimeException  {
  public UserAlreadyExistsException(String message) {
      super(message);
  }
}