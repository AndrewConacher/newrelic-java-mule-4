package org.mule.service.http.impl.service.server.grizzly;


import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.WeaveAllConstructors;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class ResponseStreamingCompletionHandler {

	@NewField
	private Token token = null;
	
	@WeaveAllConstructors
	public ResponseStreamingCompletionHandler() {
		token = NewRelic.getAgent().getTransaction().getToken();
	}
	
	@Trace(async=true)
	public void completed(org.glassfish.grizzly.WriteResult result) {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		Weaver.callOriginal();
	}
	
	@Trace(async=true)
	public void cancelled() {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		Weaver.callOriginal();
	}
	
	
	@Trace(async=true)
	public void failed(Throwable throwable) {
		NewRelic.noticeError(throwable);
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		Weaver.callOriginal();
	}

}
