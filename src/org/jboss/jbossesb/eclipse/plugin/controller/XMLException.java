package org.jboss.jbossesb.eclipse.plugin.controller;

public class XMLException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public XMLException(String msg) {
		super(msg);
	}
	
	public XMLException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public XMLException(Throwable cause) {
		super(cause);
	}
}
