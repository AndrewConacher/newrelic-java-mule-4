package org.mule.runtime.core.internal.event;

import org.mule.runtime.api.event.EventContext;
import org.mule.runtime.core.api.event.CoreEvent;

import com.newrelic.agent.bridge.NoOpToken;
import com.newrelic.api.agent.Token;

public class MuleUtils {

	public static Token getToken(CoreEvent event) {
		EventContext context = event.getContext();
		if(AbstractEventContext.class.isInstance(context)) {
			return ((AbstractEventContext)context).token;
		} 
		return null;
	}
	
	public static void setToken(CoreEvent event,Token token) {
		
		if(NoOpToken.class.isInstance(token)) {
			return;
		}
		EventContext context = event.getContext();
		if(AbstractEventContext.class.isInstance(context)) {
			AbstractEventContext dEvent = (AbstractEventContext)context;
			if(dEvent.token == null) {
				dEvent.token = token;
			}
		}
			
	}	
}
