package com.nr.instrumentation.mulehttpruntime;

import org.mule.runtime.http.api.domain.message.response.HttpResponse;
import org.mule.runtime.http.api.server.async.HttpResponseReadyCallback;
import org.mule.runtime.http.api.server.async.ResponseStatusCallback;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;

public class NRHttpResponseReadyCallback implements HttpResponseReadyCallback {
	
	private HttpResponseReadyCallback delegate = null;
	
	private Token token = null;
	
	private static boolean isTransformed = false;
	

	public NRHttpResponseReadyCallback(HttpResponseReadyCallback d, Token t) {
		if(!isTransformed) {
			isTransformed = true;
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
		}
		this.delegate = d;
		this.token = t;
	}



	@Override
	@Trace(async=true)
	public void responseReady(HttpResponse response, ResponseStatusCallback responseStatusCallback) {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		NewRelic.getAgent().getTransaction().setWebResponse(new OutboundWrapper(response));
		delegate.responseReady(response, responseStatusCallback);
	}

}
