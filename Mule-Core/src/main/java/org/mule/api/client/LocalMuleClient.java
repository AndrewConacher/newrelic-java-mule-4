package org.mule.api.client;

import java.util.Map;

import org.mule.api.MuleMessage;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.endpoint.OutboundEndpoint;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class LocalMuleClient {

	@Trace(dispatcher=true)
	public MuleMessage process(OutboundEndpoint paramOutboundEndpoint, Object paramObject, Map<String, Object> paramMap) {
		AgentBridge.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","LocalMuleClient",getClass().getName(),"process"});;
		return Weaver.callOriginal();
	}

	public MuleMessage process(OutboundEndpoint paramOutboundEndpoint, MuleMessage paramMuleMessage) {
		AgentBridge.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","LocalMuleClient",getClass().getName(),"process"});;
		return Weaver.callOriginal();
	}

	public MuleMessage request(InboundEndpoint paramInboundEndpoint, long paramLong) {
		AgentBridge.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","LocalMuleClient",getClass().getName(),"request"});;
		return Weaver.callOriginal();
	}

}
