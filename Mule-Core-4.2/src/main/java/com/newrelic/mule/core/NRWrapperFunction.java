package com.newrelic.mule.core;

import java.util.function.Function;
import java.util.logging.Level;

import org.mule.runtime.core.api.event.CoreEvent;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;

import reactor.core.publisher.Flux;

public class NRWrapperFunction<T,U> implements Function<T, U> {

	private Function<T,U> delegate = null;

	private static boolean isTransformed = false;

	public NRWrapperFunction(Function<T,U> f) {
		NewRelic.getAgent().getLogger().log(Level.FINE, "Constructing NRWrapperFunction.<init>({0})", f);
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
		NewRelic.getAgent().getLogger().log(Level.FINE, "Call to NRWrapperFunction.apply({0}), delegate {1} returns {2}", t,delegate,u);
		if(Flux.class.isInstance(u)) {
			Flux<CoreEvent> flux = (Flux<CoreEvent>)u;
			NRFlux nrFlux = new NRFlux(flux);
			u = (U)nrFlux;
		}
		return u;
	}

}
