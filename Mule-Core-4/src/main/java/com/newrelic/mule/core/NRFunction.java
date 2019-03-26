package com.newrelic.mule.core;

import java.util.function.Function;
import java.util.logging.Level;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;

public class NRFunction<T,U> implements Function<T,U> {

	private Function<T,U>  delegate = null;
	
	private static boolean isTransformed = false;
	
	public NRFunction(Function<T,U> f) {
		delegate = f;
		if(!isTransformed) {
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
			isTransformed = true;
		}
		Exception e = new Exception("Constructing NRFunction");
		NewRelic.getAgent().getLogger().log(Level.FINE, e, "Constructing NRFunction with Function: {0}",f);
	}
	
	@Override
	@Trace(dispatcher=true)
	public U apply(T t) {
		Exception e = new Exception("NRFunction.apply");
		 U u = delegate.apply(t);
		NewRelic.getAgent().getLogger().log(Level.FINE, e, "NRFunction.apply with T: {0} and returning U: {1}",t,u);
		return u;
	}

}
