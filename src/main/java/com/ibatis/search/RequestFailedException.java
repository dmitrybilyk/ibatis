package com.ibatis.search;

public class RequestFailedException extends Exception {
  private static final long serialVersionUID = 5268782056951587390L;

  public RequestFailedException() {
    super();
  }

  public RequestFailedException(String message) {
		super(message);
	}

	public RequestFailedException(String message, Throwable cause) {
		super(message, cause);
	}
}
