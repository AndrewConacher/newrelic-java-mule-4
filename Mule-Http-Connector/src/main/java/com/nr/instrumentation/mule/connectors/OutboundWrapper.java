package com.nr.instrumentation.mule.connectors;

import org.mule.runtime.api.util.MultiMap;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.OutboundHeaders;

public class OutboundWrapper implements OutboundHeaders {
	
	MultiMap<String, String> headers = null;
	
	public OutboundWrapper(MultiMap<String, String> h) {
		headers = new MultiMap<String, String>(h);
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	@Override
	public void setHeader(String name, String value) {
		if(headers == null) {
			headers = new MultiMap<String, String>();
		}
		headers.put(name, value);
	}

	public MultiMap<String, String> getHeaders() {
		return headers;
	}
}
