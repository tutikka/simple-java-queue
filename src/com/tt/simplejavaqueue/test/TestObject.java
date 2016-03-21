package com.tt.simplejavaqueue.test;

import java.io.Serializable;

public class TestObject implements Serializable {
	
	private static final long serialVersionUID = 1L;

	// field which is serialized
	private String message;

	// field which is not serialized
	private transient String transientMessage;
	
	public TestObject() {
	}

	public TestObject(String message, String transientMessage) {
		this.message = message;
		this.transientMessage = transientMessage;
	}	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTransientMessage() {
		return transientMessage;
	}

	public void setTransientMessage(String transientMessage) {
		this.transientMessage = transientMessage;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ message: ");
		sb.append(message);
		sb.append(", transientMessage: ");
		sb.append(transientMessage);
		sb.append(" }");
		return (sb.toString());
	}
	
}
