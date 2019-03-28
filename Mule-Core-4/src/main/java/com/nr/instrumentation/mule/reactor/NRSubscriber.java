package com.nr.instrumentation.mule.reactor;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

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
