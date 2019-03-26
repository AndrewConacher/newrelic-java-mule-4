package org.mule.runtime.core.internal.execution;

import org.mule.runtime.core.api.event.CoreEvent;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.mule.core.MuleUtils;

@Weave(type=MatchType.Interface)
public abstract class ModuleFlowProcessingPhaseTemplate {

	@Trace(async=true)
	public CoreEvent routeEvent(CoreEvent event) {
		if(MuleUtils.hasToken(event)) {
			Token token = MuleUtils.getToken(event);
			if(token != null) {
				token.linkAndExpire();
				MuleUtils.removeToken(event);
			}
		}
		
		CoreEvent returnedEvent = Weaver.callOriginal();
		if(!MuleUtils.hasToken(returnedEvent)) {
			Token token = NewRelic.getAgent().getTransaction().getToken();
			if(!MuleUtils.addToken(returnedEvent, token)) {
				token.expire();
				token = null;
			}
		}
		return returnedEvent;
	}
}
