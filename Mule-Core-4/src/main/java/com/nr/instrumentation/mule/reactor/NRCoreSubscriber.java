package com.nr.instrumentation.mule.reactor;

import org.mule.runtime.core.api.event.CoreEvent;
import org.reactivestreams.Subscription;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.mule.core.MuleUtils;

import reactor.core.CoreSubscriber;

public class NRCoreSubscriber<T> implements CoreSubscriber<T> {
	
	private CoreSubscriber<T> delegate = null;
	private static boolean isTransformed = false;
	
	public NRCoreSubscriber(CoreSubscriber<T> cs) {
		
		delegate = cs;
		if(!isTransformed) {
			isTransformed = true;
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
		}
	}

	@Override
	@Trace(async=true)
	public void onNext(T t) {
		if(CoreEvent.class.isInstance(t)) {
			CoreEvent event = (CoreEvent)t;
			String corrId = event.getCorrelationId();
			if(MuleUtils.hasToken(corrId)) {
				Token token = MuleUtils.getToken(corrId);
				token.link();
			}
		}
		delegate.onNext(t);
	}

	@Override
	public void onError(Throwable t) {
		NewRelic.noticeError(t);
		delegate.onError(t);
	}

	@Override
	public void onComplete() {
		delegate.onComplete();
	}

	@Override
	public void onSubscribe(Subscription s) {
		delegate.onSubscribe(s);
	}

}
