package org.mule.module.apikit;

import java.lang.reflect.Method;
import java.util.logging.Level;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.WeaveAllConstructors;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class Router {

//	public abstract String getName();
	
//	@Trace(dispatcher=true)
//	private org.reactivestreams.Publisher<org.mule.runtime.core.api.event.CoreEvent> doRoute(org.mule.runtime.core.api.event.CoreEvent event,org.mule.module.apikit.Configuration config,org.mule.extension.http.api.HttpRequestAttributes attrs)  {
//		return Weaver.callOriginal();
//	}

	//	@Trace(dispatcher=true)
	//	private Publisher<CoreEvent> doRoute(CoreEvent mainEvent, Configuration config, HttpRequestAttributes attributes) {
	//		return Weaver.callOriginal();
	//	}
	
	@WeaveAllConstructors
	public Router() {
		Class<?> thisClass = getClass();
		Method[] methods = thisClass.getDeclaredMethods();
		
		for(Method method : methods) {
			if(method.getName().equalsIgnoreCase("doroute")) {
				AgentBridge.instrumentation.instrument(method, "MuleAPIKIT/Route");
			}
		}
	}

//	@Trace
//	public CoreEvent process(final CoreEvent event) {
//		return Weaver.callOriginal();
//	}
//	
//	@Trace
//	public Publisher<CoreEvent> processEvent(CoreEvent event) {
//		return Weaver.callOriginal();
//	}
}
