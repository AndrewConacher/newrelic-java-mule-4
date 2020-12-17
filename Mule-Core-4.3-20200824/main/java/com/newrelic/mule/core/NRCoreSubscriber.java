package com.newrelic.mule.core;


import java.util.logging.Level;

import org.mule.runtime.core.api.event.CoreEvent;
import org.mule.runtime.core.internal.event.MuleUtils;
import org.reactivestreams.Subscription;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;

import reactor.core.CoreSubscriber;
import reactor.util.context.Context;

public class NRCoreSubscriber<T> implements CoreSubscriber<T> {

	private CoreSubscriber<T> coreSubscriber;
	private static boolean isTransformed = false;

	public NRCoreSubscriber(CoreSubscriber<T> c, String rType) {
		coreSubscriber= c;
		if(!isTransformed) {
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
			isTransformed = true;
		}
	}

	@Override
	@Trace(async=true,excludeFromTransactionTrace=true)
	public void onNext(T t) {
		boolean linked = false;
		boolean notCoreEvent = true;
		String metricName = NewRelic.getAgent().getTracedMethod().getMetricName();
		boolean inTransaction = !metricName.toLowerCase().contains("noop");
		
		if(t instanceof CoreEvent) {
			notCoreEvent = false;
			CoreEvent e = (CoreEvent)t;
			Token token = MuleUtils.getToken(e);
			if(token != null) {
				token.link();
				linked = true;
			} else {
				token = NewRelic.getAgent().getTransaction().getToken();
				if(token != null && token.isActive()) {
					MuleUtils.setToken(e, token);
					inTransaction = true;
				} else {
					if(token != null) {
						token.expire();
						token = null;
					}
				}
			}
		}
//		NewRelic.getAgent().getLogger().log(Level.FINE, "call to NRCoreSubsriber.onNext({0}), linked: {1}, noCoreEvent: {2}, inTransaction: {3}", t, linked, notCoreEvent, inTransaction);
		coreSubscriber.onNext(t);
	}

	@Override
	@Trace(async=true,excludeFromTransactionTrace=true)
	public void onError(Throwable t) {
		NewRelic.noticeError(t);
		coreSubscriber.onError(t);
	}

	@Override
	@Trace(async=true,excludeFromTransactionTrace=true)
	public void onComplete() {
		coreSubscriber.onComplete();
	}

	@Override
	public void onSubscribe(Subscription s) {
//		TracedMethod t = NewRelic.getAgent().getTracedMethod();
//		boolean linked = t.getMetricName().toLowerCase().contains("noop");
//		NewRelic.getAgent().getLogger().log(Level.FINE,new Exception("call to NRCoreSubscriber.onSubscribe"), "call to NRCoreSubsriber.onSubscribe({0}), linked: {1}", s, linked);
		coreSubscriber.onSubscribe(s);
	}

	@Override
	public Context currentContext() {
		return coreSubscriber.currentContext();
	}

}
