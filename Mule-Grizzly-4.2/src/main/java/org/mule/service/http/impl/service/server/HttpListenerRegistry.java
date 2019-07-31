package org.mule.service.http.impl.service.server;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class HttpListenerRegistry {

	@Trace(dispatcher=true)
	public org.mule.runtime.http.api.server.RequestHandler getRequestHandler(org.mule.runtime.http.api.server.ServerAddress serverAddress, org.mule.runtime.http.api.domain.message.request.HttpRequest request){
		return Weaver.callOriginal();
	}
}
