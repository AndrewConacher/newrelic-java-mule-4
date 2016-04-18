package org.mule.api.model;

import org.mule.api.MuleEventContext;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public class EntryPoint {
	
	@Trace(dispatcher=true)
	public  Object invoke(Object paramObject, MuleEventContext paramMuleEventContext) {
		AgentBridge.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","EntryPoint",getClass().getName(),"invoke"});
		return Weaver.callOriginal();
	}
}
