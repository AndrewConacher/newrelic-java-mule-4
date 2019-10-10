package org.mule.runtime.core.internal.event;

import org.mule.runtime.core.internal.message.InternalEvent;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class DefaultEventBuilder {
	
	public InternalEvent build() {
		InternalEvent event = Weaver.callOriginal();
		
		String  corrId = event.getCorrelationId();
		
		NewRelic.addCustomParameter("CorrelationId", corrId);
		return event;
	}

}
