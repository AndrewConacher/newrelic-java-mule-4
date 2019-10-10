package com.newrelic.mule.core;

import java.util.logging.Level;

import org.mule.runtime.core.api.event.CoreEvent;

import com.newrelic.api.agent.NewRelic;

import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;

public class NRFlux extends Flux<CoreEvent> {

	Flux<CoreEvent> delegate = null;

	public NRFlux(Flux<CoreEvent> d) {
		delegate = d;
	}


	@Override
	public void subscribe(CoreSubscriber<? super CoreEvent> actual) {
		Exception e = new Exception("call to subscribe");
		NewRelic.getAgent().getLogger().log(Level.FINE,e, "call to NRFlux.subscribe({0})", actual);
		if(!NRCoreSubscriber.class.isInstance(actual)) {
			NRCoreSubscriber wrapper = new NRCoreSubscriber(actual);
			delegate.subscribe(wrapper);
		} else {
			delegate.subscribe(actual);
		}
	}

}
