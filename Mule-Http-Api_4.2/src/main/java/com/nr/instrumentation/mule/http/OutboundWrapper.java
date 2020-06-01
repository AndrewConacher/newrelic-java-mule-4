package com.nr.instrumentation.mule.http;

import com.newrelic.api.agent.HeaderType;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import com.newrelic.api.agent.OutboundHeaders;

public class OutboundWrapper implements OutboundHeaders {

	private HttpRequest request = null;
	
	public OutboundWrapper(HttpRequest req) {
		request = req;
	}
	
	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	@Override
	public void setHeader(String name, String value) {
		request.getHeaders().put(name, value);
	}

}
