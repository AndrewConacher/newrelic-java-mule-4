package org.mule.runtime.core.internal.execution;

import org.mule.runtime.core.api.event.CoreEvent;
import org.mule.runtime.core.internal.event.MuleUtils;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class ModuleFlowProcessingPhaseTemplate {

	@Trace(async=true)
	public CoreEvent routeEvent(CoreEvent event) {

		Token token = MuleUtils.getToken(event);
		if(token != null) {
			token.link();
		} else {
			token = NewRelic.getAgent().getTransaction().getToken();
			MuleUtils.setToken(event, token);
		}
		CoreEvent returnedEvent = Weaver.callOriginal();

		Token token2 = MuleUtils.getToken(returnedEvent);
		if(token2 == null && token != null) {
			MuleUtils.setToken(returnedEvent, token);
			
		}
		
		return returnedEvent;
	}
}
