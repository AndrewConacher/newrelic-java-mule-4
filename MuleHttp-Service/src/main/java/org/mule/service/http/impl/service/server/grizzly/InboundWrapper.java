package org.mule.service.http.impl.service.server.grizzly;

import org.glassfish.grizzly.http.HttpResponsePacket;
import org.glassfish.grizzly.http.util.Header;

import com.newrelic.api.agent.ExtendedResponse;
import com.newrelic.api.agent.HeaderType;

public class InboundWrapper extends ExtendedResponse {

	private HttpResponsePacket response = null;
	
	public InboundWrapper(HttpResponsePacket response) {
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
		response.addHeader(name, value);
	}

	@Override
	public long getContentLength() {
		String header = response.getHeader(Header.ContentLength);
		if(header != null && !header.isEmpty()) {
			Long length = null;
			try {
				length = Long.parseLong(header);
			} catch(NumberFormatException e) {
				
			}
			if(length != null) {
				return length;
			}
		}
		return 0;
	}

}
