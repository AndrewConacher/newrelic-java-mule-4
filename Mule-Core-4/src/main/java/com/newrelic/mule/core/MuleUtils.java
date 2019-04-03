package com.newrelic.mule.core;

import java.util.HashMap;
import java.util.logging.Level;

import com.newrelic.agent.bridge.NoOpToken;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;

public class MuleUtils {

	private static HashMap<String,Token> tokenCache = new HashMap<String, Token>();
	
	public static boolean hasToken(String corrId) {
		boolean result = tokenCache.containsKey(corrId);
		return result;
	}
	
	public static Token getToken(String corrId) {
		Token token = tokenCache.get(corrId);
		return token;
	}
	
	public static void addToken(String corrId, Token token) {
		if(NoOpToken.class.isInstance(token)) {
			return;
		}
		tokenCache.put(corrId, token);
	}
	
	public static Token removeToken(String corrId) {
		Token token = tokenCache.remove(corrId);
		
		return token;
	}
	
	
}
