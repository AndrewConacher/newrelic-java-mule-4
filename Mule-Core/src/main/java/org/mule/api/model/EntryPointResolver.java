package org.mule.api.model;

import org.mule.api.MuleEventContext;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;

@Weave(type=MatchType.Interface)
public abstract class EntryPointResolver {
	
	@Trace(dispatcher=true)
	public abstract InvocationResult invoke(Object paramObject, MuleEventContext paramMuleEventContext);
}
