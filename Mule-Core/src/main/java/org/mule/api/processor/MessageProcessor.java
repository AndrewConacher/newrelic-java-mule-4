package org.mule.api.processor;

import java.net.URI;
import java.util.logging.Level;

import org.mule.api.MuleContext;
import org.mule.api.MuleEvent;
import org.mule.api.construct.FlowConstruct;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Logger;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class MessageProcessor {

	@Trace(dispatcher=true)
	public MuleEvent process(MuleEvent muleEvent) {
		String id = muleEvent.getId();
		Logger logger = AgentBridge.getAgent().getLogger();
		logger.log(Level.FINE, "Event Id: {0}", new Object[] {id});
		
		FlowConstruct flowConstruct = muleEvent.getFlowConstruct();
		if(flowConstruct != null) {
			logger.log(Level.FINE, "Flow Construct Name: {0}", new Object[] {flowConstruct.getName()});
		} else {
			logger.log(Level.FINE, "Flow Construct Name: {0}", new Object[] {"Null"});
		}
		URI msgSourceURI = muleEvent.getMessageSourceURI();
		if(msgSourceURI != null) {
			logger.log(Level.FINE, "Message Source URI: {0}", new Object[] {msgSourceURI.toString()});
		} else {
			logger.log(Level.FINE, "Message Source URI: {0}", new Object[] {"Null"});
		}
		MuleContext muleCtx = muleEvent.getMuleContext();
		if(muleCtx != null) {
			logger.log(Level.FINE, "Mule Cluster Id: {0}", new Object[] {muleCtx.getClusterId()});
			logger.log(Level.FINE, "Message Source URI: {0}", new Object[] {muleCtx.getUniqueIdString()});
		}
		AgentBridge.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","MessageProcessor",getClass().getName()});
		return Weaver.callOriginal();
	}
}
