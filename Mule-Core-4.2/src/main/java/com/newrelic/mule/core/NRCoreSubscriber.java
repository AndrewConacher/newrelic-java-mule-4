package com.newrelic.mule.core;

import java.util.logging.Level;

import org.mule.runtime.api.event.EventContext;
import org.mule.runtime.core.api.event.CoreEvent;
import org.mule.runtime.core.internal.event.MuleUtils;
import org.mule.runtime.core.privileged.event.BaseEventContext;
import org.reactivestreams.Subscription;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;

import reactor.core.CoreSubscriber;

public class NRCoreSubscriber implements CoreSubscriber<CoreEvent> {
	
	private CoreSubscriber<? super CoreEvent> delegate = null;
	
	private static boolean isTransformed = false;
	
	public NRCoreSubscriber(CoreSubscriber<? super CoreEvent> d) {
		delegate = d;
		if(!isTransformed) {
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
			isTransformed = true;
		}
	}

	@Override
	@Trace(async=true)
	public void onNext(CoreEvent t) {
		Token token = MuleUtils.getToken(t);
		
		if(token != null) {
			token.link();
		}
		NewRelic.getAgent().getLogger().log(Level.FINE, "in NRCoreSubscriber.onNext({0}), token = {1}",t,token);
	
		delegate.onNext(t);
	}

	@Override
	@Trace(async=true)
	public void onError(Throwable t) {
		delegate.onError(t);
	}

	@Override
	@Trace(async=true)
	public void onComplete() {
		delegate.onComplete();
	}

	@Override
	public void onSubscribe(Subscription s) {
		delegate.onSubscribe(s);
	}

}
