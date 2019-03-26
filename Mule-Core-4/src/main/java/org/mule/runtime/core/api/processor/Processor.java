package org.mule.runtime.core.api.processor;

import java.util.logging.Level;

import org.mule.runtime.core.api.event.CoreEvent;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.mule.core.MuleUtils;

@Weave(type=MatchType.Interface)
public abstract class Processor {

	@Trace(async=true)
	public CoreEvent process(CoreEvent event) {
		
		
		if(MuleUtils.hasToken(event)) {
			Token token = MuleUtils.getToken(event);
			if(token != null) {
				token.linkAndExpire();
				MuleUtils.removeToken(event);
			}
		}
		CoreEvent returnedEvent = Weaver.callOriginal();
		
		
		if(MuleUtils.hasToken(returnedEvent)) {
			MuleUtils.getToken(returnedEvent).expire();
			MuleUtils.removeToken(returnedEvent);
			
		}
		NewRelic.getAgent().getLogger().log(Level.FINE, "Processor.process: {0}.process({1}) returning {2}, {3}", getClass().getName(),event,returnedEvent,MuleUtils.hasToken(returnedEvent) ? "has token"  :  "has not token");
		return returnedEvent;
	}
	
}
