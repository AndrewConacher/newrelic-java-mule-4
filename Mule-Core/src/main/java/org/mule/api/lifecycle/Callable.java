package org.mule.api.lifecycle;

import java.net.URI;
import java.util.logging.Level;

import org.mule.api.MuleEventContext;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Logger;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class Callable {
	
	@Trace(dispatcher=true)
	public Object onCall(MuleEventContext muleEventContext) {
		URI endptURI = muleEventContext.getEndpointURI();
		Logger logger = AgentBridge.getAgent().getLogger();
		if(endptURI != null) {
			logger.log(Level.FINE, "Endpoint URI: {0}", new Object[] {endptURI});
		}
		String uri = endptURI != null ? endptURI.toASCIIString() : "UnknownURI";
		AgentBridge.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Callable",getClass().getName(),uri});
		return Weaver.callOriginal();
	}
}
