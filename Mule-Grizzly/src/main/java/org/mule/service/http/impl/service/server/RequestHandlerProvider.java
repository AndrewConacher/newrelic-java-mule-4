package org.mule.service.http.impl.service.server;

import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.server.RequestHandler;
import org.mule.runtime.http.api.server.ServerAddress;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class RequestHandlerProvider {

	@Trace(dispatcher=true)
	public RequestHandler getRequestHandler(final ServerAddress serverAddress, final HttpRequest request) {
		return Weaver.callOriginal();
	}
}
