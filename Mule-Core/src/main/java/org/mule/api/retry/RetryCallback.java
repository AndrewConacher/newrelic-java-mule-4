package org.mule.api.retry;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class RetryCallback {
	
    public abstract String getWorkDescription();

	@Trace(dispatcher=true)
	public void doWork(RetryContext paramRetryContext) {
		AgentBridge.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","RetryCallback",getWorkDescription()});
		Weaver.callOriginal();
	}
}
