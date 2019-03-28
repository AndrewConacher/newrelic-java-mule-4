package org.mule.service.http.impl.service.server.grizzly;

import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.HttpResponsePacket;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.BaseClass)
public abstract class BaseResponseCompletionHandler {
	
	@NewField
	private Token token = null;
	
	@Trace(async=true)
	public void cancelled() {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		Weaver.callOriginal();
	}
	
	
	@Trace(async=true)
	public void failed(final Throwable throwable) {
		NewRelic.noticeError(throwable);
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		Weaver.callOriginal();
	}
	
	protected HttpResponsePacket buildHttpResponsePacket(final HttpRequestPacket sourceRequest,final HttpResponse httpResponse) {
		HttpResponsePacket responsepacket = Weaver.callOriginal();
		NewRelic.getAgent().getTransaction().setWebResponse(new InboundWrapper(responsepacket));
		return responsepacket;
	}
}
