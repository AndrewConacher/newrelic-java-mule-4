package org.mule.api.transport;

import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;

@Weave(type=MatchType.Interface)
public abstract class MessageDispatching {

	@Trace(dispatcher=true)
	public abstract void dispatch(MuleEvent paramMuleEvent) throws DispatchException;

	@Trace(dispatcher=true)
	public abstract MuleMessage send(MuleEvent paramMuleEvent) throws DispatchException;

}
