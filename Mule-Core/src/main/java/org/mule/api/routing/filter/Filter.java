package org.mule.api.routing.filter;

import java.util.logging.Level;

import org.mule.api.MuleMessage;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Logger;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class Filter {
	
	@Trace(dispatcher=true)
	public boolean accept(MuleMessage muleMessage) {
		Logger logger = AgentBridge.getAgent().getLogger();
		logger.log(Level.FINE, "Message Root Id: {0}", new Object[]{muleMessage.getMessageRootId()});
		logger.log(Level.FINE, "Reply To: {0}", new Object[] {muleMessage.getReplyTo()});
		AgentBridge.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Filter",getClass().getName()});
		return Weaver.callOriginal();
	}
}
