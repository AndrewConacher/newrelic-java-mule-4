package org.mule.runtime.http.api.server.async;

import org.mule.runtime.http.api.domain.message.response.HttpResponse;

import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class HttpResponseReadyCallback {
	
	@NewField
	public Token token = null;

	@Trace(async=true)
	public void responseReady(HttpResponse response, ResponseStatusCallback responseStatusCallback) {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		Weaver.callOriginal();
	}
}
