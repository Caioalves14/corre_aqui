package com.example.caio.infra.exceptions.conflict;

public class ProductAlreadyExistsException extends RuntimeException {
  public ProductAlreadyExistsException(String message) {
      super(message);
  }
}