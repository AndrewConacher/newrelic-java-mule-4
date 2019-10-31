package com.newrelic.mule.core;

import java.util.function.Consumer;

import org.mule.runtime.core.api.event.CoreEvent;
import org.mule.runtime.core.internal.event.MuleUtils;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;

public class NREventConsumer2 implements Consumer<CoreEvent> {
	
	private static boolean isTransformed = false;
	
	private NRHolder holder = null;
	
	public NREventConsumer2(NRHolder h) {
		holder = h;
	}
	
	public NREventConsumer2() {
		if(!isTransformed) {
			isTransformed = true;
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
		}
	}
	
	@Override
	@Trace(async=true)
	public void accept(CoreEvent event) {
		
		Token token = null;
		if(holder != null && holder.hasToken()) {
			token = holder.getToken();
		} else {
			token = MuleUtils.getToken(event);
		}
		if(token != null) {
			token.link();
		}
		token = null;
	}

}
