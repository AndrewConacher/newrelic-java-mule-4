package org.mule.api.routing.filter;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class ObjectFilter {
	
	@Trace(dispatcher=true)
	public boolean accept(Object paramObject) {
		AgentBridge.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","ObjectFilter",getClass().getName(),"accept"});
		return Weaver.callOriginal();
	}
}
