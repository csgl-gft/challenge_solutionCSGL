package com.db.awmd.challenge.domain;

public class Status {
	
	private boolean ok;
	private String validationMessage;
	
	public Status(boolean ok, String validationMessage) {
		super();
		this.ok = ok;
		this.validationMessage = validationMessage;
	}

	public boolean isOk() {
		return ok;
	}

	public String getValidationMessage() {
		return validationMessage;
	}
}
