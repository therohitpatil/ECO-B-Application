package com.app.custom_excep;

@SuppressWarnings("serial")
public class MyCustomException extends RuntimeException {
	public MyCustomException(String msg) {
		super(msg);
	}
}
