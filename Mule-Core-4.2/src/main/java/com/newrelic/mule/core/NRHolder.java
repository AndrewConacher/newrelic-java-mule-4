package com.newrelic.mule.core;

import com.newrelic.api.agent.Token;

public class NRHolder {

	
	private Token token = null;
	
	public NRHolder(Token t) {
		token = t;
	}
	
	public boolean hasToken() {
		return token != null;
	}
	
	public Token getToken() {
		Token tmp = token;
		
		token = null;
		
		return tmp;
	}
}
