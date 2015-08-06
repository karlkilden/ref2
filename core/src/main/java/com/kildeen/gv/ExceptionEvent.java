package com.kildeen.gv;

public class ExceptionEvent {

	public ExceptionEvent(Throwable throwable) {
		this.throwable = throwable;
	}

	private Throwable throwable;

	public Throwable getThrowable() {
		return throwable;
	}
}
