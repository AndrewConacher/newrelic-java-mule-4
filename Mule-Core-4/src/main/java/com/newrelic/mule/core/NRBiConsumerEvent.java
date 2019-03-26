package com.newrelic.mule.core;

import java.util.function.BiConsumer;

import org.mule.runtime.core.api.event.CoreEvent;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;

public class NRBiConsumerEvent implements BiConsumer<CoreEvent,Throwable> {
	
	private Token token = null;
	
	private static boolean isTransformed = false;
	
	
	public NRBiConsumerEvent(Token t) {
		token = t;
		if(!isTransformed) {
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
		}
	}

	@Override
	@Trace(async=true)
	public void accept(CoreEvent event, Throwable t) {
		if(event != null) {
			if(MuleUtils.hasToken(event)) {
				Token token2 = MuleUtils.getToken(event);
				if(token2 != null) {
					token2.expire();
					MuleUtils.removeToken(event);
				}
			}
		}
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		if(t != null) {
			NewRelic.noticeError(t);
		}
	}

}
