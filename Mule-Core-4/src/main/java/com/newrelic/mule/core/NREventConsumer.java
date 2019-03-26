package com.newrelic.mule.core;

import java.util.function.Consumer;

import org.mule.runtime.core.api.event.CoreEvent;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;

public class NREventConsumer implements Consumer<CoreEvent> {
	
	private static boolean isTransformed = false;
	
	
	public NREventConsumer() {
		if(!isTransformed) {
			isTransformed = true;
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
		}
	}
	
	@Override
	@Trace(async=true)
	public void accept(CoreEvent t) {
		if(MuleUtils.hasToken(t)) {
			Token token = MuleUtils.getToken(t);
			token.linkAndExpire();
			MuleUtils.removeToken(t);
		}
	}

}
