package com.newrelic.mule.core;

import java.util.function.Function;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Trace;

public class NRWrapperFunction<T,U> implements Function<T, U> {

	private Function<T,U> delegate = null;

	private static boolean isTransformed = false;

	public NRWrapperFunction(Function<T,U> f) {
		delegate = f;
		if(!isTransformed) {
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
			isTransformed = true;
		}
	}

	@Override
	@Trace(async=true)
	public U apply(T t) {

		U u = delegate.apply(t);

		return u;
	}

}
