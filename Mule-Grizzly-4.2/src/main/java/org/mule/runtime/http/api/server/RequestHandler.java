package org.mule.runtime.http.api.server;

import org.mule.runtime.http.api.domain.request.HttpRequestContext;
import org.mule.runtime.http.api.server.async.HttpResponseReadyCallback;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class RequestHandler {

	@Trace(dispatcher=true)
	public void handleRequest(final HttpRequestContext requestContext, final HttpResponseReadyCallback responseCallback) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","RequestHandler",getClass().getSimpleName(),"handleRequest"});
		Weaver.callOriginal();
	}
}
