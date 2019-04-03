package org.mule.service.http.impl.service.server.grizzly;

import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.http.HttpContent;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.mule.service.http.impl.service.server.RequestHandlerProvider;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.Transaction;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.mule.http.InboundRequest;
 
@Weave
public abstract class GrizzlyRequestDispatcherFilter {
	
	private final RequestHandlerProvider requestHandlerProvider = Weaver.callOriginal();
	
	GrizzlyRequestDispatcherFilter(RequestHandlerProvider requestHandlerProvider) {
	}

	@Trace(dispatcher=true)
	public NextAction handleRead(final FilterChainContext ctx) {
		Transaction txn = NewRelic.getAgent().getTransaction();
		if(!txn.isWebTransaction()) {
			txn.convertToWebTransaction();
		}
		if(ctx.getMessage() instanceof HttpContent) {
			HttpContent httpContent = (HttpContent) ctx.getMessage();
			HttpRequestPacket request = (HttpRequestPacket) httpContent.getHttpHeader();
			InboundRequest wrapper = new InboundRequest(request);
			txn.setWebRequest(wrapper);
		}
		return Weaver.callOriginal();
	}
}