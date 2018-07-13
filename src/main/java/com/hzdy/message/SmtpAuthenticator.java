package com.hzdy.message;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SmtpAuthenticator extends Authenticator {

	String username = null;
	String password = null;

	public SmtpAuthenticator(String name, String password) {
		super();
		this.username = name;
		this.password = password;
	}

	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(this.username, this.password);
	}

}
