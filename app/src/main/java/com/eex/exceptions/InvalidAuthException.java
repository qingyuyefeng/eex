package com.eex.exceptions;

public class InvalidAuthException extends IllegalAccessException {

  public InvalidAuthException(String msg) {
    super(msg);
  }
}
