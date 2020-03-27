package org.mule.service.http.impl.service.server;

import org.mule.runtime.http.api.server.HttpServer;
import org.mule.runtime.http.api.server.PathAndMethodRequestMatcher;
import org.mule.runtime.http.api.server.RequestHandler;
import org.mule.runtime.http.api.server.RequestHandlerManager;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class HttpListenerRegistry {

	@Trace
	public RequestHandlerManager addRequestHandler(final HttpServer server, final RequestHandler requestHandler,
			final PathAndMethodRequestMatcher requestMatcher) {
		return Weaver.callOriginal();
	}
}
