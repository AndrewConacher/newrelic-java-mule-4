package org.mule.api.transport;

import org.mule.api.MuleMessage;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;

@Weave(type=MatchType.Interface)
public abstract class MessageRequesting {
	
	@Trace(dispatcher=true)
	public abstract MuleMessage request(long paramLong);
}
