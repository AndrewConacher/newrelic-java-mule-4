package org.mule.component;

import java.lang.reflect.Method;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class BindingInvocationHandler {
	
	@Trace(dispatcher=true)
	public Object invoke(Object proxy, Method method, Object[] args) {
		AgentBridge.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","BindingInvocationHandler","invoke",method.getName()});
		return Weaver.callOriginal();
	}
}
