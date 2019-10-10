package org.mule.service.http.impl.service.server.grizzly;


import org.glassfish.grizzly.http.HttpResponsePacket;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.WeaveAllConstructors;
import com.newrelic.api.agent.weaver.Weaver;

@SuppressWarnings("rawtypes")
@Weave
public abstract class ResponseCompletionHandler {

	@NewField
	private Token token = null;

	private final HttpResponsePacket httpResponsePacket = Weaver.callOriginal();


	@WeaveAllConstructors
	public ResponseCompletionHandler() {
		token = NewRelic.getAgent().getTransaction().getToken();
	}
	
	@Trace(async=true)
	public void completed(org.glassfish.grizzly.WriteResult result) {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		NewRelic.getAgent().getTransaction().setWebResponse(new ResponseWrapper(httpResponsePacket));
		Weaver.callOriginal();
	}
	
	@Trace(async=true)
	public void cancelled() {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		NewRelic.getAgent().getTransaction().setWebResponse(new ResponseWrapper(httpResponsePacket));
		Weaver.callOriginal();
	}
	
	
	@Trace(async=true)
	public void failed(Throwable throwable) {
		NewRelic.noticeError(throwable);
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		NewRelic.getAgent().getTransaction().setWebResponse(new ResponseWrapper(httpResponsePacket));
		Weaver.callOriginal();
	}

}
