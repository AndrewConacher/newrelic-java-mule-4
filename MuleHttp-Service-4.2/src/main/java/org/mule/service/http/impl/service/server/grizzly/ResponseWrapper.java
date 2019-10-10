package org.mule.service.http.impl.service.server.grizzly;

import com.newrelic.api.agent.ExtendedResponse;
import com.newrelic.api.agent.HeaderType;
import org.glassfish.grizzly.http.HttpResponsePacket;

public class ResponseWrapper extends ExtendedResponse {
	
	HttpResponsePacket response = null;

	public ResponseWrapper(HttpResponsePacket response) {
		super();
		this.response = response;
	}

	@Override
	public int getStatus() throws Exception {
		return response.getStatus();
	}

	@Override
	public String getStatusMessage() throws Exception {
		return response.getReasonPhrase();
	}

	@Override
	public String getContentType() {
		return response.getContentType();
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	@Override
	public void setHeader(String name, String value) {
		response.setHeader(name, value);
	}

	@Override
	public long getContentLength() {
		return response.getContentLength();
	}

}
