package org.mule.runtime.http.api.server;

import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.request.HttpRequestContext;
import org.mule.runtime.http.api.server.async.HttpResponseReadyCallback;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.mulehttp.InboundWrapper;

@Weave(type=MatchType.Interface)
public abstract class RequestHandler {

	@Trace(dispatcher=true)
	public void handleRequest(HttpRequestContext requestContext, HttpResponseReadyCallback responseCallback) {
		if(requestContext != null) {
			HttpRequest request = requestContext.getRequest();
			if(request != null) {
				InboundWrapper wrapper = new InboundWrapper(request);
				NewRelic.getAgent().getTransaction().setWebRequest(wrapper);
			}
		}
		if(responseCallback != null) {
			responseCallback.token = NewRelic.getAgent().getTransaction().getToken();
		}
		Weaver.callOriginal();
	}
}
