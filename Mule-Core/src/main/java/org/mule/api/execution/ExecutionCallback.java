package org.mule.api.execution;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class ExecutionCallback<T> {

	@Trace(dispatcher=true)
	public T process() {
		AgentBridge.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","ExecutionCallback",getClass().getName()});
		return Weaver.callOriginal();
	}
}
