package com.nr.instrumentation.mule.reactor;

import java.util.logging.Level;

import org.mule.runtime.api.message.Message;
import org.mule.runtime.core.api.event.CoreEvent;
import org.mule.runtime.core.internal.event.DefaultEventBuilder;
import org.mule.runtime.core.internal.event.EventQuickCopy;
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
	
	public static CoreSubscriber<?> getSubscriber(CoreSubscriber<?> cs) {
		Class<?> csClass = cs.getClass();
		String classname = csClass.getName();
		NewRelic.getAgent().getLogger().log(Level.FINE, "in NRCoreSubscriber.getSubscriber({0}), checking name against {1}", cs, classname);
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
		Exception e = new Exception("NRCoreSubscriber.<init>");
		NewRelic.getAgent().getLogger().log(Level.FINE, e, "In NRCoreSubscriber.<init>({0}",cs);
		
		delegate = cs;
		if(!isTransformed) {
			isTransformed = true;
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
		}
	}

	@Override
	@Trace(async=true)
	public void onNext(T t) {
		Exception e = new Exception("Call to NRCoreSubscriber");
		NewRelic.getAgent().getLogger().log(Level.FINE, e, "In NRCoreSubscriber.onNext, input class: {0}, input value: {1}",t.getClass().getName(),t);
		if(CoreEvent.class.isInstance(t)) {
			NewRelic.getAgent().getLogger().log(Level.FINE, "Found CoreEvent from onNext");
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
