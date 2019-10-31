package com.newrelic.mule.core;

import java.util.function.Consumer;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;

public class NRErrorConsumer implements Consumer<Throwable> {

	private NRHolder holder = null;
	
	private static boolean isTransformed = false;
	
	
	public NRErrorConsumer(NRHolder h) {
		holder = h;
		if(!isTransformed) {
			isTransformed = true;
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
		}
	}

	@Override
	@Trace(async=true)
	public void accept(Throwable t) {
		NewRelic.noticeError(t);
		if(holder != null && holder.hasToken()) {
			Token token = holder.getToken();
			if(token != null) {
				token.linkAndExpire();
				token = null;
			}
		}
	}

}
