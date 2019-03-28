package com.nr.instrumentation.mule.reactor;

import org.mule.runtime.core.api.event.CoreEvent;
import org.reactivestreams.Subscription;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.mule.core.MuleUtils;

import reactor.core.CoreSubscriber;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class NRCoreSubscriber<T> implements CoreSubscriber<T> {
	
	private CoreSubscriber<T> delegate = null;
	private static boolean isTransformed = false;
	
	public static CoreSubscriber<?> getSubscriber(CoreSubscriber<?> cs) {
		Class<?> csClass = cs.getClass();
		String classname = csClass.getName();
		if(classname.contains("AbstractMessageProcessorChain")) {
			return new NRCoreSubscriber(cs);
		} else if(classname.isEmpty()) {
			String tmp = cs.toString();
			if(tmp.contains("AbstractMessageProcessorChain")) {
				return new NRCoreSubscriber(cs);
			}
		}
		return null;
	}
	
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
			if(MuleUtils.hasToken(event)) {
				Token token = MuleUtils.getToken(event);
				token.link();
			}
		}
		delegate.onNext(t);
	}

	@Override
	public void onError(Throwable t) {
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
