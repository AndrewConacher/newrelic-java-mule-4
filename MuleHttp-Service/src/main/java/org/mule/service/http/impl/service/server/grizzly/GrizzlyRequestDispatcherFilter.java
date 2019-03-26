package org.mule.service.http.impl.service.server.grizzly;

import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.http.HttpContent;
import org.glassfish.grizzly.http.HttpRequestPacket;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Segment;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.Transaction;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.mule.http.InboundRequest;
import com.newrelic.instrumentation.mule.http.NRCompletionHandler;

@Weave
public abstract class GrizzlyRequestDispatcherFilter {

	@Trace(dispatcher=true)
	public NextAction handleRead(final FilterChainContext ctx) {
//		Token token = NewRelic.getAgent().getTransaction().getToken();
//		Segment segment = NewRelic.getAgent().getTransaction().startSegment("RequestProcessing");
//		NRCompletionHandler ch = new NRCompletionHandler(token, segment);
//		ctx.addCompletionListener(ch);
 		HttpContent httpContent = (HttpContent) ctx.getMessage();
		HttpRequestPacket request = (HttpRequestPacket) httpContent.getHttpHeader();
		Transaction transaction = NewRelic.getAgent().getTransaction();
		if(!transaction.isWebTransaction()) {
			transaction.convertToWebTransaction();
		}
		transaction.setWebRequest(new InboundRequest(request));
		NextAction nextAction = Weaver.callOriginal();
		return nextAction;
	}
}
