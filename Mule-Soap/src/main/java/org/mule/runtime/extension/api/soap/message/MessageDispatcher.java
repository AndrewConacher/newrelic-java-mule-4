package org.mule.runtime.extension.api.soap.message;

import java.util.logging.Level;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.mule.soap.InboundWrapper;

@Weave(type=MatchType.Interface)
public abstract class MessageDispatcher {

	
	@Trace(dispatcher=true)
	public DispatchingResponse dispatch(DispatchingRequest request) {
		Exception e = new Exception("Soap DispatchingRequest");
		DispatchingResponse resp = Weaver.callOriginal();
		NewRelic.getAgent().getLogger().log(Level.FINE, e, "{0}.dispatch({1}) returned {2}", getClass().getName(),request,resp);
		return resp;
	}
}
