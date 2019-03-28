package com.nr.instrumentation.mulehttpruntime;

import org.mule.runtime.http.api.domain.message.response.HttpResponse;

import com.newrelic.api.agent.ExtendedResponse;
import com.newrelic.api.agent.HeaderType;

public class OutboundWrapper extends ExtendedResponse {
	
	private HttpResponse response = null;
	
	public OutboundWrapper(HttpResponse resp) {
		response = resp;
	}

	@Override
	public int getStatus() throws Exception {
		return response.getStatusCode();
	}

	@Override
	public String getStatusMessage() throws Exception {
		return response.getReasonPhrase();
	}

	@Override
	public String getContentType() {
		return null;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	@Override
	public void setHeader(String name, String value) {
	}

	@Override
	public long getContentLength() {
		return 0;
	}

}
