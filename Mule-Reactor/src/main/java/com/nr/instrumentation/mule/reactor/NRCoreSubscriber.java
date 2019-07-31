package com.nr.instrumentation.mule.reactor;

import java.util.logging.Level;

import org.mule.runtime.core.api.event.CoreEvent;
import org.reactivestreams.Subscription;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;

import reactor.core.CoreSubscriber;

public class NRCoreSubscriber<T> implements CoreSubscriber<T> {
	
	private CoreSubscriber<T> delegate = null;
	private static boolean isTransformed = false;
	
	
	
	public NRCoreSubscriber(CoreSubscriber<T> cs) {
		Exception e = new Exception("NRCoreSubscriber.<init>");
		NewRelic.getAgent().getLogger().log(Level.FINE, e, "In NRCoreSubscriber.<init>({0}",cs);
		
		delegate = cs;
		if(!isTransformed) {
			isTransformed = true;
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
		}
	}

	@Override
	public void onNext(T t) {
		Exception e = new Exception("Call to NRCoreSubscriber");
		NewRelic.getAgent().getLogger().log(Level.FINE, e, "In NRCoreSubscriber.onNext, input class: {0}, input value: {1}",t.getClass().getName(),t);
		if(CoreEvent.class.isInstance(t)) {
			NewRelic.getAgent().getLogger().log(Level.FINE, "Found CoreEvent from onNext");
			CoreEvent event = (CoreEvent)t;
			
		}
		delegate.onNext(t);
	}

	@Override
	public void onError(Throwable t) {
		Exception e = new Exception("Call to NRCoreSubscriber");
		NewRelic.getAgent().getLogger().log(Level.FINE, e, "In NRCoreSubscriber.onError({0}",t);
		delegate.onError(t);
		
	}

	@Override
	public void onComplete() {
		Exception e = new Exception("Call to NRCoreSubscriber");
		NewRelic.getAgent().getLogger().log(Level.FINE, e, "In NRCoreSubscriber.onComplete({0}");
		delegate.onComplete();
		
	}

	@Override
	public void onSubscribe(Subscription s) {
		Exception e = new Exception("Call to NRCoreSubscriber");
		NewRelic.getAgent().getLogger().log(Level.FINE, e, "In NRCoreSubscriber.onSubscribe({0}",s);
		delegate.onSubscribe(s);
	}

}
