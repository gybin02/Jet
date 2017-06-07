package com.meiyou.jet.wpattern.exception;

public class InjectionException extends Exception {

	private static final long serialVersionUID = 810546994379030324L;

	public InjectionException() {
    }

    public InjectionException(String s) {
        super(s);
    }

    public InjectionException(Throwable throwable) {
        super(throwable);
    }
    
    public InjectionException(String s, Throwable throwable) {
        super(s, throwable);
    }

}