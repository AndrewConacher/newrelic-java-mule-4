package com.nr.instrumentation.mulehttpruntime;

import java.util.Enumeration;

import org.mule.runtime.http.api.domain.message.request.HttpRequest;

import com.newrelic.api.agent.ExtendedRequest;
import com.newrelic.api.agent.HeaderType;

public class InboundWrapper extends ExtendedRequest {
	
	private HttpRequest request =  null;
	
	public InboundWrapper(HttpRequest req) {
		request = req;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	@Override
	public String getHeader(String name) {
		return request.getHeaderValue(name);
	}

	@Override
	public String getRequestURI() {
		return request.getUri().toASCIIString();
	}

	@Override
	public String getRemoteUser() {
		return null;
	}

	@Override
	public Enumeration getParameterNames() {
		return null;
	}

	@Override
	public String[] getParameterValues(String name) {
		return null;
	}

	@Override
	public Object getAttribute(String name) {
		return null;
	}

	@Override
	public String getCookieValue(String name) {
		return null;
	}

	@Override
	public String getMethod() {
		return request.getMethod();
	}

}
