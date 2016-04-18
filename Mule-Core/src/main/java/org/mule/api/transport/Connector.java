package org.mule.api.transport;

import org.mule.api.MuleMessage;
import org.mule.api.endpoint.InboundEndpoint;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class Connector {

	public abstract String getName();

	@Trace(dispatcher=true)
	public MuleMessage request(String paramString, long paramLong) {
		AgentBridge.getAgent().getTracedMethod().setMetricName(new String[]{"Custom","Connector",getClass().getName(),"request"});
		return Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public MuleMessage request(InboundEndpoint paramInboundEndpoint, long paramLong) {
		AgentBridge.getAgent().getTracedMethod().setMetricName(new String[]{"Custom","Connector",getClass().getName(),"request"});
		return Weaver.callOriginal();
	}
	
}
