package org.mule.api.component;

import java.util.logging.Level;

import org.mule.api.MuleEvent;
import org.mule.api.construct.FlowConstruct;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Logger;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class LifecycleAdapter {

	@Trace(dispatcher=true)
	public Object invoke(MuleEvent muleEvent) {
		
		Logger logger = AgentBridge.getAgent().getLogger();
		logger.log(Level.FINE, "LifecycleAdapter {0} - Mule event id: {1}", new Object[]{getClass().getName(),muleEvent.getId()});
		FlowConstruct flowconstruct = muleEvent.getFlowConstruct();
		String flowConstructName = flowconstruct != null ? flowconstruct.getName() : "UnknownFlowConstruct";
		logger.log(Level.FINE, "LifecycleAdapter {0} - Mule event flow construct: {1}", new Object[]{getClass().getName(),flowConstructName});
		return Weaver.callOriginal();
	}
}
