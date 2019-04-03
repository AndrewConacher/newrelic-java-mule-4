package org.mule.runtime.core.internal.event;

import org.mule.runtime.core.internal.message.InternalEvent;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.mule.core.MuleUtils;

@Weave
public abstract class DefaultEventBuilder {
	
	public InternalEvent build() {
		InternalEvent event = Weaver.callOriginal();
		
		String  corrId = event.getCorrelationId();
		
		if(!MuleUtils.hasToken(corrId)) {
			Token token = NewRelic.getAgent().getTransaction().getToken();
			MuleUtils.addToken(corrId, token);
		}
		NewRelic.addCustomParameter("CorrelationId", corrId);
		return event;
		
	}

}
