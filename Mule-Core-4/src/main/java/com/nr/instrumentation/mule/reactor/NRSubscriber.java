package com.nr.instrumentation.mule.reactor;

import java.util.logging.Level;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.newrelic.api.agent.NewRelic;

public class NRSubscriber<T> implements Subscriber<T> {
	
	private Subscriber<? super T> delegate = null;
	
	public NRSubscriber(Subscriber<? super T> s) {
		delegate = s;
	}

	@Override
	public void onSubscribe(Subscription s) {
		delegate.onSubscribe(s);
	}

	@Override
	public void onNext(T t) {
		NewRelic.getAgent().getLogger().log(Level.FINE, new Exception("Call to NRSubscriber.onNext"), "Call to NRSubscriber.onNext({0} using delegate: {1}",t,delegate);
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

}
