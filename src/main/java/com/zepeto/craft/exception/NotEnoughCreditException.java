package com.zepeto.craft.exception;

/**
 * Credit 부족 Exception
 */
public class NotEnoughCreditException extends RuntimeException {
	public NotEnoughCreditException() {
		super();
	}

	public NotEnoughCreditException(String message) {
		super(message);
	}

	public NotEnoughCreditException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotEnoughCreditException(Throwable cause) {
		super(cause);
	}

}
